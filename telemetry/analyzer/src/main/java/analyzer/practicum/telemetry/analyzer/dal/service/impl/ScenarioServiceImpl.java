package analyzer.practicum.telemetry.analyzer.dal.service.impl;

import analyzer.practicum.telemetry.analyzer.dal.model.Scenario;
import analyzer.practicum.telemetry.analyzer.dal.repository.ScenarioRepository;
import analyzer.practicum.telemetry.analyzer.dal.service.ActionService;
import analyzer.practicum.telemetry.analyzer.dal.service.ConditionService;
import analyzer.practicum.telemetry.analyzer.dal.service.ScenarioService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.Optional;

@Service
@RequiredArgsConstructor
public class ScenarioServiceImpl implements ScenarioService {

    private final ScenarioRepository repository;
    private final ActionService actionServiceImpl;
    private final ConditionService conditionServiceImpl;

    @Transactional
    public void save(Scenario scenario) {
        Optional<Scenario> maybeScenario = repository.findByHubIdAndName(scenario.getHubId(), scenario.getName());
        if (maybeScenario.isEmpty()) {
            scenario.getActions().replaceAll((key, action) -> actionServiceImpl.save(action));
            scenario.getConditions().replaceAll((key, condition) -> conditionServiceImpl.save(condition));
            repository.save(scenario);
        }
    }

    public void remove(String name) {
        repository.deleteByName(name);
    }

    public List<Scenario> getByHubId(String hubId) {
        return repository.findByHubId(hubId);
    }
}
