package myself.custom.asset.repo;

import myself.custom.asset.model.DiaryLog;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.sql.Timestamp;
import java.util.List;

@Repository
public interface DiaryLogRepository extends JpaRepository<DiaryLog, Long> {
    List<DiaryLog> findByTransDateBetweenOrderByTransDateDesc(Timestamp start, Timestamp end);
}
