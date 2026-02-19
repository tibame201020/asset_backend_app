package myself.custom.asset.controller;

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.tags.Tag;
import myself.custom.asset.model.BackupData;
import myself.custom.asset.repo.CalcConfigRepo;
import myself.custom.asset.repo.CalendarEventRepo;
import myself.custom.asset.repo.TransLogRepo;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/api/setting")
@Tag(name = "系統設定", description = "系統設定、資料匯出入、應用程式設定")
public class SettingController {

    @Autowired
    private CalcConfigRepo calcConfigRepo;
    @Autowired
    private CalendarEventRepo calendarEventRepo;
    @Autowired
    private TransLogRepo transLogRepo;
    @Autowired
    private myself.custom.asset.repo.ExerciseLogRepository exerciseLogRepository;
    @Autowired
    private myself.custom.asset.repo.ExerciseTypeRepository exerciseTypeRepository;
    @Autowired
    private myself.custom.asset.repo.MealLogRepository mealLogRepository;
    @Autowired
    private myself.custom.asset.repo.MealTypeRepository mealTypeRepository;
    @Autowired
    private myself.custom.asset.repo.AppSettingRepo appSettingRepo;

    @Operation(summary = "清除指定模組資料", description = "根據 target 清除對應模組的所有資料。可用的 target 值：deposit, calc, calendar, exercise, exercisetype, meal, mealtype")
    @ApiResponse(responseCode = "200", description = "刪除成功回傳 true，失敗或未知 target 回傳 false")
    @PostMapping("/del")
    public boolean deleteAll(@RequestBody java.util.Map<String, String> payload) {
        String target = payload.get("target");
        System.out.println("Delete All Request for target: [" + target + "]");
        try {
            if (target == null)
                return false;
            String cleanTarget = target.trim().toLowerCase();
            switch (cleanTarget) {
                case "deposit":
                    transLogRepo.deleteAll();
                    System.out.println("Deleted all deposit data");
                    return true;
                case "calc":
                    calcConfigRepo.deleteAll();
                    System.out.println("Deleted all calc data");
                    return true;
                case "calendar":
                    calendarEventRepo.deleteAll();
                    System.out.println("Deleted all calendar data");
                    return true;
                case "exercise":
                    exerciseLogRepository.deleteAll();
                    System.out.println("Deleted all exercise data");
                    return true;
                case "exercisetype":
                    exerciseTypeRepository.deleteAll();
                    System.out.println("Deleted all exercise type data");
                    return true;
                case "meal":
                    mealLogRepository.deleteAll();
                    System.out.println("Deleted all meal data");
                    return true;
                case "mealtype":
                    mealTypeRepository.deleteAll();
                    System.out.println("Deleted all meal type data");
                    return true;
                default:
                    System.out.println("Unknown target: " + cleanTarget);
                    return false;
            }
        } catch (Exception e) {
            e.printStackTrace();
            return false;
        }
    }

    @Operation(summary = "匯出所有資料", description = "匯出所有模組的資料為 JSON 格式，可用於備份")
    @ApiResponse(responseCode = "200", description = "回傳包含所有模組資料的 BackupData 物件")
    @GetMapping("/export")
    public BackupData exportAll() {
        BackupData backup = new BackupData();
        backup.setCalcConfigs(calcConfigRepo.findAll());
        backup.setCalendarEvents(calendarEventRepo.findAll());
        backup.setTransLogs(transLogRepo.findAll());
        backup.setExerciseLogs(exerciseLogRepository.findAll());
        backup.setExerciseTypes(exerciseTypeRepository.findAll());
        backup.setMealLogs(mealLogRepository.findAll());
        backup.setMealTypes(mealTypeRepository.findAll());
        backup.setAppSettings(appSettingRepo.findAll());
        return backup;
    }

    @Operation(summary = "匯入資料", description = "匯入 BackupData JSON 以還原所有模組資料，匯入時會先清除既有資料再寫入")
    @ApiResponse(responseCode = "200", description = "匯入成功回傳 true，失敗回傳 false")
    @PostMapping("/import")
    public boolean importData(@RequestBody BackupData data) {
        try {
            if (data.getCalcConfigs() != null) {
                calcConfigRepo.deleteAll();
                calcConfigRepo.saveAll(data.getCalcConfigs());
            }
            if (data.getCalendarEvents() != null) {
                calendarEventRepo.deleteAll();
                calendarEventRepo.saveAll(data.getCalendarEvents());
            }
            if (data.getTransLogs() != null) {
                transLogRepo.deleteAll();
                transLogRepo.saveAll(data.getTransLogs());
            }
            if (data.getExerciseLogs() != null) {
                exerciseLogRepository.deleteAll();
                exerciseLogRepository.saveAll(data.getExerciseLogs());
            }
            if (data.getExerciseTypes() != null) {
                exerciseTypeRepository.deleteAll();
                exerciseTypeRepository.saveAll(data.getExerciseTypes());
            }
            if (data.getMealLogs() != null) {
                mealLogRepository.deleteAll();
                mealLogRepository.saveAll(data.getMealLogs());
            }
            if (data.getMealTypes() != null) {
                mealTypeRepository.deleteAll();
                mealTypeRepository.saveAll(data.getMealTypes());
            }
            if (data.getAppSettings() != null) {
                appSettingRepo.deleteAll();
                appSettingRepo.saveAll(data.getAppSettings());
            }
            return true;
        } catch (Exception e) {
            e.printStackTrace();
            return false;
        }
    }

    @Operation(summary = "取得應用程式設定", description = "取得所有應用程式設定，回傳鍵值對 Map")
    @ApiResponse(responseCode = "200", description = "回傳 Map<keyName, value>")
    @GetMapping("/app")
    public java.util.Map<String, String> getAppSettings() {
        java.util.List<myself.custom.asset.model.AppSetting> list = appSettingRepo.findAll();
        java.util.Map<String, String> map = new java.util.HashMap<>();
        for (myself.custom.asset.model.AppSetting s : list) {
            map.put(s.getKeyName(), s.getValue());
        }
        return map;
    }

    @Operation(summary = "儲存應用程式設定", description = "新增或更新一筆應用程式設定 (以 keyName 為主鍵)")
    @ApiResponse(responseCode = "200", description = "回傳儲存後的設定物件")
    @PostMapping("/app")
    public myself.custom.asset.model.AppSetting saveAppSetting(
            @RequestBody myself.custom.asset.model.AppSetting setting) {
        return appSettingRepo.save(setting);
    }
}
