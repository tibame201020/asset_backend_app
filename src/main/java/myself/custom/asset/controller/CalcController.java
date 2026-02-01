package myself.custom.asset.controller;

import myself.custom.asset.model.CalcConfig;
import myself.custom.asset.service.CalcService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/calc")
public class CalcController {
    @Autowired
    private CalcService calcService;

    @PostMapping("/insert")
    public boolean save(@RequestBody CalcConfig[] calcConfigs) {
        return calcService.save(calcConfigs);
    }

    @GetMapping("/query")
    public List<CalcConfig> queryAll() {
        return calcService.queryAllConfig();
    }

    @PostMapping("/queryById")
    public CalcConfig queryById(@RequestBody Long id) {
        return calcService.queryById(id);
    }

    @DeleteMapping("/delete/{id}")
    public boolean deleteById(@PathVariable Long id) {
        return calcService.deleteById(id);
    }

    @PutMapping("/update")
    public boolean updateCalcConfig(@RequestBody CalcConfig calcConfig) {
        return calcService.updateCalcConfig(calcConfig);
    }

}
