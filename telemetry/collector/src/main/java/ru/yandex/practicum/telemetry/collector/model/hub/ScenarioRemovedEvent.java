package ru.yandex.practicum.telemetry.collector.model.hub;

import jakarta.validation.constraints.Size;
import lombok.Getter;
import lombok.Setter;
import lombok.ToString;
import ru.yandex.practicum.telemetry.collector.model.hub.enumeration.HubEventType;

@Getter
@Setter
@ToString(callSuper = true)
public class ScenarioRemovedEvent extends HubEvent {

    @Size(min = 3, message = "Длина названия должна быть не менее 3-х символов")
    private String name;
    @Override
    public HubEventType getType() {
        return HubEventType.SCENARIO_REMOVED;
    }
}
