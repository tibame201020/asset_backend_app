package myself.custom.asset.controller;

import myself.custom.asset.model.CalcConfig;
import myself.custom.asset.service.CalcService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
@RequestMapping("/api/calc")
public class CalcController {
    @Autowired
    private CalcService calcService;

    @RequestMapping("/insert")
    public boolean save(@RequestBody CalcConfig[] calcConfigs) {
        return calcService.save(calcConfigs);
    }

    @RequestMapping("/query")
    public List<CalcConfig> queryAll() {
        return calcService.queryAllConfig();
    }

    @RequestMapping("/queryById")
    public CalcConfig queryById(@RequestBody Long id) {
        return calcService.queryById(id);
    }
    @RequestMapping("/deleteById")
    public boolean deleteById(@RequestBody Long id) {
        return calcService.deleteById(id);
    }

    @RequestMapping("/update")
    public boolean updateCalcConfig(@RequestBody CalcConfig calcConfig) {
        return calcService.updateCalcConfig(calcConfig);
    }


}
