package myself.custom.asset.repo;

import myself.custom.asset.model.Asset;
import myself.custom.asset.model.pk.CustomPk;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface AssetRepo extends JpaRepository<Asset, CustomPk> {
    List<Asset> findByCustomPkLineIdOrderByCustomPkLogTimeDesc(String lineId);
}
