package ra.demo.security.jwt;

import io.jsonwebtoken.ExpiredJwtException;
import io.jsonwebtoken.JwtException;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.UnsupportedJwtException;
import io.jsonwebtoken.security.Keys;
import io.jsonwebtoken.security.SignatureException;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.stereotype.Component;
import ra.demo.security.principal.CustomUserDetails;

import javax.crypto.SecretKey;
import java.nio.charset.StandardCharsets;
import java.time.Instant;
import java.util.Date;

@Component
@RequiredArgsConstructor
@Slf4j
public class JWTProvider {
    @Value("${jwt-secret}")
    private String jwtSecret;
    @Value("${jwt-expired}")
    private Long jwtExpired;

    public String generateToken(String username){
        try{
            SecretKey key = Keys.hmacShaKeyFor(jwtSecret.getBytes(StandardCharsets.UTF_8));
            Date today = new Date();

            return Jwts.builder()
                    .subject(username)
                    .signWith(key)
                    .issuedAt(today)
                    .expiration(new Date(today.getTime()+jwtExpired))
                    .compact();
        }catch (Exception ex){
            throw new RuntimeException("Không tạo được chuỗi jwt ",ex);
        }
    }

    public boolean validateToken(String token){
        try{
            SecretKey key = Keys.hmacShaKeyFor(jwtSecret.getBytes(StandardCharsets.UTF_8));
            Jwts.parser().verifyWith(key).build().parseSignedClaims(token);
            return true;
        } catch (ExpiredJwtException e) {
            log.info("Token hết hạn ",e.getMessage());
            throw new RuntimeException("Token hết hạn ",e);
        } catch (UnsupportedJwtException e) {
            log.info("Hệ thống không xử lý jwt ",e.getMessage());
            throw new RuntimeException("Hệ thống không xử lý jwt ",e);
        }catch (SignatureException  e){
            log.info("Sai chữ ký token ",e.getMessage());
            throw new RuntimeException("Sai chữ ký token ",e);
        }catch (IllegalArgumentException e){
            log.info("Token rỗng",e.getMessage());
            throw new RuntimeException("Token rỗng",e);
        }catch (JwtException e){
            log.info("Lỗi xử lý token",e.getMessage());
            throw new RuntimeException("Lỗi xử lý token",e);
        }
    }

    public String getUsernameFromToken(String token){
        try{
            SecretKey key = Keys.hmacShaKeyFor(jwtSecret.getBytes(StandardCharsets.UTF_8));
            return Jwts.parser().verifyWith(key).build().parseSignedClaims(token).getPayload().getSubject();
        } catch (RuntimeException e) {
            throw new RuntimeException(e);
        }
    }

}
