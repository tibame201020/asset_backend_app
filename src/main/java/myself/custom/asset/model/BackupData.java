package myself.custom.asset.model;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

import java.io.Serializable;
import java.util.List;

@Getter
@Setter
@ToString
@Schema(description = "備份資料 (匯出/匯入用)")
public class BackupData implements Serializable {
    @Schema(description = "試算配置列表")
    private List<CalcConfig> calcConfigs;

    @Schema(description = "行事曆事件列表")
    private List<CalendarEvent> calendarEvents;

    @Schema(description = "交易紀錄列表")
    private List<TransLog> transLogs;

    @Schema(description = "運動紀錄列表")
    private List<ExerciseLog> exerciseLogs;

    @Schema(description = "運動類型列表")
    private List<ExerciseType> exerciseTypes;

    @Schema(description = "飲食紀錄列表")
    private List<MealLog> mealLogs;

    @Schema(description = "飲食類型列表")
    private List<MealType> mealTypes;

    @Schema(description = "應用程式設定列表")
    private List<AppSetting> appSettings;
}
