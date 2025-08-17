package analyzer.practicum.telemetry.analyzer.dal.service.impl;

import analyzer.practicum.telemetry.analyzer.dal.model.Sensor;
import analyzer.practicum.telemetry.analyzer.dal.repository.SensorRepository;
import analyzer.practicum.telemetry.analyzer.dal.service.SensorService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@RequiredArgsConstructor
public class SensorServiceImpl implements SensorService {
    private final SensorRepository repository;

    @Transactional
    public void save(Sensor sensor) {
        boolean exists = repository.existsByIdAndHubId(sensor.getId(), sensor.getHubId());
        if (!exists) {
            repository.save(sensor);
        }
    }

    @Transactional
    public void remove(String id) {
        repository.deleteById(id);
    }
}

