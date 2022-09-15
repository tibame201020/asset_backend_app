package myself.custom.asset.service.impl;

import myself.custom.asset.enums.RecordAction;
import myself.custom.asset.model.Asset;
import myself.custom.asset.model.StockRecord;
import myself.custom.asset.model.domain.Stock;
import myself.custom.asset.model.pk.CustomPk;
import myself.custom.asset.repo.AssetRepo;
import myself.custom.asset.repo.StockRecordRepo;
import myself.custom.asset.service.AssetService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.util.CollectionUtils;

import java.util.Arrays;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

@Service
public class AssetServiceImpl implements AssetService {
    @Autowired
    private AssetRepo assetRepo;
    @Autowired
    private StockRecordRepo stockRecordRepo;


    @Override
    public Map<String, Object> operatingStock(String lineId, Stock stock, RecordAction recordAction) {
        switch (recordAction) {
            case BUY:
                return buyStock(lineId, stock);
            case SELL:
                return sellStock(lineId, stock);
            default:
                return null;
        }
    }

    private Map<String, Object> buyStock(String lineId, Stock stock) {
        Map<String, Object> rtnMap = new HashMap<>();
        Asset asset = getLatestAsset(lineId);

        if (!checkBuyStock(asset, stock)) {
            rtnMap.put("status", false);
            rtnMap.put("result", "remain-cash not enough");
        } else {
            StockRecord stockRecord = createStockRecord(lineId, stock, RecordAction.BUY);
            Asset assetRecord = calacAsset(asset, stock, RecordAction.BUY);

            stockRecordRepo.save(stockRecord);
            assetRepo.save(assetRecord);

            rtnMap.put("status", true);
            rtnMap.put("result", asset);
        }

        return rtnMap;
    }

    private boolean checkBuyStock(Asset asset, Stock stock) {
        return asset.getCash() >= (stock.getPrice() * stock.getQuantity());
    }

    private Map<String, Object> sellStock(String lineId, Stock stock) {
        Map<String, Object> rtnMap = new HashMap<>();

        Asset asset = getLatestAsset(lineId);
        if (!(checkSellStock(asset, stock))) {
            rtnMap.put("status", false);
            rtnMap.put("result", "u don't have enough stock volume to sell");
        } else {
            StockRecord stockRecord = createStockRecord(lineId, stock, RecordAction.SELL);
            Asset assetRecord = calacAsset(asset, stock, RecordAction.SELL);

            stockRecordRepo.save(stockRecord);
            assetRepo.save(assetRecord);

            rtnMap.put("status", true);
            rtnMap.put("result", asset);
        }
        return rtnMap;
    }

    private Asset calacAsset(Asset asset, Stock stock, RecordAction recordAction) {
        CustomPk customPk = new CustomPk();
        customPk.setLineId(asset.getCustomPk().getLineId());

        Asset rtnAsset = new Asset();
        rtnAsset.setCustomPk(customPk);

        switch (recordAction) {
            case SELL:
                Stock[] stocksToSell = Arrays.stream(asset.getStocks()).peek(stock1 -> {
                    if (stock1.getCode().equals(stock.getCode())) {
                        stock1.setPrice(stock.getPrice());
                        stock1.setQuantity(stock1.getQuantity() - stock.getQuantity());
                    }
                }).collect(Collectors.toList()).toArray(new Stock[asset.getStocks().length]);
                rtnAsset.setStocks(stocksToSell);
                rtnAsset.setCash(asset.getCash() + (stock.getPrice() * stock.getQuantity()));
                break;
            case BUY:
                Stock[] stocks = asset.getStocks();
                if (null == stocks || stocks.length == 0) {
                    asset.setStocks(new Stock[]{});
                }

                List<Stock> stockList = Arrays.stream(asset.getStocks()).filter(stock1 -> stock1.getCode().equals(stock.getCode())).collect(Collectors.toList());
                if (stockList.size() > 0) {
                    Stock[] stocksToBuy = Arrays.stream(asset.getStocks()).peek(stock1 -> {
                        if (stock1.getCode().equals(stock.getCode())) {
                            stock1.setPrice(stock.getPrice());
                            stock1.setQuantity(stock1.getQuantity() + stock.getQuantity());
                        }
                    }).collect(Collectors.toList()).toArray(new Stock[asset.getStocks().length]);
                    rtnAsset.setStocks(stocksToBuy);
                } else {
                    stockList = Arrays.stream(asset.getStocks()).collect(Collectors.toList());
                    stockList.add(stock);
                    Stock[] stocksToBuy = stockList.toArray(new Stock[stockList.size()]);
                    rtnAsset.setStocks(stocksToBuy);
                }
                rtnAsset.setCash(asset.getCash() - (stock.getPrice() * stock.getQuantity()));
                break;
            default:
                break;
        }

        return rtnAsset;
    }

    private StockRecord createStockRecord(String lineId, Stock stock, RecordAction recordAction) {
        CustomPk customPk = new CustomPk();
        customPk.setLineId(lineId);

        StockRecord stockRecord = new StockRecord();
        stockRecord.setCustomPk(customPk);
        stockRecord.setStock(stock);
        stockRecord.setAction(recordAction);

        return stockRecord;
    }


    // get asset
    @Override
    public Asset getLatestAsset(String lineId) {
        List<Asset> assetList = getAssets(lineId);

        return CollectionUtils.isEmpty(assetList) ? null : assetList.get(0);
    }

    @Override
    public List<Asset> getAssets(String lineId) {
        return assetRepo.findByCustomPkLineIdOrderByCustomPkLogTimeDesc(lineId);
    }

    private boolean checkSellStock(Asset asset, Stock targetStock) {
        Stock[] stocks = asset.getStocks();
        String stockCode = targetStock.getCode();
        Stock targetStockInAsset = Arrays.stream(stocks).filter(stock -> stockCode.equals(stock.getCode())).findAny().orElse(new Stock(stockCode, 0, 0));
        return targetStockInAsset.getQuantity() >= targetStock.getQuantity();
    }


}
