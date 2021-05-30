package statement.repositories;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import statement.entity.Statement;
import statement.entity.StatementStatus;

import java.util.List;

@Repository
public interface StatementRepositories extends JpaRepository<Statement, Long> {
    Statement findByNumber(int number);
    List<Statement> findAllByPassportNumber(String passportNumber);
    List<Statement> findByStatementStatus(StatementStatus statementStatus);
}
