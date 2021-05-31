package statement.controllers;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import statement.entity.Statement;
import statement.exeptions.StatementAlreadyExistException;
import statement.message.dto.StatementDTO;
import statement.message.dto.StatementStatusDTO;
import statement.message.mappers.StatementMapper;
import statement.message.mappers.StatementStatusMapper;
import statement.service.StatementService;

import javax.persistence.EntityNotFoundException;
import java.sql.SQLException;
import java.util.List;

@RestController
@RequestMapping("/statement")
public class StatementRestController {

    @Autowired
    private StatementService statementService;
    @Autowired
    private StatementMapper statementMapper;
    @Autowired
    private StatementStatusMapper statementStatusMapper;

    @PostMapping(value = "/registration")
    public ResponseEntity<?> registeringStatement(@RequestBody StatementDTO statementDTO){
        try {
            statementService.registeringNewStatement(statementMapper.toEntity(statementDTO));
            return  ResponseEntity.ok().body("Заявление сохранино!");
        } catch (StatementAlreadyExistException | SQLException | EntityNotFoundException e) {
            return ResponseEntity.badRequest().body(e.getMessage());
        }
    }

    @GetMapping("/status/{number}")
    public ResponseEntity<StatementStatusDTO> getStatusStatementByNumber(@PathVariable(name = "number") int number) {
        Statement statement = statementService.getStatusStatement(number);

        return statement != null
                ? new ResponseEntity<>(statementStatusMapper.toDTO(statement), HttpStatus.valueOf(202))
                : new ResponseEntity<>(HttpStatus.valueOf(404));
    }

    @GetMapping("/{passportNumber}")
    public ResponseEntity<List<StatementDTO>> getAllStatementByPassportNumber(@PathVariable(name = "passportNumber") String passportNumber) {
        List<StatementDTO> statementDTOList = statementMapper.toDTOs(statementService.getAllStatement(passportNumber));

        return !statementDTOList.isEmpty()
                ? new ResponseEntity<>(statementDTOList, HttpStatus.valueOf(200))
                : new ResponseEntity<>(statementDTOList, HttpStatus.valueOf(404));
    }
}
