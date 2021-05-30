package statement.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Service;
import statement.entity.Statement;
import statement.exeptions.StatementAlreadyExistException;
import statement.repositories.StatementRepositories;
import statement.repositories.StatementStatusRepositories;

import javax.persistence.EntityNotFoundException;
import javax.transaction.Transactional;
import java.util.List;

@Service
public class StatementServiceImp implements StatementService{

    @Autowired
    private StatementRepositories statementRepositories;
    @Autowired
    private StatementStatusRepositories statementStatusRepositories;

    @Override
    @Transactional
    public void registeringNewStatement(Statement statement) throws StatementAlreadyExistException {
        if(statementRepositories.findByNumber(statement.getNumber()) != null) {
            throw new StatementAlreadyExistException("Заявление с таким номером уже существует");
        }

        statement.setEmployeeLogin(SecurityContextHolder.getContext().getAuthentication().getName());
        statement.setStatementStatus(statementStatusRepositories.findByKey("NEW")
                .orElseThrow(() -> new EntityNotFoundException("Not found entity Statement.key = NEW")));
        statementRepositories.save(statement);
    }

    @Override
    public List<Statement> getAllStatement(String passportNumber) {
        return statementRepositories.findAllByPassportNumber(passportNumber);
    }

    @Override
    public Statement getStatusStatement(int number) {
        return statementRepositories.findByNumber(number);
    }
}