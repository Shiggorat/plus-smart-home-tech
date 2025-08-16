package analyzer.practicum.telemetry.analyzer.dal.service.impl;

import analyzer.practicum.telemetry.analyzer.dal.model.Condition;
import analyzer.practicum.telemetry.analyzer.dal.repository.ConditionRepository;
import analyzer.practicum.telemetry.analyzer.dal.service.ConditionService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class ConditionServiceImpl implements ConditionService {
    private final ConditionRepository repository;
    public Condition save(Condition condition) {
        return repository.save(condition);
    }
}
