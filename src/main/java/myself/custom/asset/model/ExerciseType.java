package myself.custom.asset.model;

import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import java.io.Serializable;

@Entity
@Getter
@Setter
@ToString
public class ExerciseType implements Serializable {
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Long id;
    private String name;
    private String icon;
    private Double defaultDuration; // minutes
    private Double kcalPerHour; // Calories burned per hour
}
