package statement.config;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import statement.message.mappers.StatementMapper;
import statement.repositories.DepartmentRepositories;
import statement.repositories.StatementRepositories;
import statement.repositories.StatementStatusRepositories;
import statement.repositories.TypeOfStatementRepositories;
import statement.service.ProcessingStatementScheduledImp;

import static org.mockito.Mockito.mock;

@Configuration
public class ProcessingStatementScheduledImpTestConfig {

    @Bean
    public ProcessingStatementScheduledImp processingStatementScheduledImp() {
        return new ProcessingStatementScheduledImp();
    }

    @Bean
    public StatementRepositories statementRepositories() {
        return mock(StatementRepositories.class);
    }

    @Bean
    public StatementStatusRepositories statementStatusRepositories() {
        return mock(StatementStatusRepositories.class);
    }

    @Bean
    public StatementMapper statementMapper() {
        return mock(StatementMapper.class);
    }

    @Bean
    TypeOfStatementRepositories typeOfStatementRepositories() {
        return mock(TypeOfStatementRepositories.class);
    }
    @Bean
    DepartmentRepositories departmentRepositories() {
        return mock(DepartmentRepositories.class);
    }
}
