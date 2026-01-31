package myself.custom.asset.repo;

import myself.custom.asset.model.MealLog;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.sql.Timestamp;
import java.util.List;

@Repository
public interface MealLogRepository extends JpaRepository<MealLog, Long> {
    List<MealLog> findByTransDateBetweenOrderByTransDateDesc(Timestamp start, Timestamp end);
}
