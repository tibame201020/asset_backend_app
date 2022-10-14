package myself.custom.asset.service;

import myself.custom.asset.model.CalcConfig;

import java.util.List;

public interface CalcService {
    boolean save(CalcConfig[] calcConfigs);

    List<CalcConfig> queryAllConfig();

    CalcConfig queryById(Long id);

    boolean deleteById(Long id);
}
