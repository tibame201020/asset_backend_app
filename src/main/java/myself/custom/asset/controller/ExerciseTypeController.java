package myself.custom.asset.controller;

import myself.custom.asset.model.ExerciseType;
import myself.custom.asset.service.ExerciseTypeService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/exercise-type")
@CrossOrigin
public class ExerciseTypeController {

    @Autowired
    private ExerciseTypeService exerciseTypeService;

    @GetMapping("/all")
    public List<ExerciseType> getAll() {
        return exerciseTypeService.getAll();
    }

    @PostMapping("/save")
    public ExerciseType save(@RequestBody ExerciseType type) {
        return exerciseTypeService.save(type);
    }

    @DeleteMapping("/delete/{id}")
    public boolean delete(@PathVariable Long id) {
        try {
            exerciseTypeService.delete(id);
            return true;
        } catch (Exception e) {
            return false;
        }
    }
}
