package myself.custom.asset.model;

import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

import java.io.Serializable;
import java.util.List;

@Getter
@Setter
@ToString
public class BackupData implements Serializable {
    private List<CalcConfig> calcConfigs;
    private List<CalendarEvent> calendarEvents;
    private List<TransLog> transLogs;
}
