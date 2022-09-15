package myself.custom.asset.repo;

import myself.custom.asset.model.StockRecord;
import myself.custom.asset.model.pk.CustomPk;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface StockRecordRepo extends JpaRepository<StockRecord, CustomPk> {
}
