package myself.custom.asset.repo;

import myself.custom.asset.model.ExerciseLog;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.sql.Timestamp;
import java.util.List;

@Repository
public interface ExerciseLogRepository extends JpaRepository<ExerciseLog, Long> {
    List<ExerciseLog> findAllByTransDateBetweenOrderByTransDateDesc(Timestamp start, Timestamp end);
}
