package myself.custom.asset.controller;

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.Parameter;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.tags.Tag;
import myself.custom.asset.model.DateRange;
import myself.custom.asset.model.ExerciseLog;
import myself.custom.asset.service.ExerciseLogService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/exercise")
@Tag(name = "運動紀錄", description = "管理運動紀錄 (ExerciseLog)")
public class ExerciseLogController {

    @Autowired
    private ExerciseLogService exerciseLogService;

    @Operation(summary = "儲存運動紀錄", description = "新增或更新一筆運動紀錄")
    @ApiResponse(responseCode = "200", description = "儲存成功回傳 true，失敗回傳 false")
    @PostMapping("/save")
    public boolean saveExerciseLog(@RequestBody ExerciseLog exerciseLog) {
        return exerciseLogService.saveExerciseLog(exerciseLog);
    }

    @Operation(summary = "依日期區間查詢運動紀錄", description = "根據起訖日期查詢區間內的運動紀錄")
    @ApiResponse(responseCode = "200", description = "回傳運動紀錄列表")
    @PostMapping("/queryByDateRange")
    public List<ExerciseLog> queryByDateRange(@RequestBody DateRange dateRange) {
        return exerciseLogService.queryExerciseLogBetweenDate(dateRange);
    }

    @Operation(summary = "刪除運動紀錄", description = "根據 ID 刪除指定的運動紀錄")
    @ApiResponse(responseCode = "200", description = "刪除成功回傳 true，失敗回傳 false")
    @DeleteMapping("/delete/{id}")
    public boolean deleteExerciseLogById(
            @Parameter(description = "運動紀錄 ID", required = true, example = "1") @PathVariable long id) {
        return exerciseLogService.deleteExerciseLogById(id);
    }
}
