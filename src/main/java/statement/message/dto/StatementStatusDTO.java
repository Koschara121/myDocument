package statement.message.dto;

public class StatementStatusDTO {

    private String status;
    private String key;

    public StatementStatusDTO(String status, String key) {
        this.status = status;
        this.key = key;
    }

    public StatementStatusDTO() {
    }

    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
    }

    public String getKey() {
        return key;
    }

    public void setKey(String key) {
        this.key = key;
    }

    @Override
    public String toString() {
        return "StatementStatusDTO{" +
                "status='" + status + '\'' +
                ", key='" + key + '\'' +
                '}';
    }
}
