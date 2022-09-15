package myself.custom.asset.model.pk;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

import javax.persistence.Embeddable;
import java.io.Serializable;
import java.sql.Timestamp;

@Getter
@Setter
@Embeddable
@ToString
@AllArgsConstructor
public class CustomPk implements Serializable {
    private String lineId;
    private Timestamp logTime;

    public CustomPk() {
        this.logTime = new Timestamp(System.currentTimeMillis());
    }
}
