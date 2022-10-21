package myself.custom.asset.repo;

import myself.custom.asset.model.CalendarEvent;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.sql.Timestamp;
import java.util.List;

@Repository
public interface CalendarEventRepo extends JpaRepository<CalendarEvent, Long> {
    List<CalendarEvent> findByMonthOrderByStart(int month);
    List<CalendarEvent> findByDateStrOrderByStart(String dateStr);
    List<CalendarEvent> findAllByStartBetweenOrderByStart(Timestamp start, Timestamp end);
}
