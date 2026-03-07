package myself.custom.asset.controller;

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.Parameter;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.validation.Valid;
import myself.custom.asset.model.CalcConfig;
import myself.custom.asset.service.CalcService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/calc")
@Tag(name = "試算配置", description = "管理試算相關的配置項目 (CalcConfig)")
public class CalcController {
    @Autowired
    private CalcService calcService;

    @Operation(summary = "批次新增試算配置", description = "一次新增多筆試算配置資料")
    @ApiResponse(responseCode = "200", description = "新增成功回傳 true，失敗回傳 false")
    @PostMapping("/insert")
    public boolean save(@RequestBody @Valid CalcConfig[] calcConfigs) {
        return calcService.save(calcConfigs);
    }

    @Operation(summary = "查詢所有試算配置", description = "取得所有試算配置清單")
    @ApiResponse(responseCode = "200", description = "回傳試算配置列表")
    @GetMapping("/query")
    public List<CalcConfig> queryAll() {
        return calcService.queryAllConfig();
    }

    @Operation(summary = "依 ID 查詢試算配置", description = "根據指定的 ID 查詢單筆試算配置")
    @ApiResponse(responseCode = "200", description = "回傳該筆試算配置，找不到則為 null")
    @PostMapping("/queryById")
    public CalcConfig queryById(@RequestBody Long id) {
        return calcService.queryById(id);
    }

    @Operation(summary = "刪除試算配置", description = "根據 ID 刪除指定的試算配置")
    @ApiResponse(responseCode = "200", description = "刪除成功回傳 true，失敗回傳 false")
    @DeleteMapping("/delete/{id}")
    public boolean deleteById(
            @Parameter(description = "試算配置 ID", required = true, example = "1") @PathVariable Long id) {
        return calcService.deleteById(id);
    }

    @Operation(summary = "更新試算配置", description = "更新已存在的試算配置資料")
    @ApiResponse(responseCode = "200", description = "更新成功回傳 true，失敗回傳 false")
    @PutMapping("/update")
    public boolean updateCalcConfig(@RequestBody @Valid CalcConfig calcConfig) {
        return calcService.updateCalcConfig(calcConfig);
    }

}
