package statement.entity;

import javax.persistence.*;
import java.util.List;

@Entity
@Table(name = "statement_status")
public class StatementStatus {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private long id;

    @Column(name = "key")
    private String key;

    @Column(name = "name")
    private String name;

    @OneToMany(mappedBy = "statementStatus", fetch = FetchType.LAZY)
    private List<Statement> statements;

    public StatementStatus() {
    }

    public StatementStatus(String key, String name, List<Statement> statements) {
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

    public List<Statement> getEmployees() {
        return statements;
    }

    public void setEmployees(List<Statement> employees) {
        this.statements = employees;
    }

    @Override
    public String toString() {
        return "StatementStatus{" +
                "id=" + id +
                ", key='" + key + '\'' +
                ", name='" + name + '\'' +
                ", statements=" + statements +
                '}';
    }
}
