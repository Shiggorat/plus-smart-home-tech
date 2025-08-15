package ru.yandex.practicum.telemetry.collector.utils;

import org.mapstruct.MappingConstants;
import ru.yandex.practicum.grpc.telemetry.event.DeviceActionProto;
import ru.yandex.practicum.grpc.telemetry.event.ScenarioConditionProto;
import ru.yandex.practicum.kafka.telemetry.event.*;

@org.mapstruct.Mapper(componentModel = MappingConstants.ComponentModel.SPRING)
public interface Mapper {
    default DeviceActionAvro toDeviceActionAvro(DeviceActionProto deviceAction) {
        return DeviceActionAvro.newBuilder()
                .setType(ActionTypeAvro.valueOf(deviceAction.getType().name()))
                .setSensorId(deviceAction.getSensorId())
                .setValue(deviceAction.getValue())
                .build();
    }

    default ScenarioConditionAvro toScenarioConditionAvro(ScenarioConditionProto scenarioCondition) {
        ScenarioConditionAvro.Builder builder = ScenarioConditionAvro.newBuilder()
                .setType(ConditionTypeAvro.valueOf(scenarioCondition.getType().name()))
                .setOperation(ConditionOperationAvro.valueOf(scenarioCondition.getOperation().name()))
                .setSensorId(scenarioCondition.getSensorId());

        switch (scenarioCondition.getValueCase()) {
            case BOOL_VALUE:
                builder.setValue(scenarioCondition.getBoolValue());
                break;
            case INT_VALUE:
                builder.setValue(scenarioCondition.getIntValue());
                break;
            case VALUE_NOT_SET:
                builder.setValue(null);
                break;
        }

        return builder.build();
    }
}
