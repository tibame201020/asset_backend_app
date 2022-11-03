package myself.custom.asset.repo;

import myself.custom.asset.model.CalcConfig;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface CalcConfigRepo extends JpaRepository<CalcConfig, Long> {
}
