package ru.yandex.practicum.telemetry.collector.model.hub;

import jakarta.validation.Valid;
import jakarta.validation.constraints.NotEmpty;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Size;
import lombok.AccessLevel;
import lombok.Getter;
import lombok.Setter;
import lombok.ToString;
import lombok.experimental.FieldDefaults;
import ru.yandex.practicum.telemetry.collector.model.hub.enumeration.HubEventType;

import java.util.List;

@Getter
@Setter
@ToString(callSuper = true)
@FieldDefaults(level = AccessLevel.PRIVATE)
public class ScenarioAddedEvent extends HubEvent {
    @NotNull
    @Size(min = 3, message = "Длина названия должна быть не менее 3-х символов")
    String name;
    @NotEmpty
    @Valid
    List<ScenarioCondition> conditions;
    @NotEmpty
    @Valid
    List<DeviceAction> actions;

    @Override
    public HubEventType getType() {
        return HubEventType.SCENARIO_ADDED;
    }
}
