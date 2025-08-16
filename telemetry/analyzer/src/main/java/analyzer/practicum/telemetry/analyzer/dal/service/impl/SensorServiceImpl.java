package analyzer.practicum.telemetry.analyzer.dal.service.impl;

import analyzer.practicum.telemetry.analyzer.dal.model.Sensor;
import analyzer.practicum.telemetry.analyzer.dal.repository.SensorRepository;
import analyzer.practicum.telemetry.analyzer.dal.service.SensorService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class SensorServiceImpl implements SensorService {
    private final SensorRepository repository;
    public void save(Sensor sensor) {
        boolean exists = repository.existsByIdAndHubId(sensor.getId(), sensor.getHubId());
        if (!exists) {
            repository.save(sensor);
        }
    }

    public void remove(String id) {
        repository.deleteById(id);
    }
}

