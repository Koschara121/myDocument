package statement.message.dto;

import statement.entity.Statement;


public class StatementDTO {
    private int number;
    private String typeOfStatement;
    private String department;
    private String passportNumber;
    private String surname;
    private String name;
    private String middleName;

    public StatementDTO(int number, String typeOfStatement, String department,  String passportNumber, String surname, String name, String middleName) {
        this.number = number;
        this.typeOfStatement = typeOfStatement;
        this.department = department;
        this.passportNumber = passportNumber;
        this.surname = surname;
        this.name = name;
        this.middleName = middleName;
    }

    public static StatementDTO build(Statement statement) {
        return new StatementDTO(
                statement.getNumber(),
                statement.getTypeOfStatement().getKey(),
                statement.getDepartment().getKey(),
                statement.getPassportNumber(),
                statement.getSurname(),
                statement.getName(),
                statement.getMiddleName());
    }

    public StatementDTO() {
    }

    public int getNumber() {
        return number;
    }

    public void setNumber(int number) {
        this.number = number;
    }

    public String getTypeOfStatement() {
        return typeOfStatement;
    }

    public void setTypeOfStatement(String typeOfStatement) {
        this.typeOfStatement = typeOfStatement;
    }

    public String getDepartment() {
        return department;
    }

    public void setDepartment(String department) {
        this.department = department;
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
}
