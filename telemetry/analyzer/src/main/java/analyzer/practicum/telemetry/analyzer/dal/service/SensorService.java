package analyzer.practicum.telemetry.analyzer.dal.service;

import analyzer.practicum.telemetry.analyzer.dal.model.Sensor;

public interface SensorService {
    public void save(Sensor sensor);

    public void remove(String id);
}
