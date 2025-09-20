package ru.yandex.practicum.telemetry.collector.model.exception;

public class UnknownEventTypeException extends RuntimeException {
    public UnknownEventTypeException(String message) {
        super(message);
    }
}
