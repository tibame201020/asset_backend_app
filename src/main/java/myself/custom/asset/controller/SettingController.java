package myself.custom.asset.controller;

import myself.custom.asset.repo.CalcConfigRepo;
import myself.custom.asset.repo.CalendarEventRepo;
import myself.custom.asset.repo.TransLogRepo;
import org.springframework.beans.factory.annotation.Autowired;
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
    public boolean deleteAll(@RequestBody String target) {
        try {
            switch (target) {
                case "deposit":
                    transLogRepo.deleteAll();
                    return true;
                case "calc":
                    calcConfigRepo.deleteAll();
                    return true;
                case "calendar":
                    calendarEventRepo.deleteAll();
                    return true;
                default:
                    return false;
            }
        } catch (Exception e) {
            return false;
        }
    }
}
