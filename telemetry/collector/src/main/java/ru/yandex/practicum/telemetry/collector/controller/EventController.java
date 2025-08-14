package ru.yandex.practicum.telemetry.collector.controller;

import jakarta.validation.Valid;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;
import ru.yandex.practicum.telemetry.collector.model.exception.UnknownEventTypeException;
import ru.yandex.practicum.telemetry.collector.model.hub.HubEvent;
import ru.yandex.practicum.telemetry.collector.model.hub.enumeration.HubEventType;
import ru.yandex.practicum.telemetry.collector.model.sensor.SensorEvent;
import ru.yandex.practicum.telemetry.collector.model.sensor.SensorEventType;
import ru.yandex.practicum.telemetry.collector.service.handler.hub.HubEventHandler;
import ru.yandex.practicum.telemetry.collector.service.handler.sensor.SensorEventHandler;

import java.util.Map;
import java.util.Set;
import java.util.function.Function;
import java.util.stream.Collectors;

@RestController
@RequestMapping("/events")
public class EventController {

    private final Map<SensorEventType, SensorEventHandler> sensorEventHandlers;
    private final Map<HubEventType, HubEventHandler> hubEventHandlers;

    public EventController(Set<SensorEventHandler> sensorEventHandlers, Set<HubEventHandler> hubEventHandlers) {
        this.sensorEventHandlers = sensorEventHandlers.stream()
                .collect(Collectors.toMap(SensorEventHandler::getMessageType, Function.identity()));
        this.hubEventHandlers = hubEventHandlers.stream()
                .collect(Collectors.toMap(HubEventHandler::getMessageType, Function.identity()));
    }

    @PostMapping("/sensors")
    @ResponseStatus(HttpStatus.OK)
    public void addSensorEvent(@Valid @RequestBody SensorEvent sensorEvent) {
        SensorEventType type = sensorEvent.getType();
        SensorEventHandler handler = sensorEventHandlers.get(type);
        if (handler != null) {
            handler.handle(sensorEvent);
        } else {
            throw new UnknownEventTypeException("Unknown sensor event type: " + type);
        }
    }

    @PostMapping("/hubs")
    @ResponseStatus(HttpStatus.OK)
    public void addHubEvent(@Valid @RequestBody HubEvent hubEvent) {
        HubEventType type = hubEvent.getType();
        HubEventHandler handler = hubEventHandlers.get(type);
        if (handler != null) {
            handler.handle(hubEvent);
        } else {
            throw new UnknownEventTypeException("Unknown hub event type: " + type);
        }
    }
}
