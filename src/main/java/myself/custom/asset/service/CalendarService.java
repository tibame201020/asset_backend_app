package myself.custom.asset.service;

import myself.custom.asset.model.CalendarEvent;

import java.sql.Timestamp;
import java.util.List;

public interface CalendarService {
    boolean addEvent(CalendarEvent calendarEvent);

    List<CalendarEvent> queryEventByMonth(int month);

    List<CalendarEvent> queryEventByDateStr(String dateStr);

    List<CalendarEvent> queryCalendarEventBetweenDate(Timestamp start, Timestamp end);
}
