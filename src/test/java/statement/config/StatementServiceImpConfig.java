package statement.config;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import statement.repositories.StatementRepositories;
import statement.repositories.StatementStatusRepositories;
import statement.service.StatementServiceImp;

import static org.mockito.Mockito.mock;

@Configuration
public class StatementServiceImpConfig {

    @Bean
    public StatementServiceImp statementServiceImp() {
        return new StatementServiceImp();
    }

    @Bean
    public StatementRepositories statementRepositories() {
        return mock(StatementRepositories.class);
    }

    @Bean
    public StatementStatusRepositories statementStatusRepositories() {
        return mock(StatementStatusRepositories.class);
    }
}
