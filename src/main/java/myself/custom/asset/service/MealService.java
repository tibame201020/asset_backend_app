package myself.custom.asset.service;

import jakarta.annotation.PostConstruct;
import lombok.extern.slf4j.Slf4j;
import myself.custom.asset.model.MealLog;
import myself.custom.asset.model.MealType;
import myself.custom.asset.repo.MealLogRepository;
import myself.custom.asset.repo.MealTypeRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.sql.Timestamp;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

@Service
@Slf4j
public class MealService {

    @Autowired
    private MealLogRepository mealLogRepository;

    @Autowired
    private MealTypeRepository mealTypeRepository;

    @PostConstruct
    public void seedDefaults() {
        if (mealTypeRepository.count() == 0) {
            log.info("Seeding default meal types...");
            List<MealType> defaults = new ArrayList<>();

            defaults.add(createType("Rice", "🍚", 200.0));
            defaults.add(createType("Noodles", "🍜", 400.0));
            defaults.add(createType("Bread", "🍞", 150.0));
            defaults.add(createType("Salad", "🥗", 100.0));
            defaults.add(createType("Meat", "🥩", 300.0));
            defaults.add(createType("Coffee", "☕", 50.0));
            defaults.add(createType("Fruit", "🍎", 80.0));

            mealTypeRepository.saveAll(defaults);
        }
    }

    private MealType createType(String name, String icon, Double defaultCalories) {
        MealType type = new MealType();
        type.setName(name);
        type.setIcon(icon);
        type.setDefaultCalories(defaultCalories);
        return type;
    }

    public List<MealLog> getAllLogs() {
        return mealLogRepository.findAll();
    }

    public List<MealLog> getLogsByRange(Timestamp start, Timestamp end) {
        return mealLogRepository.findByTransDateBetweenOrderByTransDateDesc(start, end);
    }

    public MealLog saveLog(MealLog log) {
        if (log.getMealTypeId() != null) {
            MealType type = mealTypeRepository.findById(log.getMealTypeId())
                    .orElseThrow(() -> new IllegalArgumentException("Meal type not found: " + log.getMealTypeId()));
            if (log.getCalories() == null) {
                log.setCalories(type.getDefaultCalories());
            }
        }
        if (log.getLogTime() == null) {
            log.setLogTime(new Timestamp(System.currentTimeMillis()));
        }
        return mealLogRepository.save(log);
    }

    public void deleteLog(Long id) {
        mealLogRepository.deleteById(id);
    }

    public List<MealType> getAllTypes() {
        return mealTypeRepository.findAll();
    }

    public MealType saveType(MealType type) {
        return mealTypeRepository.save(type);
    }

    public void deleteType(Long id) {
        mealTypeRepository.deleteById(id);
    }

    public Optional<MealType> getTypeById(Long id) {
        return mealTypeRepository.findById(id);
    }
}
