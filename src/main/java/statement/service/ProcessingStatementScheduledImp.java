package statement.service;

import org.springframework.beans.factory.annotation.Autowired;
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
public class ProcessingStatementScheduledImp implements ProcessingStatementScheduled {

    @Autowired
    private StatementRepositories statementRepositories;
    @Autowired
    private StatementStatusRepositories statementStatusRepositories;
    @Autowired
    private StatementMapper statementMapper;

    @Override
    @Scheduled(cron = "0 * * ? * *")
    public void processingNewStatement() {

        StatementStatus status = statementStatusRepositories.findByKey("NEW")
                .orElseThrow(() -> new EntityNotFoundException("Not found entity Statement.key = NEW"));

        Set<Statement> newStatements = new HashSet<>(statementRepositories.findByStatementStatus(status));

        if(!newStatements.isEmpty()) newStatements.forEach(this::requestNewStatement);
    }

    private void requestNewStatement(Statement statement){

        RestTemplate restTemplate = new RestTemplate();
        String url = "http://iis:8080/api/IIS/registration";

        HttpHeaders header = new HttpHeaders();
        header.setBasicAuth("admin","admin");

        HttpEntity<StatementDTO> request = new HttpEntity<StatementDTO>(statementMapper.toDTO(statement), header);

       try {
           ResponseEntity<?> response = restTemplate.postForEntity(url, request, ResponseEntity.class);

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
        RestTemplate restTemplate = new RestTemplate();
        String url = "http://iis:8080/api/IIS/status/?";

        UriComponentsBuilder builder = UriComponentsBuilder.fromHttpUrl(url)
                .queryParam("keyDepartment", statement.getDepartment().getKey())
                .queryParam("typeStatement", statement.getTypeOfStatement().getKey())
                .queryParam("numberStatement", statement.getNumber());

        HttpHeaders header = new HttpHeaders();
        header.setBasicAuth("admin","admin");
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

    @Transactional
    private void updateStatusStatement(Statement statement, StatementStatus status) {
        statement.setStatementStatus(status);
        statementRepositories.save(statement);
    }
}
