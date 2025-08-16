package analyzer.practicum.telemetry.analyzer.handler.impl;

import analyzer.practicum.telemetry.analyzer.dal.service.SensorService;
import analyzer.practicum.telemetry.analyzer.handler.HubEventHandler;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;
import ru.yandex.practicum.kafka.telemetry.event.DeviceRemovedEventAvro;
import ru.yandex.practicum.kafka.telemetry.event.HubEventAvro;

@Component
@RequiredArgsConstructor
public class DeviceRemovedHandler implements HubEventHandler<DeviceRemovedEventAvro> {

    private final SensorService service;

    @Override
    public Class<DeviceRemovedEventAvro> getMessageType() {
        return DeviceRemovedEventAvro.class;
    }

    @Override
    public void handle(HubEventAvro event) {
        DeviceRemovedEventAvro deviceRemovedEventAvro = (DeviceRemovedEventAvro) event.getPayload();
        service.remove(deviceRemovedEventAvro.getId());
    }
}
