package analyzer.practicum.telemetry.analyzer.dal.repository;

import analyzer.practicum.telemetry.analyzer.dal.model.Condition;
import org.springframework.data.jpa.repository.JpaRepository;

public interface ConditionRepository extends JpaRepository<Condition, Long> {
}
