package myself.custom.asset.model;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.validation.constraints.Max;
import jakarta.validation.constraints.Min;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
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
@Schema(description = "行事曆事件")
public class CalendarEvent implements Serializable {
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    @Schema(description = "主鍵 ID (自動產生)", example = "1", accessMode = Schema.AccessMode.READ_ONLY)
    private Long id;

    @NotBlank(message = "事件標題不能為空")
    @Schema(description = "事件標題", example = "團隊會議")
    private String title;

    @NotNull(message = "開始時間不能為空")
    @Schema(description = "開始時間")
    private Timestamp start;

    @Schema(description = "開始時間文字", example = "2026-02-19 10:00")
    private String startText;

    @Schema(description = "結束時間")
    private Timestamp end;

    @Schema(description = "結束時間文字", example = "2026-02-19 11:00")
    private String endText;

    @Min(value = 1, message = "月份必須在 1-12 之間")
    @Max(value = 12, message = "月份必須在 1-12 之間")
    @Schema(description = "月份 (1-12)", example = "2")
    private int month;

    @NotBlank(message = "日期字串不能為空")
    @Schema(description = "日期字串", example = "2026-02-19")
    private String dateStr;

    @Schema(description = "紀錄時間")
    private Timestamp logTime;
}
