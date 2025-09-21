package analyzer.practicum.telemetry.analyzer.dal.service;

import analyzer.practicum.telemetry.analyzer.dal.model.Scenario;

import java.util.List;

public interface ScenarioService {
    void save(Scenario scenario);

    void remove(String name);

    List<Scenario> getByHubId(String hubId);
}
