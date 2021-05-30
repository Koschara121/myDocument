package statement.controllers;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;
import statement.message.dto.JWTResponseDTO;
import statement.message.dto.UserDTO;
import statement.repositories.EmployeeRepositories;
import statement.service.authentication.jwt.JwtProvider;

@RestController
public class AuthRestController {
    @Autowired
    JwtProvider jwtProvider;
    @Autowired
    AuthenticationManager authenticationManager;
    @Autowired
    EmployeeRepositories employeeRepositories;

    @PostMapping(value = "/login")
    public ResponseEntity<?> authenticateUser(@RequestBody UserDTO user) {
        Authentication authentication = authenticationManager.authenticate(
                new UsernamePasswordAuthenticationToken(
                        user.getLogin(),
                        user.getPassword()
                )
        );

        SecurityContextHolder.getContext().setAuthentication(authentication);

        String jwt = jwtProvider.generateJwtToken(authentication);
        String username = jwtProvider.getUserNameFromJwtToken(jwt);

        return ResponseEntity.ok(new JWTResponseDTO(jwt, username));
    }
}
