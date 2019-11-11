package com.transporter.security;

import com.transporter.entities.user.User;
import io.jsonwebtoken.*;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.security.core.Authentication;
import org.springframework.stereotype.Component;

import java.time.LocalDateTime;
import java.util.Date;

/**
 * Class used for generating JWT Token after a user successfully logs in, and to validate
 * JWT Token sent in the authorization header of the requests.
 */
@Slf4j
@Component
public class JwtTokenProvider {

    private String jtwSecret = "verySecretKey";

    @Value("${app.jwtExpirationTime}")
    private int jwtExpirationInMs;

    public String generateToken(Authentication authentication) {
        User user = (User) authentication.getPrincipal();
        Date expireTime = new Date(new Date().getTime() + jwtExpirationInMs);

        return Jwts.builder()
                .setSubject(Long.toString(user.getId()))
                .setIssuedAt(new Date())
                .setExpiration(expireTime)
                .signWith(SignatureAlgorithm.HS512, jtwSecret)
                .compact();
    }

    public Long getUserIdFromJwt(String token){
        Claims claims = Jwts.parser()
                .setSigningKey(jtwSecret)
                .parseClaimsJws(token)
                .getBody();

        return Long.parseLong(claims.getSubject());
    }

    public boolean tokenIsValid(String token){
        try{
            Jwts.parser().setSigningKey(jtwSecret).parseClaimsJws(token);
            return true;
        } catch (SignatureException ex){
            log.error("Invalid JWT Signature");
        } catch (MalformedJwtException ex){
            log.error("Invalid JWT Token");
        } catch (ExpiredJwtException ex){
            log.error("Expired JWT Token");
        } catch (UnsupportedJwtException ex){
            log.error("Unsupported JWT Token");
        } catch (IllegalArgumentException ex){
            log.error("JTW Claims String is empty");
        }
        return false;
    }


}
