package ru.yandex.practicum.telemetry.collector.service.handler.sensor.impl;

import ru.yandex.practicum.kafka.telemetry.event.TemperatureSensorAvro;
import ru.yandex.practicum.telemetry.collector.model.sensor.SensorEvent;
import ru.yandex.practicum.telemetry.collector.model.sensor.SensorEventType;
import ru.yandex.practicum.telemetry.collector.model.sensor.TemperatureSensorEvent;
import ru.yandex.practicum.telemetry.collector.service.handler.EventProducer;

public class TemperatureSensorEventHandler extends BaseSensorEventHandler<TemperatureSensorAvro> {
    public TemperatureSensorEventHandler(EventProducer eventProducer) {
        super(eventProducer);
    }

    @Override
    public SensorEventType getMessageType() {
        return SensorEventType.TEMPERATURE_SENSOR_EVENT;
    }

    @Override
    public void handle(SensorEvent event) {
        eventProducer.sendSensorEvent(mapToSensorEventAvro(event));
    }

    @Override
    protected TemperatureSensorAvro mapToAvro(SensorEvent event) {
        TemperatureSensorEvent temperatureSensorEvent = (TemperatureSensorEvent) event;
        return TemperatureSensorAvro.newBuilder()
                .setTemperatureC(temperatureSensorEvent.getTemperatureC())
                .setTemperatureF(temperatureSensorEvent.getTemperatureF())
                .build();
    }
}
