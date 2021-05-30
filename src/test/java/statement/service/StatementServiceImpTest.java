package statement.service;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContext;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit.jupiter.SpringExtension;
import org.springframework.test.context.support.AnnotationConfigContextLoader;
import statement.config.StatementServiceImpConfig;
import statement.entity.Statement;
import statement.entity.StatementStatus;
import statement.exeptions.StatementAlreadyExistException;
import statement.repositories.StatementRepositories;
import statement.repositories.StatementStatusRepositories;

import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.mockito.Mockito.*;

@ExtendWith(SpringExtension.class)
@ContextConfiguration(classes = StatementServiceImpConfig.class, loader = AnnotationConfigContextLoader.class)
class StatementServiceImpTest {

    @Autowired
    private StatementServiceImp statementServiceImp;

    @Autowired
    private StatementRepositories statementRepositories;
    @Autowired
    private StatementStatusRepositories statementStatusRepositories;

    private static final StatementStatus status = mock(StatementStatus.class);
    private static final Statement statement = mock(Statement.class);
    private static final Authentication auth = mock(Authentication.class);
    private static final SecurityContext sec = mock(SecurityContext.class);

    @BeforeEach
    public void init(){
        when(auth.getName()).thenReturn("Admin");
        when(sec.getAuthentication()).thenReturn(auth);
        SecurityContextHolder.setContext(sec);
    }


    //Регистрация новгого заявления которого нет в БД
    @Test
    void registeringNewStatement() throws StatementAlreadyExistException {

        when(statement.getNumber()).thenReturn(1);
        when(statementRepositories.findByNumber(1)).thenReturn(null);
        when(statementStatusRepositories.findByKey(anyString())).thenReturn(java.util.Optional.ofNullable(status));

        statementServiceImp.registeringNewStatement(statement);

        verify(statementRepositories, times(1)).save(any());
    }

    //Регистрация заявления которого уже есть в БД
    @Test
    void registeringDuplicateStatement() {

        when(statement.getNumber()).thenReturn(1);
        when(statementRepositories.findByNumber(1)).thenReturn(statement);
        when(statementStatusRepositories.findByKey(anyString())).thenReturn(java.util.Optional.ofNullable(status));

        assertThrows(StatementAlreadyExistException.class, () -> {
            statementServiceImp.registeringNewStatement(statement);
        } );
    }
}