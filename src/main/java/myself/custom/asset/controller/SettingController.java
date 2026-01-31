package myself.custom.asset.controller;

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

    @RequestMapping("/del")
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
        return backup;
    }

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
            return true;
        } catch (Exception e) {
            e.printStackTrace();
            return false;
        }
    }
}
