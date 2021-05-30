package statement.message.mappers;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import statement.entity.Statement;
import statement.message.dto.StatementDTO;
import statement.repositories.DepartmentRepositories;
import statement.repositories.TypeOfStatementRepositories;

import javax.persistence.EntityNotFoundException;
import java.util.List;
import java.util.stream.Collectors;

@Component
public class StatementMapper {

    @Autowired
    private TypeOfStatementRepositories typeOfStatementRepositories;
    @Autowired
    private DepartmentRepositories departmentRepositories;


    public Statement toEntity(StatementDTO statementDTO) {
        Statement statement = new Statement();

            statement.setTypeOfStatement(typeOfStatementRepositories.findByKey(statementDTO.getTypeOfStatement())
                    .orElseThrow(() -> new EntityNotFoundException("Not found entity Statement.key = " + statementDTO.getTypeOfStatement())));
            statement.setDepartment(departmentRepositories.findByKey(statementDTO.getDepartment())
                    .orElseThrow(() -> new EntityNotFoundException("Not found entity Department.key = " + statementDTO.getDepartment())));

            statement.setSurname(statementDTO.getSurname());
            statement.setName(statementDTO.getName());
            statement.setMiddleName(statementDTO.getMiddleName());
            statement.setPassportNumber(statementDTO.getPassportNumber());
            statement.setNumber(statementDTO.getNumber());
            return statement;
    }

    public StatementDTO toDTO(Statement statement) {
        return StatementDTO.build(statement);
    }

    public List<StatementDTO> toDTOs(List<Statement> statements) {
       return statements.stream()
                .map(this::toDTO)
                .collect(Collectors.toList());
    }
}
