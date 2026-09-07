package myself.custom.asset.controller;

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.Parameter;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.tags.Tag;
import myself.custom.asset.model.CalendarEvent;
import myself.custom.asset.service.CalendarService;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/calendar")
@Tag(name = "行事曆", description = "管理行事曆事件 (CalendarEvent)")
public class CalendarController {
    @Autowired
    private CalendarService calendarService;

    @Operation(summary = "新增行事曆事件", description = "新增一筆行事曆事件")
    @ApiResponse(responseCode = "200", description = "回傳新增後的行事曆事件物件（含 ID）")
    @PostMapping("/add")
    public CalendarEvent addCalendarEvent(@RequestBody @Valid CalendarEvent calendarEvent) {
        return calendarService.addEvent(calendarEvent);
    }

    @Operation(summary = "更新行事曆事件", description = "依 ID 更新既有行事曆事件")
    @ApiResponse(responseCode = "200", description = "回傳更新後的行事曆事件物件")
    @PutMapping("/update/{id}")
    public CalendarEvent updateCalendarEvent(
            @Parameter(description = "行事曆事件 ID", required = true, example = "1") @PathVariable Long id,
            @RequestBody @Valid CalendarEvent calendarEvent) {
        return calendarService.updateEvent(id, calendarEvent);
    }

    @Operation(summary = "依月份查詢事件", description = "根據月份數字 (1-12) 查詢行事曆事件")
    @ApiResponse(responseCode = "200", description = "回傳該月份的事件列表")
    @PostMapping("/queryByMonth")
    public List<CalendarEvent> queryCalendarEventByMonth(@RequestBody int month) {
        return calendarService.queryEventByMonth(month);
    }

    @Operation(summary = "依日期字串查詢事件", description = "根據日期字串 (如 '2026-02-19') 查詢行事曆事件")
    @ApiResponse(responseCode = "200", description = "回傳該日期的事件列表")
    @PostMapping("/queryByDateStr")
    public List<CalendarEvent> queryCalendarEventByDateStr(@RequestBody String dateStr) {
        return calendarService.queryEventByDateStr(dateStr);
    }

    @Operation(summary = "依時間區間查詢事件", description = "根據起始與結束時間查詢區間內的行事曆事件")
    @ApiResponse(responseCode = "200", description = "回傳區間內的事件列表")
    @PostMapping("/queryEventsByRange")
    public List<CalendarEvent> queryCalendarEventBetweenDate(@RequestBody CalendarEvent calendarEvent) {
        if (calendarEvent.getStart() == null) {
            return null;
        }
        return calendarService.queryCalendarEventBetweenDate(calendarEvent.getStart(), calendarEvent.getEnd());
    }

    @Operation(summary = "刪除行事曆事件", description = "根據 ID 刪除指定的行事曆事件")
    @ApiResponse(responseCode = "200", description = "刪除成功回傳 true，失敗回傳 false")
    @DeleteMapping("/delete/{id}")
    public boolean deleteCalendarEventById(
            @Parameter(description = "行事曆事件 ID", required = true, example = "1") @PathVariable long id) {
        return calendarService.deleteEvent(id);
    }
}
