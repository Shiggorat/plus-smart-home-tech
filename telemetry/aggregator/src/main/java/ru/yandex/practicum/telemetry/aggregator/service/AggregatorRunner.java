package ru.yandex.practicum.telemetry.aggregator.service;

import lombok.RequiredArgsConstructor;
import org.springframework.boot.CommandLineRunner;
import org.springframework.stereotype.Component;

@Component
@RequiredArgsConstructor
public class AggregatorRunner implements CommandLineRunner {

    private final SnapshotProcessor snapshotProcessor;

    @Override
    public void run(String... args) throws Exception {
        snapshotProcessor.start();
    }
}
