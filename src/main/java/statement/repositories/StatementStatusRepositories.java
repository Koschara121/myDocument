package statement.repositories;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import statement.entity.StatementStatus;

import java.util.Optional;

@Repository
public interface StatementStatusRepositories extends JpaRepository<StatementStatus, Long> {
    Optional<StatementStatus> findByKey(String key);
}
