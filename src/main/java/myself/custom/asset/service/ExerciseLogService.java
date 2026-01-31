package myself.custom.asset.service;

import myself.custom.asset.model.DateRange;
import myself.custom.asset.model.ExerciseLog;
import myself.custom.asset.repo.ExerciseLogRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.sql.Timestamp;
import java.util.List;

@Service
public class ExerciseLogService {

    @Autowired
    private ExerciseLogRepository exerciseLogRepository;

    public boolean saveExerciseLog(ExerciseLog exerciseLog) {
        if (exerciseLog.getLogTime() == null) {
            exerciseLog.setLogTime(new Timestamp(System.currentTimeMillis()));
        }
        exerciseLogRepository.save(exerciseLog);
        return true;
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
