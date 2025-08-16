package analyzer.practicum.telemetry.analyzer.dal.service.impl;

import analyzer.practicum.telemetry.analyzer.dal.model.Action;
import analyzer.practicum.telemetry.analyzer.dal.repository.ActionRepository;
import analyzer.practicum.telemetry.analyzer.dal.service.ActionService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class ActionServiceImpl implements ActionService {
    private final ActionRepository repository;
    public Action save(Action action) {
        return repository.save(action);
    }
}
