package rikkei.academy.security.jwt;

import io.jsonwebtoken.*;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;
import rikkei.academy.security.userPrincipal.CustomUserDetails;

import java.util.Date;

@Component
@Slf4j
public class JwtTokenProvider {
    @Value("${ra.jwt.secret}")
    private String JWT_SECRET;
    @Value("${ra.jwt.expiration}")
    private int JWT_EXPIRATION;

    // tạo token từ username, secret, expiration
    public String createToken(CustomUserDetails userDetails) {
        Date now = new Date();
        Date dateExpired = new Date(now.getTime() + JWT_EXPIRATION);
        return Jwts.builder().setSubject(userDetails.getUsername())
                .setIssuedAt(now)
                .setExpiration(dateExpired)
                .signWith(SignatureAlgorithm.HS512, JWT_SECRET)
                .compact();
    }
    // lấy ra username từ token được mã hóa
    public String getUsernameFromJWTtoken(String token) {
        return Jwts.parser().setSigningKey(JWT_SECRET).parseClaimsJws(token).getBody().getSubject();
    }
    // xác thực token
    public boolean validateToken(String token){
        try {
            Jwts.parser().setSigningKey(JWT_SECRET)
                    .parseClaimsJws(token);
            return true;
        }catch (MalformedJwtException ex){
            log.error("Invalid JWT Token");
        }catch (ExpiredJwtException ex){
            log.error("Expired JWT Token");
        }catch (UnsupportedJwtException ex){
            log.error("Unsupported JWT Token");
        }catch (IllegalArgumentException ex){
            log.error("JWT claims String is empty");
        }
        return false;
    }
}
