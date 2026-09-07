package myself.custom.asset.service;

import jakarta.annotation.PostConstruct;
import lombok.extern.slf4j.Slf4j;
import myself.custom.asset.model.ExerciseType;
import myself.custom.asset.repo.ExerciseLogRepository;
import myself.custom.asset.repo.ExerciseTypeRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

@Service
@Slf4j
public class ExerciseTypeService {

    @Autowired
    private ExerciseTypeRepository exerciseTypeRepository;

    @Autowired
    private ExerciseLogRepository exerciseLogRepository;

    @PostConstruct
    public void seedDefaults() {
        if (exerciseTypeRepository.count() == 0) {
            log.info("Seeding default exercise types...");
            List<ExerciseType> defaults = new ArrayList<>();
            defaults.add(createType("Jogging", "🏃", 30.0, 595.0));
            defaults.add(createType("Cycling", "🚴", 45.0, 525.0));
            defaults.add(createType("Walking", "🚶", 30.0, 245.0));
            defaults.add(createType("Fitness", "💪", 60.0, 350.0));
            defaults.add(createType("Yoga", "🧘", 60.0, 175.0));
            defaults.add(createType("Swimming", "🏊", 30.0, 490.0));
            defaults.add(createType("Basketball", "🏀", 60.0, 560.0));
            exerciseTypeRepository.saveAll(defaults);
        }
    }

    private ExerciseType createType(String name, String icon, Double duration, Double kcalPerHour) {
        ExerciseType type = new ExerciseType();
        type.setName(name);
        type.setIcon(icon);
        type.setDefaultDuration(duration);
        type.setKcalPerHour(kcalPerHour);
        return type;
    }

    public List<ExerciseType> getAll() {
        return exerciseTypeRepository.findAll();
    }

    public ExerciseType save(ExerciseType type) {
        return exerciseTypeRepository.save(type);
    }

    public void delete(Long id) {
        if (exerciseLogRepository.existsByExerciseTypeId(id)) {
            throw new IllegalArgumentException("Exercise type is still referenced by exercise logs: " + id);
        }
        exerciseTypeRepository.deleteById(id);
    }

    public Optional<ExerciseType> getById(Long id) {
        return exerciseTypeRepository.findById(id);
    }
}
