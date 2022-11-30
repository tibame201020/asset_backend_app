package myself.custom.asset.model;

import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

import java.io.Serializable;
import java.sql.Timestamp;

@Setter
@Getter
@ToString
public class DateRange implements Serializable {
    private Timestamp start;
    private Timestamp end;
    private String type;
    private String keyword;
}
