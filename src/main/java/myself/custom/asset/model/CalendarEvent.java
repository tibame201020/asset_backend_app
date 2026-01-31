package myself.custom.asset.model;

import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import java.io.Serializable;
import java.sql.Timestamp;

@Entity
@Setter
@Getter
@ToString
@jakarta.persistence.Table(indexes = {
        @jakarta.persistence.Index(name = "idx_calendar_month", columnList = "month"),
        @jakarta.persistence.Index(name = "idx_calendar_datestr", columnList = "dateStr"),
        @jakarta.persistence.Index(name = "idx_calendar_start", columnList = "start")
})
public class CalendarEvent implements Serializable {
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Long id;
    private String title;
    private Timestamp start;
    private String startText;
    private Timestamp end;
    private String endText;
    private int month;
    private String dateStr;
    private Timestamp logTime;
}
