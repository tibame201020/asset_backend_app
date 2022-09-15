package myself.custom.asset;

import com.google.gson.Gson;
import myself.custom.asset.enums.RecordAction;
import myself.custom.asset.model.domain.Stock;
import myself.custom.asset.repo.AssetRepo;
import myself.custom.asset.repo.StockRecordRepo;
import myself.custom.asset.service.AssetService;
import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.Bean;
import java.util.Map;

@SpringBootApplication
public class AssetApplication {

    public static void main(String[] args) {
        SpringApplication.run(AssetApplication.class, args);
    }

    @Bean
    CommandLineRunner run(AssetRepo assetRepo, StockRecordRepo stockRecordRepo, AssetService assetService) {
        return args -> {
            Gson gson = new Gson();
//            CustomPk customPk = new CustomPk();
//            customPk.setLineId("line8");
//
//            Stock stock = new Stock();
//            stock.setCode("2330");
//            stock.setPrice(180);
//            stock.setQuantity(20);
//
//            Stock stock2 = new Stock();
//            stock2.setCode("6330");
//            stock2.setPrice(250);
//            stock2.setQuantity(10);
//
//            Asset asset = new Asset();
//            asset.setCustomPk(customPk);
//            asset.setCash(50000);
//            asset.setStocks(new Stock[]{stock, stock2});
//            assetRepo.save(asset);
//
//
//            StockRecord stockRecord = new StockRecord();
//            stockRecord.setCustomPk(customPk);
//            stockRecord.setAction(RecordAction.BUY);
//            stockRecord.setStock(stock);
//            stockRecordRepo.save(stockRecord);
//
//
//            List<Asset> assets = assetRepo.findByCustomPkLineIdOrderByCustomPkLogTimeDesc("line8");
//            List<StockRecord> stockRecords = stockRecordRepo.findAll();
//
//            System.out.println(gson.toJson(assets));
            System.out.println("before");
            System.out.println(gson.toJson(assetService.getLatestAsset("line8")));

            Stock stock = new Stock();
            stock.setCode("1314");
            stock.setPrice(200);
            stock.setQuantity(8);
            Map<String, Object> result = assetService.operatingStock("line8", stock, RecordAction.BUY);

            System.out.println("after");
            System.out.println(gson.toJson(assetService.getLatestAsset("line8")));
            System.out.println(gson.toJson(stockRecordRepo.findAll()));

            System.out.println(gson.toJson(result));

        };
    }


}
