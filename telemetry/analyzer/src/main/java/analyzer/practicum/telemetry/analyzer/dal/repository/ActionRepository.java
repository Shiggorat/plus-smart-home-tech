package analyzer.practicum.telemetry.analyzer.dal.repository;

import analyzer.practicum.telemetry.analyzer.dal.model.Action;
import org.springframework.data.jpa.repository.JpaRepository;

public interface ActionRepository extends JpaRepository<Action, Long> {
}
