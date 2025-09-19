package analyzer.practicum.telemetry.analyzer.handler.impl;

import analyzer.practicum.telemetry.analyzer.dal.mapper.SensorMapper;
import analyzer.practicum.telemetry.analyzer.dal.model.Sensor;
import analyzer.practicum.telemetry.analyzer.dal.service.SensorService;
import analyzer.practicum.telemetry.analyzer.handler.HubEventHandler;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;
import ru.yandex.practicum.kafka.telemetry.event.DeviceAddedEventAvro;
import ru.yandex.practicum.kafka.telemetry.event.HubEventAvro;

@Component
@RequiredArgsConstructor
public class DeviceAddedHandler implements HubEventHandler<DeviceAddedEventAvro> {

    private final SensorService service;

    @Override
    public Class<DeviceAddedEventAvro> getMessageType() {
        return DeviceAddedEventAvro.class;
    }

    @Override
    public void handle(HubEventAvro event) {
        DeviceAddedEventAvro deviceAddedEventAvro = (DeviceAddedEventAvro) event.getPayload();
        Sensor sensor = SensorMapper.mapSensor(deviceAddedEventAvro, event.getHubId());
        service.save(sensor);
    }
}
