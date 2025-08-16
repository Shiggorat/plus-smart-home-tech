package analyzer.practicum.telemetry.analyzer.dal.repository;

import analyzer.practicum.telemetry.analyzer.dal.model.Sensor;
import org.springframework.data.jpa.repository.JpaRepository;

public interface SensorRepository extends JpaRepository<Sensor, String> {
    boolean existsByIdAndHubId(String id, String hubId);
}
