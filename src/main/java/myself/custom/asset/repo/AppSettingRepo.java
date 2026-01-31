package myself.custom.asset.repo;

import myself.custom.asset.model.AppSetting;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface AppSettingRepo extends JpaRepository<AppSetting, String> {
}
