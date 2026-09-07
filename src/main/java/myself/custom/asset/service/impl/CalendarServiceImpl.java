package myself.custom.asset.service.impl;

import myself.custom.asset.model.CalendarEvent;
import myself.custom.asset.repo.CalendarEventRepo;
import myself.custom.asset.service.CalendarService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.sql.Timestamp;
import java.util.List;

@Service
public class CalendarServiceImpl implements CalendarService {
    @Autowired
    private CalendarEventRepo calendarEventRepo;

    @Override
    public CalendarEvent addEvent(CalendarEvent calendarEvent) {
        return calendarEventRepo.save(calendarEvent);
    }

    @Override
    public CalendarEvent updateEvent(Long id, CalendarEvent calendarEvent) {
        CalendarEvent existing = calendarEventRepo.findById(id)
                .orElseThrow(() -> new IllegalArgumentException("Calendar event not found: " + id));
        calendarEvent.setId(existing.getId());
        if (calendarEvent.getLogTime() == null) {
            calendarEvent.setLogTime(existing.getLogTime());
        }
        return calendarEventRepo.save(calendarEvent);
    }

    @Override
    public List<CalendarEvent> queryEventByMonth(int month) {
        return calendarEventRepo.findByMonthOrderByStart(month);
    }

    @Override
    public List<CalendarEvent> queryEventByDateStr(String dateStr) {
        return calendarEventRepo.findByDateStrOrderByStart(dateStr);
    }

    @Override
    public List<CalendarEvent> queryCalendarEventBetweenDate(Timestamp start, Timestamp end) {
        if (start.equals(end)) {
            end = new Timestamp(end.getTime() + 24 * 60 * 60 * 1000);
        }
        return calendarEventRepo.findAllByStartBetweenOrderByStart(start, end);
    }

    @Override
    public boolean deleteEvent(Long id) {
        calendarEventRepo.deleteById(id);
        return true;
    }
}
