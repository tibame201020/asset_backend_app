package myself.custom.asset;

import myself.custom.asset.model.ExerciseLog;
import myself.custom.asset.model.ExerciseType;
import myself.custom.asset.model.MealType;
import myself.custom.asset.model.TransLog;
import myself.custom.asset.repo.ExerciseTypeRepository;
import myself.custom.asset.repo.MealTypeRepository;
import myself.custom.asset.service.ExerciseLogService;
import myself.custom.asset.service.TransLogService;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import java.sql.Timestamp;
import java.util.Set;
import java.util.stream.Collectors;

import static org.junit.jupiter.api.Assertions.*;

@SpringBootTest(properties = {
        "spring.datasource.url=jdbc:h2:mem:record-contract;DB_CLOSE_DELAY=-1;NON_KEYWORDS=VALUE,KEY,END,START,MONTH,DATE,NAME,CATEGORY",
        "spring.jpa.hibernate.ddl-auto=create-drop"
})
class RecordTaxonomyContractTests {

    @Autowired
    private MealTypeRepository mealTypeRepository;

    @Autowired
    private ExerciseTypeRepository exerciseTypeRepository;

    @Autowired
    private ExerciseLogService exerciseLogService;

    @Autowired
    private TransLogService transLogService;

    @Test
    void mealTypesUseMealPeriodTaxonomy() {
        Set<String> names = mealTypeRepository.findAll().stream().map(MealType::getName).collect(Collectors.toSet());
        assertEquals(Set.of("早餐", "午餐", "晚餐", "消夜", "零食", "飲料", "其他"), names);
    }

    @Test
    void exerciseCaloriesCanBeDerivedFromCanonicalType() {
        ExerciseType jogging = exerciseTypeRepository.findByName("Jogging").orElseThrow();
        ExerciseLog log = new ExerciseLog();
        log.setExerciseTypeId(jogging.getId());
        log.setExerciseName("戶外慢跑");
        log.setDuration(30.0);
        log.setTransDate(new Timestamp(System.currentTimeMillis()));

        ExerciseLog saved = exerciseLogService.saveExerciseLog(log);
        assertNotNull(saved.getId());
        assertEquals(297.5, saved.getCalories(), 0.001);
        assertEquals("戶外慢跑", saved.getExerciseName());
    }

    @Test
    void transactionRejectsNonCanonicalCategory() {
        TransLog log = new TransLog();
        log.setType("支出");
        log.setCategory("飲食");
        log.setName("牛肉麵");
        log.setValue(180.0);
        log.setTransDate(new Timestamp(System.currentTimeMillis()));

        IllegalArgumentException error = assertThrows(IllegalArgumentException.class,
                () -> transLogService.saveTransLog(log));
        assertTrue(error.getMessage().contains("Invalid category"));
    }

    @Test
    void transactionAcceptsCanonicalCategoryAndPositiveAmount() {
        TransLog log = new TransLog();
        log.setType("支出");
        log.setCategory("食");
        log.setName("牛肉麵");
        log.setValue(180.0);
        log.setTransDate(new Timestamp(System.currentTimeMillis()));

        TransLog saved = transLogService.saveTransLog(log);
        assertNotNull(saved.getId());
        assertEquals("食", saved.getCategory());
    }
}
