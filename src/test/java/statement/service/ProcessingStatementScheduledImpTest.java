package statement.service;

import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit.jupiter.SpringExtension;
import org.springframework.test.context.support.AnnotationConfigContextLoader;
import statement.config.ProcessingStatementScheduledImpTestConfig;
import statement.entity.StatementStatus;
import statement.message.mappers.StatementMapper;
import statement.repositories.StatementRepositories;
import statement.repositories.StatementStatusRepositories;

import javax.persistence.EntityNotFoundException;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.mockito.ArgumentMatchers.anyString;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.when;

@ExtendWith(SpringExtension.class)
@ContextConfiguration(classes = ProcessingStatementScheduledImpTestConfig.class, loader = AnnotationConfigContextLoader.class)
class ProcessingStatementScheduledImpTest {

    @Autowired
    private ProcessingStatementScheduled processingStatementScheduledImp;

    //mock
    @Autowired
    private StatementRepositories statementRepositories;
    @Autowired
    private StatementStatusRepositories statementStatusRepositories;
    @Autowired
    private StatementMapper statementMapper;

    private static final StatementStatus status = mock(StatementStatus.class);



    @Test
    void processingNewStatement() {

        when(statementStatusRepositories.findByKey(anyString())).thenReturn(Optional.empty());

        assertThrows(EntityNotFoundException.class, () -> {
            processingStatementScheduledImp.processingNewStatement();
        } );
    }
}