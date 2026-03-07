package myself.custom.asset.controller;

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.Parameter;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.tags.Tag;
import myself.custom.asset.model.DiaryLog;
import myself.custom.asset.service.DiaryService;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.sql.Timestamp;
import java.util.List;

@RestController
@RequestMapping("/api/diary")
@Tag(name = "日記", description = "管理日記紀錄 (DiaryLog)")
public class DiaryController {

    @Autowired
    private DiaryService diaryService;

    @Operation(summary = "查詢日記紀錄", description = "查詢所有日記，或依起訖時間戳 (毫秒) 篩選指定區間的日記")
    @ApiResponse(responseCode = "200", description = "回傳日記列表")
    @GetMapping("/logs")
    public List<DiaryLog> getLogs(
            @Parameter(description = "起始時間戳 (毫秒)，選填") @RequestParam(required = false) Long start,
            @Parameter(description = "結束時間戳 (毫秒)，選填") @RequestParam(required = false) Long end) {
        if (start != null && end != null) {
            return diaryService.getLogsByRange(new Timestamp(start), new Timestamp(end));
        }
        return diaryService.getAllLogs();
    }

    @Operation(summary = "儲存日記", description = "新增或更新一筆日記紀錄")
    @ApiResponse(responseCode = "200", description = "回傳儲存後的日記物件（含自動產生的 ID）")
    @PostMapping("/log")
    public DiaryLog saveLog(@RequestBody @Valid DiaryLog log) {
        return diaryService.saveLog(log);
    }

    @Operation(summary = "刪除日記", description = "根據 ID 刪除指定的日記紀錄")
    @ApiResponse(responseCode = "200", description = "刪除成功")
    @DeleteMapping("/log/{id}")
    public void deleteLog(
            @Parameter(description = "日記 ID", required = true, example = "1") @PathVariable Long id) {
        diaryService.deleteLog(id);
    }
}
