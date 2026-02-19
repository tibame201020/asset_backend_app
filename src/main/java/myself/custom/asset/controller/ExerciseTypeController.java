package myself.custom.asset.controller;

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.Parameter;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.tags.Tag;
import myself.custom.asset.model.ExerciseType;
import myself.custom.asset.service.ExerciseTypeService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/exercise-type")
@CrossOrigin
@Tag(name = "運動類型", description = "管理運動類型定義 (ExerciseType)")
public class ExerciseTypeController {

    @Autowired
    private ExerciseTypeService exerciseTypeService;

    @Operation(summary = "查詢所有運動類型", description = "取得所有已定義的運動類型清單")
    @ApiResponse(responseCode = "200", description = "回傳運動類型列表")
    @GetMapping("/all")
    public List<ExerciseType> getAll() {
        return exerciseTypeService.getAll();
    }

    @Operation(summary = "儲存運動類型", description = "新增或更新一筆運動類型定義")
    @ApiResponse(responseCode = "200", description = "回傳儲存後的運動類型物件")
    @PostMapping("/save")
    public ExerciseType save(@RequestBody ExerciseType type) {
        return exerciseTypeService.save(type);
    }

    @Operation(summary = "刪除運動類型", description = "根據 ID 刪除指定的運動類型")
    @ApiResponse(responseCode = "200", description = "刪除成功回傳 true，失敗回傳 false")
    @DeleteMapping("/delete/{id}")
    public boolean delete(
            @Parameter(description = "運動類型 ID", required = true, example = "1") @PathVariable Long id) {
        try {
            exerciseTypeService.delete(id);
            return true;
        } catch (Exception e) {
            return false;
        }
    }
}
