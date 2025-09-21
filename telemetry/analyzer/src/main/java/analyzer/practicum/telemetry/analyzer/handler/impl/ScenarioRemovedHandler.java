package analyzer.practicum.telemetry.analyzer.handler.impl;

import analyzer.practicum.telemetry.analyzer.dal.service.ScenarioService;
import analyzer.practicum.telemetry.analyzer.handler.HubEventHandler;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;
import ru.yandex.practicum.kafka.telemetry.event.HubEventAvro;
import ru.yandex.practicum.kafka.telemetry.event.ScenarioRemovedEventAvro;

@Component
@RequiredArgsConstructor
public class ScenarioRemovedHandler implements HubEventHandler<ScenarioRemovedEventAvro> {

    private final ScenarioService service;

    @Override
    public Class<ScenarioRemovedEventAvro> getMessageType() {
        return ScenarioRemovedEventAvro.class;
    }

    @Override
    public void handle(HubEventAvro event) {
        ScenarioRemovedEventAvro scenarioRemovedEventAvro = (ScenarioRemovedEventAvro) event.getPayload();
        service.remove(scenarioRemovedEventAvro.getName());
    }
}
