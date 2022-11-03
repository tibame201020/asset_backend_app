package myself.custom.asset.repo;

import myself.custom.asset.model.TransLog;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.sql.Timestamp;
import java.util.List;

@Repository
public interface TransLogRepo extends JpaRepository<TransLog, Long> {
    List<TransLog> findByTransDateBetweenOrderByTransDate(Timestamp start, Timestamp end);
}
