package myself.custom.asset.controller;

import myself.custom.asset.model.CalendarEvent;
import myself.custom.asset.service.CalendarService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/calendar")
public class CalendarController {
    @Autowired
    private CalendarService calendarService;

    @PostMapping("/add")
    public boolean addCalendarEvent(@RequestBody CalendarEvent calendarEvent) {
        return calendarService.addEvent(calendarEvent);
    }

    @PostMapping("/queryByMonth")
    public List<CalendarEvent> queryCalendarEventByMonth(@RequestBody int month) {
        return calendarService.queryEventByMonth(month);
    }

    @PostMapping("/queryByDateStr")
    public List<CalendarEvent> queryCalendarEventByDateStr(@RequestBody String dateStr) {
        return calendarService.queryEventByDateStr(dateStr);
    }

    @PostMapping("/queryEventsByRange")
    public List<CalendarEvent> queryCalendarEventBetweenDate(@RequestBody CalendarEvent calendarEvent) {
        if (calendarEvent.getStart() == null) {
            return null;
        }
        return calendarService.queryCalendarEventBetweenDate(calendarEvent.getStart(), calendarEvent.getEnd());
    }

    @DeleteMapping("/delete/{id}")
    public boolean deleteTransLogById(@PathVariable long id) {
        return calendarService.deleteEvent(id);
    }
}
