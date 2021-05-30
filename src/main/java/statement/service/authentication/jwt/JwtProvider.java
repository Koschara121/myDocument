package statement.service.authentication.jwt;

import io.jsonwebtoken.*;
import org.springframework.security.core.Authentication;
import org.springframework.stereotype.Component;
import statement.service.authentication.EmployeeDetails;

import java.time.LocalDateTime;
import java.time.ZoneId;
import java.util.Date;

@Component
public class JwtProvider {

    private String jwtSecret = "qwerty";

    public String generateJwtToken(Authentication authentication) {

        EmployeeDetails userPrincipal = (EmployeeDetails) authentication.getPrincipal();

        Date exp = Date.from(LocalDateTime.now().plusMinutes(30).atZone(ZoneId.systemDefault()).toInstant());

        return Jwts.builder()
                .setSubject((userPrincipal.getUsername()))
                .setIssuedAt(new Date())
                .setExpiration(exp)
                .signWith(SignatureAlgorithm.HS512, jwtSecret)
                .compact();
    }

    public boolean validateJwtToken(String authToken) {
        try {
            Jwts.parser().setSigningKey(jwtSecret).parseClaimsJws(authToken);
            return true;
        } catch (SignatureException e) {
           // logger.error("Invalid JWT signature -> Message: {} ", e);
        } catch (MalformedJwtException e) {
            //logger.error("Invalid JWT token -> Message: {}", e);
        } catch (ExpiredJwtException e) {
            //logger.error("Expired JWT token -> Message: {}", e);
        } catch (UnsupportedJwtException e) {
            //logger.error("Unsupported JWT token -> Message: {}", e);
        } catch (IllegalArgumentException e) {
            //logger.error("JWT claims string is empty -> Message: {}", e);
        }
        return false;
    }

    public String getUserNameFromJwtToken(String token) {
        return Jwts.parser()
                .setSigningKey(jwtSecret)
                .parseClaimsJws(token)
                .getBody().getSubject();
    }
}
