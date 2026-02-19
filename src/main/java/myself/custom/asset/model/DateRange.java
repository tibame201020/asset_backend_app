package myself.custom.asset.model;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

import java.io.Serializable;
import java.sql.Timestamp;

@Setter
@Getter
@ToString
@Schema(description = "日期區間查詢條件")
public class DateRange implements Serializable {
    @Schema(description = "起始時間")
    private Timestamp start;

    @Schema(description = "結束時間")
    private Timestamp end;

    @Schema(description = "類型篩選 (選填)", example = "支出")
    private String type;

    @Schema(description = "關鍵字篩選 (選填)", example = "午餐")
    private String keyword;
}
