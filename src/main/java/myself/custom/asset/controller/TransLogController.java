package myself.custom.asset.controller;

import myself.custom.asset.model.DateRange;
import myself.custom.asset.model.TransLog;
import myself.custom.asset.service.TransLogService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/trans")
public class TransLogController {

    @Autowired
    private TransLogService transLogService;

    @PostMapping("/save")
    public boolean saveTransLog(@RequestBody TransLog transLog) {
        return transLogService.saveTransLog(transLog);
    }

    @PostMapping("/queryByDateRange")
    public List<TransLog> queryByDateRange(@RequestBody DateRange dateRange) {
        return transLogService.queryTransLogBetweenDate(dateRange);
    }

    @DeleteMapping("/delete/{id}")
    public boolean deleteTransLogById(@PathVariable long id) {
        return transLogService.deleteTransLogById(id);
    }
}
