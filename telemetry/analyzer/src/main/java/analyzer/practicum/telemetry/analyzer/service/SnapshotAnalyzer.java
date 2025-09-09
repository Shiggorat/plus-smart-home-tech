package analyzer.practicum.telemetry.analyzer.service;

import analyzer.practicum.telemetry.analyzer.controller.ActionRequestProtoMapper;
import analyzer.practicum.telemetry.analyzer.controller.HubRouterController;
import analyzer.practicum.telemetry.analyzer.dal.model.Condition;
import analyzer.practicum.telemetry.analyzer.dal.model.Scenario;
import analyzer.practicum.telemetry.analyzer.dal.model.enumeration.ConditionType;
import analyzer.practicum.telemetry.analyzer.dal.service.ScenarioService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Component;
import ru.yandex.practicum.grpc.telemetry.event.DeviceActionRequest;
import ru.yandex.practicum.kafka.telemetry.event.ClimateSensorAvro;
import ru.yandex.practicum.kafka.telemetry.event.LightSensorAvro;
import ru.yandex.practicum.kafka.telemetry.event.MotionSensorAvro;
import ru.yandex.practicum.kafka.telemetry.event.SensorStateAvro;
import ru.yandex.practicum.kafka.telemetry.event.SensorsSnapshotAvro;
import ru.yandex.practicum.kafka.telemetry.event.SwitchSensorAvro;

import java.util.List;
import java.util.Map;

@Component
@RequiredArgsConstructor
@Slf4j
public class SnapshotAnalyzer {
    private final ScenarioService scenarioService;
    private final HubRouterController hubRouterController;
    private final ActionRequestProtoMapper actionRequestProtoMapper;

    public void handleSnapshot(SensorsSnapshotAvro sensorsSnapshotAvro) {
        String hubId = sensorsSnapshotAvro.getHubId();
        Map<String, SensorStateAvro> states = sensorsSnapshotAvro.getSensorsState();
        List<Scenario> scenarios = scenarioService.getByHubId(hubId);
        log.info("Для снепшота hubId = {}, найдены сценарии: {}", hubId, scenarios);
        scenarios.stream()
                .filter(scenario -> checkConditions(scenario, states))
                .forEach(this::executeActions);
    }

    private boolean checkConditions(Scenario scenario, Map<String, SensorStateAvro> states) {
        Map<String, Condition> conditionsMap = scenario.getConditions();
        if (conditionsMap.isEmpty()) {
            log.info("Conditions is empty");
        }
        if (scenario.getActions().isEmpty()) {
            log.info("Actions is empty");
        }
        return conditionsMap.entrySet().stream().allMatch(entry ->
                evaluateCondition(entry.getKey(), entry.getValue(), states.get(entry.getKey())));
    }

    private boolean evaluateCondition(String sensorId, Condition condition, SensorStateAvro state) {
        if (state == null) {
            return false;
        }

        Integer sensorValue = extractSensorValue(state.getData(), condition.getType());
        if (sensorValue == null) {
            return false;
        }

        return switch (condition.getOperation()) {
            case EQUALS -> sensorValue.equals(condition.getValue());
            case LOWER_THAN -> sensorValue < condition.getValue();
            case GREATER_THAN -> sensorValue > condition.getValue();
        };
    }

    private Integer extractSensorValue(Object data, ConditionType type) {
        return switch (type) {
            case TEMPERATURE -> data instanceof ClimateSensorAvro climate ? climate.getTemperatureC() : null;
            case HUMIDITY -> data instanceof ClimateSensorAvro climate ? climate.getHumidity() : null;
            case CO2LEVEL -> data instanceof ClimateSensorAvro climate ? climate.getCo2Level() : null;
            case LUMINOSITY -> data instanceof LightSensorAvro light ? light.getLuminosity() : null;
            case MOTION -> data instanceof MotionSensorAvro motion ? (motion.getMotion() ? 1 : 0) : null;
            case SWITCH -> data instanceof SwitchSensorAvro switchSensor ? (switchSensor.getState() ? 1 : 0) : null;
        };
    }

    private void executeActions(Scenario scenario) {
        log.info("Сработавший сценарий: {}", scenario.getActions());
        List<DeviceActionRequest> requests = actionRequestProtoMapper.toActionRequest(scenario);
        requests.forEach(hubRouterController::sendAction);
    }
}
