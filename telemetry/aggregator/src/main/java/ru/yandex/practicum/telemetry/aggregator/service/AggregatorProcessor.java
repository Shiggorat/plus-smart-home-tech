package ru.yandex.practicum.telemetry.aggregator.service;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.apache.avro.specific.SpecificRecordBase;
import org.apache.kafka.clients.consumer.Consumer;
import org.apache.kafka.clients.consumer.ConsumerRecord;
import org.apache.kafka.clients.consumer.ConsumerRecords;
import org.apache.kafka.clients.consumer.OffsetAndMetadata;
import org.apache.kafka.clients.producer.Producer;
import org.apache.kafka.clients.producer.ProducerRecord;
import org.apache.kafka.common.TopicPartition;
import org.apache.kafka.common.errors.WakeupException;
import org.springframework.stereotype.Component;
import ru.yandex.practicum.kafka.telemetry.event.SensorEventAvro;
import ru.yandex.practicum.kafka.telemetry.event.SensorsSnapshotAvro;
import ru.yandex.practicum.telemetry.aggregator.config.KafkaClient;
import ru.yandex.practicum.telemetry.aggregator.storage.SensorSnapshotStorage;

import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Optional;

@Slf4j
@Component
@RequiredArgsConstructor
public class AggregatorProcessor {

    private final Map<TopicPartition, OffsetAndMetadata> currentOffsets = new HashMap<>();

    private final KafkaClient kafkaClient;
    private final SensorSnapshotStorage storage;


    public void start() {
        Consumer<String, SpecificRecordBase> consumer = kafkaClient.getConsumer();
        Producer<String, SpecificRecordBase> producer = kafkaClient.getProducer();

        Runtime.getRuntime().addShutdownHook(new Thread(consumer::wakeup));

        try {
            consumer.subscribe(List.of(kafkaClient.getTopicsProperties().getTelemetrySensorsV1()));

            while (true) {
                ConsumerRecords<String, SpecificRecordBase> records = consumer.poll(kafkaClient.getPollTimeout());

                int count = 0;
                for (ConsumerRecord<String, SpecificRecordBase> record : records) {
                    handleRecord(record, producer);
                    manageOffsets(record, count, consumer);
                    count++;
                }
                consumer.commitAsync();
            }
        } catch (WakeupException ignored) {

        } catch (Exception e) {
            log.error("Ошибка во время обработки событий от датчиков", e);
        } finally {
            try {
                producer.flush();
                consumer.commitSync(currentOffsets);
            } finally {
                log.info("Закрываем consumer");
                consumer.close();
                log.info("Закрываем producer");
                producer.close();
            }
        }
    }

    private void manageOffsets(ConsumerRecord<String, SpecificRecordBase> record, int count, Consumer<String, SpecificRecordBase> consumer) {
        currentOffsets.put(
                new TopicPartition(record.topic(), record.partition()),
                new OffsetAndMetadata(record.offset() + 1)
        );

        if(count % 10 == 0) {
            consumer.commitAsync(currentOffsets, (offsets, exception) -> {
                if(exception != null) {
                    log.warn("Ошибка во время фиксации оффсетов: {}", offsets, exception);
                }
            });
        }
    }

    private void handleRecord(ConsumerRecord<String, SpecificRecordBase> record, Producer<String, SpecificRecordBase> producer) {
        if (record.value() instanceof SensorEventAvro event) {
            Optional<SensorsSnapshotAvro> maybeUpdatedSnapshot = storage.updateSnapshot(event);
            maybeUpdatedSnapshot.ifPresent(snapshot -> {
                long timestamp = snapshot.getTimestamp().toEpochMilli();
                ProducerRecord<String, SpecificRecordBase> snapshotRecord = new ProducerRecord<>(
                        kafkaClient.getTopicsProperties().getTelemetrySnapshotsV1(),
                        null,
                        timestamp,
                        snapshot.getHubId(),
                        snapshot
                        );
                producer.send(snapshotRecord, (metadata, ex) -> {
                    if (ex != null) {
                        log.error("Ошибка при отправке снапшота для хаба {}: {}", snapshot.getHubId(), ex.getMessage(), ex);
                    } else {
                        log.info("Снапшот для хаба {} успешно отправлен: partition={}, offset={}",
                                snapshot.getHubId(), metadata.partition(), metadata.offset());
                    }
                });
        });
        } else {
            log.warn("Получено неизвестное сообщение: {}", record.value());
        }
    }
}
