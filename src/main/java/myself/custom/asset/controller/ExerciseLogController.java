package myself.custom.asset.controller;

import myself.custom.asset.model.DateRange;
import myself.custom.asset.model.ExerciseLog;
import myself.custom.asset.service.ExerciseLogService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/exercise")
public class ExerciseLogController {

    @Autowired
    private ExerciseLogService exerciseLogService;

    @PostMapping("/save")
    public boolean saveExerciseLog(@RequestBody ExerciseLog exerciseLog) {
        return exerciseLogService.saveExerciseLog(exerciseLog);
    }

    @PostMapping("/queryByDateRange")
    public List<ExerciseLog> queryByDateRange(@RequestBody DateRange dateRange) {
        return exerciseLogService.queryExerciseLogBetweenDate(dateRange);
    }

    @DeleteMapping("/delete/{id}")
    public boolean deleteExerciseLogById(@PathVariable long id) {
        return exerciseLogService.deleteExerciseLogById(id);
    }
}
