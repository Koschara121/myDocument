package statement.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.PropertySource;
import org.springframework.core.env.Environment;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpMethod;
import org.springframework.http.ResponseEntity;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestClientException;
import org.springframework.web.client.RestTemplate;
import org.springframework.web.util.UriComponentsBuilder;
import statement.entity.Statement;
import statement.entity.StatementStatus;
import statement.message.dto.StatementDTO;
import statement.message.dto.StatementStatusDTO;
import statement.message.mappers.StatementMapper;
import statement.repositories.StatementRepositories;
import statement.repositories.StatementStatusRepositories;

import javax.persistence.EntityNotFoundException;
import javax.transaction.Transactional;
import java.util.HashSet;
import java.util.Set;

@Service
@PropertySource(value = "classpath:resttemplate.properties")
public class ProcessingStatementScheduledImp implements ProcessingStatementScheduled {

    @Autowired
    private StatementRepositories statementRepositories;
    @Autowired
    private StatementStatusRepositories statementStatusRepositories;
    @Autowired
    private StatementMapper statementMapper;
    @Autowired
    private Environment environment;
    @Autowired
    private RestTemplate restTemplate;

    @Override
    @Scheduled(cron = "0 * * ? * *")
    public void processingNewStatement() {

        StatementStatus status = statementStatusRepositories.findByKey("NEW")
                .orElseThrow(() -> new EntityNotFoundException("Not found entity Statement.key = NEW"));

        Set<Statement> newStatements = new HashSet<>(statementRepositories.findByStatementStatus(status));

        if(!newStatements.isEmpty()) newStatements.forEach(this::requestNewStatement);
    }

    private void requestNewStatement(Statement statement){

        HttpHeaders header = getHeaderForBasicAuth();

        HttpEntity<StatementDTO> request = new HttpEntity<StatementDTO>(statementMapper.toDTO(statement), header);

       try {
           ResponseEntity<?> response = restTemplate.postForEntity(environment.getRequiredProperty("rest.template.new.statement.url"), request, ResponseEntity.class);

           if(response.getStatusCodeValue() == 200) {
               updateStatusStatement(statement, statementStatusRepositories.findByKey("PROCESSING")
                       .orElseThrow(() -> new EntityNotFoundException("Not found entity Statement.key = PROCESSING" )));
           }

       } catch (RestClientException re) {
           System.err.println(re.getMessage());
       }
    }

    @Override
    @Scheduled(cron = "0 * * ? * *")
    public void processingOldStatement() {
        StatementStatus status = statementStatusRepositories.findByKey("PROCESSING")
                .orElseThrow(() -> new EntityNotFoundException("Not found entity Statement.key = PROCESSING" ));

        Set<Statement> oldStatements = new HashSet<>(statementRepositories.findByStatementStatus(status));

        if(!oldStatements.isEmpty()) oldStatements.forEach(this::requestStatusStatement);
    }

    private void requestStatusStatement(Statement statement) {

        UriComponentsBuilder builder = UriComponentsBuilder.fromHttpUrl(environment.getRequiredProperty("rest.template.old.statement.url"))
                .queryParam("keyDepartment", statement.getDepartment().getKey())
                .queryParam("typeStatement", statement.getTypeOfStatement().getKey())
                .queryParam("numberStatement", statement.getNumber());

        HttpHeaders header = getHeaderForBasicAuth();

        HttpEntity<String> request = new HttpEntity<String>(header);

        try {
            ResponseEntity<StatementStatusDTO> response = restTemplate.exchange(builder.toUriString(), HttpMethod.GET, request, StatementStatusDTO.class);

            if(response.getStatusCodeValue() == 200) {
                StatementStatusDTO statementStatusDTO = response.getBody();
                StatementStatus status = statementStatusRepositories.findByKey("COMPLETED")
                        .orElseThrow(() -> new EntityNotFoundException("Not found entity Statement.key = COMPLETED"));

                if(statementStatusDTO.getKey().equals(status.getKey())) {
                    updateStatusStatement(statement, status);
                }
            }
        }catch (RestClientException re) {
            System.err.println(re.getMessage());
        }
    }

    private HttpHeaders getHeaderForBasicAuth() {
        HttpHeaders header = new HttpHeaders();
        header.setBasicAuth(environment.getRequiredProperty("header.username"),
                            environment.getRequiredProperty("header.password"));
        return header;
    }

    @Transactional
    private void updateStatusStatement(Statement statement, StatementStatus status) {
        statement.setStatementStatus(status);
        statementRepositories.save(statement);
    }
}
