package statement.config;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.scheduling.annotation.EnableScheduling;
import org.springframework.security.core.userdetails.UserDetailsService;
import statement.service.authentication.EmployeeDetailsService;

@EnableScheduling
@ComponentScan(basePackages = "/statement")
public class ApplicationConfig {
    @Bean
    public UserDetailsService employeeDetailService(){
        return new EmployeeDetailsService();
    }
}
