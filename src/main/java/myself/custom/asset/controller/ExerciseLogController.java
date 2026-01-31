package myself.custom.asset.controller;

import myself.custom.asset.model.DateRange;
import myself.custom.asset.model.ExerciseLog;
import myself.custom.asset.service.ExerciseLogService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
@RequestMapping("/api/exercise")
public class ExerciseLogController {

    @Autowired
    private ExerciseLogService exerciseLogService;

    @RequestMapping("/save")
    public boolean saveExerciseLog(@RequestBody ExerciseLog exerciseLog) {
        return exerciseLogService.saveExerciseLog(exerciseLog);
    }

    @RequestMapping("/queryByDateRange")
    public List<ExerciseLog> queryByDateRange(@RequestBody DateRange dateRange) {
        return exerciseLogService.queryExerciseLogBetweenDate(dateRange);
    }

    @RequestMapping("/delete")
    public boolean deleteExerciseLogById(@RequestBody long id) {
        return exerciseLogService.deleteExerciseLogById(id);
    }
}
