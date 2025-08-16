package analyzer.practicum.telemetry.analyzer.handler.impl;

import analyzer.practicum.telemetry.analyzer.dal.mapper.ScenarioMapper;
import analyzer.practicum.telemetry.analyzer.dal.model.Scenario;
import analyzer.practicum.telemetry.analyzer.dal.service.ScenarioService;
import analyzer.practicum.telemetry.analyzer.handler.HubEventHandler;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;
import ru.yandex.practicum.kafka.telemetry.event.HubEventAvro;
import ru.yandex.practicum.kafka.telemetry.event.ScenarioAddedEventAvro;

@Component
@RequiredArgsConstructor
public class ScenarioAddedHandler implements HubEventHandler<ScenarioAddedEventAvro> {

    private final ScenarioService service;
    @Override
    public Class<ScenarioAddedEventAvro> getMessageType() {
        return ScenarioAddedEventAvro.class;
    }

    @Override
    public void handle(HubEventAvro event) {
        ScenarioAddedEventAvro scenarioAddedAvro = (ScenarioAddedEventAvro) event.getPayload();
        Scenario scenario = ScenarioMapper.map(event.getHubId(), scenarioAddedAvro);
        service.save(scenario);
    }
}
