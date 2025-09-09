package ru.yandex.practicum.telemetry.collector.service.handler.hub.impl;

import org.springframework.stereotype.Component;
import ru.yandex.practicum.grpc.telemetry.event.HubEventProto;
import ru.yandex.practicum.grpc.telemetry.event.ScenarioAddedEventProto;
import ru.yandex.practicum.kafka.telemetry.event.ScenarioAddedEventAvro;
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
    public HubEventProto.PayloadCase getMessageType() {
        return HubEventProto.PayloadCase.SCENARIO_ADDED;
    }

    @Override
    public void handle(HubEventProto event) {
        eventProducer.sendHubEvent(mapToHubEventAvro(event));
    }

    @Override
    protected ScenarioAddedEventAvro mapToAvro(HubEventProto event) {
        ScenarioAddedEventProto scenarioAddedEvent = event.getScenarioAdded();
        return ScenarioAddedEventAvro.newBuilder()
                .setActions(scenarioAddedEvent.getActionList().stream()
                        .map(mapper::toDeviceActionAvro)
                        .collect(Collectors.toList()))
                .setConditions(scenarioAddedEvent.getConditionList().stream()
                        .map(mapper::toScenarioConditionAvro)
                        .collect(Collectors.toList()))
                .setName(scenarioAddedEvent.getName())
                .build();
    }
}
