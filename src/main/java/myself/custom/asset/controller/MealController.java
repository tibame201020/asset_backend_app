package myself.custom.asset.controller;

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.Parameter;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.tags.Tag;
import myself.custom.asset.model.MealLog;
import myself.custom.asset.model.MealType;
import myself.custom.asset.service.MealService;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.sql.Timestamp;
import java.util.List;

@RestController
@RequestMapping("/api/meal")
@Tag(name = "飲食管理", description = "管理飲食紀錄 (MealLog) 與飲食類型 (MealType)")
public class MealController {

    @Autowired
    private MealService mealService;

    // ===== Logs =====

    @Operation(summary = "查詢飲食紀錄", description = "查詢所有飲食紀錄，或依起訖時間戳 (毫秒) 篩選指定區間的紀錄")
    @ApiResponse(responseCode = "200", description = "回傳飲食紀錄列表")
    @GetMapping("/logs")
    public List<MealLog> getLogs(
            @Parameter(description = "起始時間戳 (毫秒)，選填") @RequestParam(required = false) Long start,
            @Parameter(description = "結束時間戳 (毫秒)，選填") @RequestParam(required = false) Long end) {
        if (start != null && end != null) {
            return mealService.getLogsByRange(new Timestamp(start), new Timestamp(end));
        }
        return mealService.getAllLogs();
    }

    @Operation(summary = "儲存飲食紀錄", description = "新增或更新一筆飲食紀錄")
    @ApiResponse(responseCode = "200", description = "回傳儲存後的飲食紀錄物件")
    @PostMapping("/log")
    public MealLog saveLog(@RequestBody @Valid MealLog log) {
        return mealService.saveLog(log);
    }

    @Operation(summary = "刪除飲食紀錄", description = "根據 ID 刪除指定的飲食紀錄")
    @ApiResponse(responseCode = "200", description = "刪除成功")
    @DeleteMapping("/log/{id}")
    public void deleteLog(
            @Parameter(description = "飲食紀錄 ID", required = true, example = "1") @PathVariable Long id) {
        mealService.deleteLog(id);
    }

    // ===== Types =====

    @Operation(summary = "查詢所有飲食類型", description = "取得所有已定義的飲食類型清單")
    @ApiResponse(responseCode = "200", description = "回傳飲食類型列表")
    @GetMapping("/types")
    public List<MealType> getAllTypes() {
        return mealService.getAllTypes();
    }

    @Operation(summary = "儲存飲食類型", description = "新增或更新一筆飲食類型定義")
    @ApiResponse(responseCode = "200", description = "回傳儲存後的飲食類型物件")
    @PostMapping("/type")
    public MealType saveType(@RequestBody @Valid MealType type) {
        return mealService.saveType(type);
    }

    @Operation(summary = "刪除飲食類型", description = "根據 ID 刪除指定的飲食類型")
    @ApiResponse(responseCode = "200", description = "刪除成功")
    @DeleteMapping("/type/{id}")
    public void deleteType(
            @Parameter(description = "飲食類型 ID", required = true, example = "1") @PathVariable Long id) {
        mealService.deleteType(id);
    }
}
