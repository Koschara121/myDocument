package statement.entity;


import javax.persistence.*;

@Entity
@Table(name = "statement")
public class Statement {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private long id;

    @Column(name = "number")
    private int number;

    @ManyToOne(cascade = CascadeType.REMOVE , optional = false)
    @JoinColumn(name = "typeOfStatement_id")
    private TypeOfStatement typeOfStatement;

    @ManyToOne(cascade = CascadeType.REMOVE, optional = false)
    @JoinColumn(name = "department_id")
    private Department department;

    @ManyToOne(cascade = CascadeType.REMOVE , optional = false)
    @JoinColumn(name = "statementStatus_id")
    private StatementStatus statementStatus;

    @Column(name = "passport_number")
    private String passportNumber;

    @Column(name = "sure_name")
    private String surname;

    @Column(name = "name")
    private String name;

    @Column(name = "middle_name")
    private String middleName;

    @Column(name = "employee_login")
    private String employeeLogin;

    public Statement() {
    }

    public Statement(int number, TypeOfStatement typeOfStatement, Department department, StatementStatus statementStatus, String passportNumber, String surname, String name, String middleName, String employeeLogin) {
        this.number = number;
        this.typeOfStatement = typeOfStatement;
        this.department = department;
        this.statementStatus = statementStatus;
        this.passportNumber = passportNumber;
        this.surname = surname;
        this.name = name;
        this.middleName = middleName;
        this.employeeLogin = employeeLogin;
    }

    public long getId() {
        return id;
    }

    public void setId(long id) {
        this.id = id;
    }

    public int getNumber() {
        return number;
    }

    public void setNumber(int number) {
        this.number = number;
    }

    public TypeOfStatement getTypeOfStatement() {
        return typeOfStatement;
    }

    public void setTypeOfStatement(TypeOfStatement typeOfStatement) {
        this.typeOfStatement = typeOfStatement;
    }

    public Department getDepartment() {
        return department;
    }

    public void setDepartment(Department department) {
        this.department = department;
    }

    public StatementStatus getStatementStatus() {
        return statementStatus;
    }

    public void setStatementStatus(StatementStatus statementStatus) {
        this.statementStatus = statementStatus;
    }

    public String getPassportNumber() {
        return passportNumber;
    }

    public void setPassportNumber(String passportNumber) {
        this.passportNumber = passportNumber;
    }

    public String getSurname() {
        return surname;
    }

    public void setSurname(String surname) {
        this.surname = surname;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getMiddleName() {
        return middleName;
    }

    public void setMiddleName(String middleName) {
        this.middleName = middleName;
    }

    public String getEmployeeLogin() {
        return employeeLogin;
    }

    public void setEmployeeLogin(String employeeLogin) {
        this.employeeLogin = employeeLogin;
    }


}
