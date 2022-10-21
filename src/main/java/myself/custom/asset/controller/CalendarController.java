package myself.custom.asset.controller;

import myself.custom.asset.model.CalendarEvent;
import myself.custom.asset.service.CalendarService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
@RequestMapping("/api/calendar")
public class CalendarController {
    @Autowired
    private CalendarService calendarService;

    @RequestMapping("/add")
    public boolean addCalendarEvent(@RequestBody CalendarEvent calendarEvent) {
        System.out.println(calendarEvent);
        return calendarService.addEvent(calendarEvent);
    }

    @RequestMapping("/queryByMonth")
    public List<CalendarEvent> queryCalendarEventByMonth(@RequestBody int month) {
        System.out.println(month);
        return calendarService.queryEventByMonth(month);
    }

    @RequestMapping("/queryByDateStr")
    public List<CalendarEvent> queryCalendarEventByDateStr(@RequestBody String dateStr) {
        System.out.println(dateStr);
        return calendarService.queryEventByDateStr(dateStr);
    }

    @RequestMapping("/queryEventsByRange")
    public List<CalendarEvent> queryCalendarEventBetweenDate(@RequestBody CalendarEvent calendarEvent) {
        System.out.println(calendarEvent);
        return calendarService.queryCalendarEventBetweenDate(calendarEvent.getStart(), calendarEvent.getEnd());
    }
}
