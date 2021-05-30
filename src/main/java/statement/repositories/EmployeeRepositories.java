package statement.repositories;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import statement.entity.Employee;

import java.util.Optional;

@Repository
public interface EmployeeRepositories extends JpaRepository<Employee, Long> {
     Optional<Employee> findByLogin(String login);
}
