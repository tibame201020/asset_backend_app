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
import java.util.Set;

@Service
@Slf4j
public class MealService {

    private static final Set<String> CANONICAL_MEAL_TYPES = Set.of(
            "早餐", "午餐", "晚餐", "消夜", "零食", "飲料", "其他");

    @Autowired
    private MealLogRepository mealLogRepository;

    @Autowired
    private MealTypeRepository mealTypeRepository;

    @PostConstruct
    public void seedDefaults() {
        List<MealType> existing = mealTypeRepository.findAll();
        boolean canonical = existing.size() == CANONICAL_MEAL_TYPES.size()
                && existing.stream().map(MealType::getName).collect(java.util.stream.Collectors.toSet())
                        .equals(CANONICAL_MEAL_TYPES);

        if (canonical) {
            return;
        }

        // Safe migration from the old food-category taxonomy. Only replace it when
        // there are no meal logs that could reference the existing IDs.
        if (!existing.isEmpty() && mealLogRepository.count() > 0) {
            log.warn("Meal types use a legacy taxonomy but meal logs exist; preserving types to avoid breaking references.");
            return;
        }

        if (!existing.isEmpty()) {
            log.info("Replacing legacy meal type taxonomy with meal-period taxonomy...");
            mealTypeRepository.deleteAll();
        } else {
            log.info("Seeding default meal period types...");
        }

        List<MealType> defaults = new ArrayList<>();
        defaults.add(createType("早餐", "🍳"));
        defaults.add(createType("午餐", "🍱"));
        defaults.add(createType("晚餐", "🍽️"));
        defaults.add(createType("消夜", "🌙"));
        defaults.add(createType("零食", "🍪"));
        defaults.add(createType("飲料", "🥤"));
        defaults.add(createType("其他", "🍴"));
        mealTypeRepository.saveAll(defaults);
    }

    private MealType createType(String name, String icon) {
        MealType type = new MealType();
        type.setName(name);
        type.setIcon(icon);
        return type;
    }

    public List<MealLog> getAllLogs() {
        return mealLogRepository.findAll();
    }

    public List<MealLog> getLogsByRange(Timestamp start, Timestamp end) {
        return mealLogRepository.findByTransDateBetweenOrderByTransDateDesc(start, end);
    }

    public MealLog saveLog(MealLog log) {
        if (log.getMealTypeId() != null && !mealTypeRepository.existsById(log.getMealTypeId())) {
            throw new IllegalArgumentException("Meal type not found: " + log.getMealTypeId());
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
        if (mealLogRepository.existsByMealTypeId(id)) {
            throw new IllegalArgumentException("Meal type is still referenced by meal logs: " + id);
        }
        mealTypeRepository.deleteById(id);
    }

    public Optional<MealType> getTypeById(Long id) {
        return mealTypeRepository.findById(id);
    }
}
