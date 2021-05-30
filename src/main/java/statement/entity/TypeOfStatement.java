package statement.entity;

import statement.entity.Statement;

import javax.persistence.*;
import java.util.List;

@Entity
@Table(name = "type_of_statement")
public class TypeOfStatement {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private long id;

    @Column(name = "key")
    private String key;

    @Column(name = "name")
    private String name;

    @OneToMany(mappedBy = "typeOfStatement", fetch = FetchType.LAZY)
    private List<Statement> statements;

    public TypeOfStatement() {
    }

    public TypeOfStatement(String key, String name, List<Statement> statements) {
        this.key = key;
        this.name = name;
        this.statements = statements;
    }

    public long getId() {
        return id;
    }

    public void setId(long id) {
        this.id = id;
    }

    public String getKey() {
        return key;
    }

    public void setKey(String key) {
        this.key = key;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public List<Statement> getStatements() {
        return statements;
    }

    public void setStatements(List<Statement> statements) {
        this.statements = statements;
    }
}
