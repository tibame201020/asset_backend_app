package myself.custom.asset.controller;

import myself.custom.asset.enums.RecordAction;
import myself.custom.asset.model.Asset;
import myself.custom.asset.model.requestBean.ReqBean;
import myself.custom.asset.service.AssetService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;
import java.util.Map;

@RestController
@RequestMapping("/api")
public class AssetController {
    @Autowired
    private AssetService assetService;

    @RequestMapping("/buyStock")
    public Map<String, Object> buyStock(@RequestBody ReqBean reqBean) {
        return assetService.operatingStock(reqBean.getLineId(), reqBean.getStock(), RecordAction.BUY);
    }

    @RequestMapping("/sellStock")
    public Map<String, Object> sellStock(@RequestBody ReqBean reqBean) {
        return assetService.operatingStock(reqBean.getLineId(), reqBean.getStock(), RecordAction.SELL);
    }

    @RequestMapping("/getLatestAsset")
    public Asset getLatestAsset(@RequestBody String lineId) {
        return assetService.getLatestAsset(lineId);
    }

    @RequestMapping("/getAssets")
    public List<Asset> getAssets(@RequestBody String lineId) {
        return assetService.getAssets(lineId);
    }
}
