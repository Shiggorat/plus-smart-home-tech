package ru.yandex.practicum.telemetry.collector.service.handler.hub.impl;

import org.springframework.stereotype.Component;
import ru.yandex.practicum.kafka.telemetry.event.ScenarioAddedEventAvro;
import ru.yandex.practicum.telemetry.collector.model.hub.HubEvent;
import ru.yandex.practicum.telemetry.collector.model.hub.ScenarioAddedEvent;
import ru.yandex.practicum.telemetry.collector.model.hub.enumeration.HubEventType;
import ru.yandex.practicum.telemetry.collector.service.handler.EventProducer;
import ru.yandex.practicum.telemetry.collector.utils.Mapper;

import java.util.stream.Collectors;

@Component
public class ScenarioAddedEventHandler extends BaseHubEventHandler<ScenarioAddedEventAvro> {

    private final Mapper mapper;
    public ScenarioAddedEventHandler(EventProducer eventProducer, Mapper mapper) {
        super(eventProducer);
        this.mapper = mapper;
    }

    @Override
    public HubEventType getMessageType() {
        return HubEventType.SCENARIO_ADDED;
    }

    @Override
    public void handle(HubEvent event) {
        eventProducer.sendHubEvent(mapToHubEventAvro(event));
    }

    @Override
    protected ScenarioAddedEventAvro mapToAvro(HubEvent event) {
        ScenarioAddedEvent scenarioAddedEvent = (ScenarioAddedEvent) event;
        return ScenarioAddedEventAvro.newBuilder()
                .setActions(scenarioAddedEvent.getActions().stream()
                        .map(mapper::toDeviceActionAvro)
                        .collect(Collectors.toList()))
                .setConditions(scenarioAddedEvent.getConditions().stream()
                        .map(mapper::toScenarioConditionAvro)
                        .collect(Collectors.toList()))
                .setName(scenarioAddedEvent.getName())
                .build();
    }
}
