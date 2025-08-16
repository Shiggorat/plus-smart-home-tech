package ru.yandex.practicum.telemetry.aggregator;

import lombok.RequiredArgsConstructor;
import org.springframework.boot.CommandLineRunner;
import org.springframework.stereotype.Component;
import ru.yandex.practicum.telemetry.aggregator.service.AggregatorProcessor;

@Component
@RequiredArgsConstructor
public class AggregatorRunner implements CommandLineRunner {

    private final AggregatorProcessor aggregatorProcessor;

    @Override
    public void run(String... args) throws Exception {
        aggregatorProcessor.start();
    }
}
