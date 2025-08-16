package analyzer.practicum.telemetry.analyzer.dal.model;

import analyzer.practicum.telemetry.analyzer.dal.model.enumeration.ActionType;
import jakarta.persistence.*;
import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

@Entity
@Getter
@Setter
@ToString
@Table(name = "actions")
public class Action {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private long id;
    @Enumerated(EnumType.STRING)
    private ActionType type;
    private int value;
    @Transient
    private String sensorId;
}
