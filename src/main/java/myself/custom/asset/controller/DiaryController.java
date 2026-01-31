package myself.custom.asset.controller;

import myself.custom.asset.model.DiaryLog;
import myself.custom.asset.service.DiaryService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.sql.Timestamp;
import java.util.List;

@RestController
@RequestMapping("/api/diary")
public class DiaryController {

    @Autowired
    private DiaryService diaryService;

    @GetMapping("/logs")
    public List<DiaryLog> getLogs(@RequestParam(required = false) Long start,
            @RequestParam(required = false) Long end) {
        if (start != null && end != null) {
            return diaryService.getLogsByRange(new Timestamp(start), new Timestamp(end));
        }
        return diaryService.getAllLogs();
    }

    @PostMapping("/log")
    public DiaryLog saveLog(@RequestBody DiaryLog log) {
        return diaryService.saveLog(log);
    }

    @DeleteMapping("/log/{id}")
    public void deleteLog(@PathVariable Long id) {
        diaryService.deleteLog(id);
    }
}
