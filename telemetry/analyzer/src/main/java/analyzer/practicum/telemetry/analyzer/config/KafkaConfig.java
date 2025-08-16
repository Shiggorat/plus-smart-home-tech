package analyzer.practicum.telemetry.analyzer.config;

import org.apache.avro.specific.SpecificRecordBase;
import org.apache.kafka.clients.consumer.Consumer;
import org.apache.kafka.clients.consumer.ConsumerConfig;
import org.apache.kafka.clients.consumer.KafkaConsumer;
import org.apache.kafka.common.serialization.StringDeserializer;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Scope;
import ru.yandex.practicum.kafka.serializer.HubEventDeserializer;
import ru.yandex.practicum.kafka.serializer.SnapshotsDeserializer;

import java.time.Duration;
import java.util.Properties;

@Configuration
public class KafkaConfig {
    @Value("${kafka.bootstrap.servers}")
    private String bootstrapServers;
    @Value("${kafka.consumer.group.hub}")
    private String groupHub;

    @Value("${kafka.consumer.group.snapshot}")
    private String groupSnapshot;

    @Value("${kafka.consumer.poll.timeout}")
    private long pollTimeout;

    @Autowired
    private KafkaTopicsProperties topicsProperties;

    private KafkaConsumer<String, SpecificRecordBase> kafkaConsumerHub() {
        Properties config = new Properties();
        config.setProperty(ConsumerConfig.BOOTSTRAP_SERVERS_CONFIG, bootstrapServers);
        config.setProperty(ConsumerConfig.KEY_DESERIALIZER_CLASS_CONFIG, StringDeserializer.class.getCanonicalName());
        config.setProperty(ConsumerConfig.VALUE_DESERIALIZER_CLASS_CONFIG, HubEventDeserializer.class.getCanonicalName());
        config.setProperty(ConsumerConfig.GROUP_ID_CONFIG, groupHub);
        return new KafkaConsumer<>(config);
    }

    private KafkaConsumer<String, SpecificRecordBase> kafkaConsumerSnapshot() {
        Properties config = new Properties();
        config.setProperty(ConsumerConfig.BOOTSTRAP_SERVERS_CONFIG, bootstrapServers);
        config.setProperty(ConsumerConfig.KEY_DESERIALIZER_CLASS_CONFIG, StringDeserializer.class.getCanonicalName());
        config.setProperty(ConsumerConfig.VALUE_DESERIALIZER_CLASS_CONFIG, SnapshotsDeserializer.class.getCanonicalName());
        config.setProperty(ConsumerConfig.GROUP_ID_CONFIG, groupSnapshot);
        return new KafkaConsumer<>(config);
    }

    @Bean
    @Scope("prototype")
    public KafkaClient getClient() {
        return new KafkaClient() {

            private Consumer<String, SpecificRecordBase> consumerHub;
            private Consumer<String, SpecificRecordBase> consumerSnapshot;

            @Override
            public Consumer<String, SpecificRecordBase> getConsumerHub() {
                if (consumerHub == null) {
                    consumerHub = kafkaConsumerHub();
                }
                return consumerHub;
            }

            @Override
            public Consumer<String, SpecificRecordBase> getConsumerSnapshot() {
                if (consumerSnapshot == null) {
                    consumerSnapshot = kafkaConsumerSnapshot();
                }
                return consumerSnapshot;
            }

            @Override
            public Duration getPollTimeout() {
                return Duration.ofMillis(pollTimeout);
            }

            @Override
            public KafkaTopicsProperties getTopicsProperties() {
                return topicsProperties;
            }

        };
    }
}
