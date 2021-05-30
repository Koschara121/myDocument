package statement.service.authentication;

import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;
import statement.entity.Employee;

import java.util.Collection;
import java.util.List;

public class EmployeeDetails implements UserDetails {
    private  String username;
    private String password;
    private Collection<? extends GrantedAuthority> authorities;

    public static EmployeeDetails build(Employee employee){
        return new EmployeeDetails(
                employee.getLogin(),
                employee.getPassword(),
                List.of(new SimpleGrantedAuthority("user")));
    }

    public EmployeeDetails(String username, String password, Collection<? extends GrantedAuthority> authorities) {
        this.username = username;
        this.password = password;
        this.authorities = authorities;
    }

    public EmployeeDetails() {
    }

    @Override
    public Collection<? extends GrantedAuthority> getAuthorities() {
        return this.authorities;
    }

    @Override
    public String getPassword() {
        return this.password;
    }

    @Override
    public String getUsername() {
        return this.username;
    }

    @Override
    public boolean isAccountNonExpired() {
        return true;
    }

    @Override
    public boolean isAccountNonLocked() {
        return true;
    }

    @Override
    public boolean isCredentialsNonExpired() {
        return true;
    }

    @Override
    public boolean isEnabled() {
        return true;
    }
}
