package myself.custom.asset.controller;

import myself.custom.asset.model.DateRange;
import myself.custom.asset.model.TransLog;
import myself.custom.asset.service.TransLogService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
@RequestMapping("/api/trans")
public class TransLogController {

    @Autowired
    private TransLogService transLogService;

    @RequestMapping("/save")
    public boolean saveTransLog(@RequestBody TransLog transLog) {
        return transLogService.saveTransLog(transLog);
    }

    @RequestMapping("/queryByDateRange")
    public List<TransLog> queryByDateRange(@RequestBody DateRange dateRange) {
        return transLogService.queryTransLogBetweenDate(dateRange);
    }

    @RequestMapping("/delete")
    public boolean deleteTransLogById(@RequestBody long id) {
        return transLogService.deleteTransLogById(id);
    }
}
