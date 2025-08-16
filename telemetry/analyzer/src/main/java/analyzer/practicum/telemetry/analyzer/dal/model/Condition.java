package analyzer.practicum.telemetry.analyzer.dal.model;

import analyzer.practicum.telemetry.analyzer.dal.model.enumeration.ConditionOperation;
import analyzer.practicum.telemetry.analyzer.dal.model.enumeration.ConditionType;
import jakarta.persistence.*;
import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

@Entity
@Getter
@Setter
@ToString
@Table(name = "conditions")
public class Condition {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private long id;
    @Enumerated(EnumType.STRING)
    private ConditionType type;
    @Enumerated(EnumType.STRING)
    private ConditionOperation operation;
    private int value;
    @Transient
    private String sensorId;
}
