package myself.custom.asset.controller;

import myself.custom.asset.model.BackupData;
import myself.custom.asset.repo.CalcConfigRepo;
import myself.custom.asset.repo.CalendarEventRepo;
import myself.custom.asset.repo.TransLogRepo;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/api/setting")
public class SettingController {

    @Autowired
    private CalcConfigRepo calcConfigRepo;
    @Autowired
    private CalendarEventRepo calendarEventRepo;
    @Autowired
    private TransLogRepo transLogRepo;

    @RequestMapping("/del")
    public boolean deleteAll(@RequestBody java.util.Map<String, String> payload) {
        String target = payload.get("target");
        System.out.println("Delete All Request for target: [" + target + "]");
        try {
            if (target == null)
                return false;
            String cleanTarget = target.trim().toLowerCase();
            switch (cleanTarget) {
                case "deposit":
                    transLogRepo.deleteAll();
                    System.out.println("Deleted all deposit data");
                    return true;
                case "calc":
                    calcConfigRepo.deleteAll();
                    System.out.println("Deleted all calc data");
                    return true;
                case "calendar":
                    calendarEventRepo.deleteAll();
                    System.out.println("Deleted all calendar data");
                    return true;
                default:
                    System.out.println("Unknown target: " + cleanTarget);
                    return false;
            }
        } catch (Exception e) {
            e.printStackTrace();
            return false;
        }
    }

    @GetMapping("/export")
    public BackupData exportAll() {
        BackupData backup = new BackupData();
        backup.setCalcConfigs(calcConfigRepo.findAll());
        backup.setCalendarEvents(calendarEventRepo.findAll());
        backup.setTransLogs(transLogRepo.findAll());
        return backup;
    }

    @PostMapping("/import")
    public boolean importData(@RequestBody BackupData data) {
        try {
            if (data.getCalcConfigs() != null) {
                calcConfigRepo.deleteAll();
                calcConfigRepo.saveAll(data.getCalcConfigs());
            }
            if (data.getCalendarEvents() != null) {
                calendarEventRepo.deleteAll();
                calendarEventRepo.saveAll(data.getCalendarEvents());
            }
            if (data.getTransLogs() != null) {
                transLogRepo.deleteAll();
                transLogRepo.saveAll(data.getTransLogs());
            }
            return true;
        } catch (Exception e) {
            e.printStackTrace();
            return false;
        }
    }
}
