package ru.yandex.practicum.telemetry.collector.service.handler.hub.impl;

import lombok.RequiredArgsConstructor;
import org.apache.avro.specific.SpecificRecordBase;
import ru.yandex.practicum.kafka.telemetry.event.HubEventAvro;
import ru.yandex.practicum.telemetry.collector.model.hub.HubEvent;
import ru.yandex.practicum.telemetry.collector.service.handler.EventProducer;
import ru.yandex.practicum.telemetry.collector.service.handler.hub.HubEventHandler;

@RequiredArgsConstructor
public abstract class BaseHubEventHandler<T extends SpecificRecordBase> implements HubEventHandler {

    protected final EventProducer eventProducer;

    protected HubEventAvro mapToHubEventAvro(HubEvent event) {
        return HubEventAvro.newBuilder()
                .setHubId(event.getHubId())
                .setTimestamp(event.getTimestamp())
                .setPayload(mapToAvro(event))
                .build();
    }
    protected abstract T mapToAvro(HubEvent event);

}
