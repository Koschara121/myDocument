package statement.service;

import statement.entity.Statement;
import statement.exeptions.StatementAlreadyExistException;

import java.sql.SQLException;
import java.util.List;

public interface StatementService {
    void registeringNewStatement(Statement statement) throws SQLException, StatementAlreadyExistException;
    List<Statement> getAllStatement(String passportNumber);
    Statement getStatusStatement(int number);
}
