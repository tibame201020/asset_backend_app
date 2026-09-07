package myself.custom.asset.service;

import myself.custom.asset.model.DateRange;
import myself.custom.asset.model.ExerciseLog;
import myself.custom.asset.model.ExerciseType;
import myself.custom.asset.repo.ExerciseLogRepository;
import myself.custom.asset.repo.ExerciseTypeRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.sql.Timestamp;
import java.util.List;

@Service
public class ExerciseLogService {

    @Autowired
    private ExerciseLogRepository exerciseLogRepository;

    @Autowired
    private ExerciseTypeRepository exerciseTypeRepository;

    public ExerciseLog saveExerciseLog(ExerciseLog exerciseLog) {
        if (exerciseLog.getExerciseTypeId() != null) {
            ExerciseType type = exerciseTypeRepository.findById(exerciseLog.getExerciseTypeId())
                    .orElseThrow(() -> new IllegalArgumentException(
                            "Exercise type not found: " + exerciseLog.getExerciseTypeId()));
            if (exerciseLog.getCalories() == null && type.getKcalPerHour() != null) {
                exerciseLog.setCalories(type.getKcalPerHour() * exerciseLog.getDuration() / 60.0);
            }
        }
        if (exerciseLog.getLogTime() == null) {
            exerciseLog.setLogTime(new Timestamp(System.currentTimeMillis()));
        }
        return exerciseLogRepository.save(exerciseLog);
    }

    public List<ExerciseLog> queryExerciseLogBetweenDate(DateRange dateRange) {
        return exerciseLogRepository.findAllByTransDateBetweenOrderByTransDateDesc(
                dateRange.getStart(), dateRange.getEnd());
    }

    public boolean deleteExerciseLogById(long id) {
        exerciseLogRepository.deleteById(id);
        return true;
    }
}
