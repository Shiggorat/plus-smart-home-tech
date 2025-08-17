package analyzer.practicum.telemetry.analyzer.dal.service.impl;

import analyzer.practicum.telemetry.analyzer.dal.model.Action;
import analyzer.practicum.telemetry.analyzer.dal.repository.ActionRepository;
import analyzer.practicum.telemetry.analyzer.dal.service.ActionService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@RequiredArgsConstructor
public class ActionServiceImpl implements ActionService {
    private final ActionRepository repository;

    @Transactional
    public Action save(Action action) {
        return repository.save(action);
    }
}
