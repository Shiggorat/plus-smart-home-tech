package analyzer.practicum.telemetry.analyzer.controller;

import analyzer.practicum.telemetry.analyzer.dal.model.Scenario;
import com.google.protobuf.Timestamp;
import org.springframework.stereotype.Component;
import ru.yandex.practicum.grpc.telemetry.event.ActionTypeProto;
import ru.yandex.practicum.grpc.telemetry.event.DeviceActionProto;
import ru.yandex.practicum.grpc.telemetry.event.DeviceActionRequest;

import java.time.Instant;
import java.util.List;

@Component
public class ActionRequestProtoMapper {
    public List<DeviceActionRequest> toActionRequest(Scenario scenario) {
        Timestamp timestamp = Timestamp.newBuilder()
                .setSeconds(Instant.now().getEpochSecond())
                .setNanos(Instant.now().getNano())
                .build();
        return scenario.getActions().entrySet().stream().map(entry -> {
            return DeviceActionRequest.newBuilder()
                    .setAction(
                            DeviceActionProto.newBuilder()
                                    .setSensorId(entry.getKey())
                                    .setType(ActionTypeProto.valueOf(entry.getValue().getType().name()))
                                    .setValue(entry.getValue().getValue())
                    )
                    .setHubId(scenario.getHubId())
                    .setScenarioName(scenario.getName())
                    .setTimestamp(timestamp)
                    .build();
        }).toList();
    }
}
