package statement.message.mappers;

import org.springframework.stereotype.Component;
import statement.entity.Statement;
import statement.message.dto.StatementStatusDTO;

@Component
public class StatementStatusMapper {

    public StatementStatusDTO toDTO(Statement statement) {
        return new StatementStatusDTO(statement.getStatementStatus().getName(), statement.getStatementStatus().getKey());
    }
}
