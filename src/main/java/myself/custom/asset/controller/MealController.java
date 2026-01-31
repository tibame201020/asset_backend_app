package myself.custom.asset.controller;

import myself.custom.asset.model.MealLog;
import myself.custom.asset.model.MealType;
import myself.custom.asset.service.MealService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.sql.Timestamp;
import java.util.List;

@RestController
@RequestMapping("/api/meal")
public class MealController {

    @Autowired
    private MealService mealService;

    // Logs
    @GetMapping("/logs")
    public List<MealLog> getLogs(@RequestParam(required = false) Long start, @RequestParam(required = false) Long end) {
        if (start != null && end != null) {
            return mealService.getLogsByRange(new Timestamp(start), new Timestamp(end));
        }
        return mealService.getAllLogs();
    }

    @PostMapping("/log")
    public MealLog saveLog(@RequestBody MealLog log) {
        return mealService.saveLog(log);
    }

    @DeleteMapping("/log/{id}")
    public void deleteLog(@PathVariable Long id) {
        mealService.deleteLog(id);
    }

    // Types
    @GetMapping("/types")
    public List<MealType> getAllTypes() {
        return mealService.getAllTypes();
    }

    @PostMapping("/type")
    public MealType saveType(@RequestBody MealType type) {
        return mealService.saveType(type);
    }

    @DeleteMapping("/type/{id}")
    public void deleteType(@PathVariable Long id) {
        mealService.deleteType(id);
    }
}
