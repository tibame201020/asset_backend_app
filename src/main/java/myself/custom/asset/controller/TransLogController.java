package myself.custom.asset.controller;

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.Parameter;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.tags.Tag;
import myself.custom.asset.model.DateRange;
import myself.custom.asset.model.TransLog;
import myself.custom.asset.service.TransLogService;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/trans")
@Tag(name = "交易紀錄", description = "管理收支交易紀錄 (TransLog)")
public class TransLogController {

    @Autowired
    private TransLogService transLogService;

    @Operation(summary = "儲存交易紀錄", description = "新增或更新一筆收支交易紀錄")
    @ApiResponse(responseCode = "200", description = "儲存成功回傳 true，失敗回傳 false")
    @PostMapping("/save")
    public boolean saveTransLog(@RequestBody @Valid TransLog transLog) {
        return transLogService.saveTransLog(transLog);
    }

    @Operation(summary = "依日期區間查詢交易紀錄", description = "根據起訖日期與可選的類型、關鍵字查詢區間內的交易紀錄")
    @ApiResponse(responseCode = "200", description = "回傳交易紀錄列表")
    @PostMapping("/queryByDateRange")
    public List<TransLog> queryByDateRange(@RequestBody @Valid DateRange dateRange) {
        return transLogService.queryTransLogBetweenDate(dateRange);
    }

    @Operation(summary = "刪除交易紀錄", description = "根據 ID 刪除指定的交易紀錄")
    @ApiResponse(responseCode = "200", description = "刪除成功回傳 true，失敗回傳 false")
    @DeleteMapping("/delete/{id}")
    public boolean deleteTransLogById(
            @Parameter(description = "交易紀錄 ID", required = true, example = "1") @PathVariable long id) {
        return transLogService.deleteTransLogById(id);
    }
}
