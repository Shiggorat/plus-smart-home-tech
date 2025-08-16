package analyzer.practicum.telemetry.analyzer.dal.mapper;

import analyzer.practicum.telemetry.analyzer.dal.model.Sensor;
import ru.yandex.practicum.kafka.telemetry.event.DeviceAddedEventAvro;


public class SensorMapper {
    public static Sensor mapSensor(DeviceAddedEventAvro avro, String hubId) {
        Sensor sensor = new Sensor();
        sensor.setId(avro.getId());
        sensor.setHubId(hubId);
        return sensor;
    }

}
