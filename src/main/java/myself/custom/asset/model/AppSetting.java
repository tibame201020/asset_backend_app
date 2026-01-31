package myself.custom.asset.model;

import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

import jakarta.persistence.Entity;
import jakarta.persistence.Id;
import java.io.Serializable;

@Entity
@Setter
@Getter
@ToString
public class AppSetting implements Serializable {
    @Id
    private String keyName; // e.g., "balance_goal"
    private String value; // e.g., "2000"
}
