package myself.custom.asset.service;

import myself.custom.asset.enums.RecordAction;
import myself.custom.asset.model.Asset;
import myself.custom.asset.model.domain.Stock;

import java.util.List;
import java.util.Map;

public interface AssetService {
    Map<String, Object> operatingStock(String lineId, Stock stock, RecordAction recordAction);

    Asset getLatestAsset(String lineId);

    List<Asset> getAssets(String lineId);
}
