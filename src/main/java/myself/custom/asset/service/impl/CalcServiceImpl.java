package myself.custom.asset.service.impl;

import myself.custom.asset.model.CalcConfig;
import myself.custom.asset.repo.CalcConfigRepo;
import myself.custom.asset.service.CalcService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.Arrays;
import java.util.List;

@Service
public class CalcServiceImpl implements CalcService {
    @Autowired
    private CalcConfigRepo calcConfigRepo;

    @Override
    public boolean save(CalcConfig[] calcConfigs) {
        try {
            Arrays.stream(calcConfigs).forEach(calcConfig -> {
                calcConfig.setId(null);
                System.out.println(calcConfig);
                calcConfigRepo.save(calcConfig);
            });
            return true;
        } catch (Exception e) {
            return false;
        }
    }

    @Override
    public List<CalcConfig> queryAllConfig() {
        return calcConfigRepo.findAll();
    }

    @Override
    public CalcConfig queryById(Long id) {
        return calcConfigRepo.findById(id).orElse(new CalcConfig());
    }

    @Override
    public boolean deleteById(Long id) {
        try {
            calcConfigRepo.deleteById(id);
            return true;
        } catch (Exception e) {
            return false;
        }
    }
}
