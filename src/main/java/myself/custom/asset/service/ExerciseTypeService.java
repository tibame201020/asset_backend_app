package myself.custom.asset.service;

import jakarta.annotation.PostConstruct;
import lombok.extern.slf4j.Slf4j;
import myself.custom.asset.model.ExerciseType;
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

    @PostConstruct
    public void seedDefaults() {
        if (exerciseTypeRepository.count() == 0) {
            log.info("Seeding default exercise types...");
            List<ExerciseType> defaults = new ArrayList<>();

            // Calculated as MET * 70kg
            defaults.add(createType("Jogging", "🏃", 30.0, 595.0)); // 8.5 * 70
            defaults.add(createType("Cycling", "🚴", 45.0, 525.0)); // 7.5 * 70
            defaults.add(createType("Walking", "🚶", 30.0, 245.0)); // 3.5 * 70
            defaults.add(createType("Fitness", "💪", 60.0, 350.0)); // 5.0 * 70
            defaults.add(createType("Yoga", "🧘", 60.0, 175.0)); // 2.5 * 70
            defaults.add(createType("Swimming", "🏊", 30.0, 490.0)); // 7.0 * 70
            defaults.add(createType("Basketball", "🏀", 60.0, 560.0)); // 8.0 * 70

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
        exerciseTypeRepository.deleteById(id);
    }

    public Optional<ExerciseType> getById(Long id) {
        return exerciseTypeRepository.findById(id);
    }
}
