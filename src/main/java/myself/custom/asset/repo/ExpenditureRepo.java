package myself.custom.asset.repo;

import myself.custom.asset.model.Expenditure;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface ExpenditureRepo extends JpaRepository<Expenditure, Long> {
}
