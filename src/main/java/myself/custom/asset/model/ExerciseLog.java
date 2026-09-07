package myself.custom.asset.model;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.PositiveOrZero;
import java.io.Serializable;
import java.sql.Timestamp;

@Entity
@Getter
@Setter
@ToString
@jakarta.persistence.Table(indexes = {
        @jakarta.persistence.Index(name = "idx_exercise_log_date", columnList = "transDate")
})
@Schema(description = "運動紀錄。exerciseTypeId 是固定分析維度，exerciseName 是該次運動的具體名稱。")
public class ExerciseLog implements Serializable {
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    @Schema(description = "主鍵 ID (自動產生)", example = "1", accessMode = Schema.AccessMode.READ_ONLY)
    private Long id;

    @NotBlank(message = "運動名稱不能為空")
    @Schema(description = "具體運動名稱，作為 ExerciseType 下的第二層 drill-down", example = "戶外慢跑")
    private String exerciseName;

    @Schema(description = "運動類型 ID；固定的 primary analytics dimension", example = "1")
    private Long exerciseTypeId;

    @NotNull(message = "運動時長不能為空")
    @PositiveOrZero(message = "運動時長不能為負數")
    @Schema(description = "運動時長 (分鐘)", example = "30.0")
    private Double duration;

    @Schema(description = "消耗卡路里；未提供時可依 ExerciseType.kcalPerHour 自動計算", example = "250.0")
    private Double calories;

    @NotNull(message = "運動日期不能為空")
    @Schema(description = "運動日期")
    private Timestamp transDate;

    @Schema(description = "更細節的備註，作為第三層 drill-down", example = "河堤，輕鬆配速")
    private String ps;

    @Schema(description = "紀錄時間")
    private Timestamp logTime;
}
