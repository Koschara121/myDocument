package statement.service.authentication;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;
import statement.entity.Employee;
import statement.repositories.EmployeeRepositories;

@Service
public class EmployeeDetailsService implements UserDetailsService {

    @Autowired
    private EmployeeRepositories employeeRepositories;

    @Override
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {

        Employee employee = employeeRepositories.findByLogin(username)
                .orElseThrow(() -> new UsernameNotFoundException("User not found"));
        return EmployeeDetails.build(employee);
    }
}
