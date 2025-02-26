package com.example.springbootdemo.security;

import io.jsonwebtoken.Claims;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.SignatureAlgorithm;
import io.jsonwebtoken.security.Keys;
import org.springframework.stereotype.Component;

import javax.crypto.SecretKey;
import java.util.Date;
import java.util.HashMap;
import java.util.Map;
import java.util.function.Function;

@Component
public class JwtUtil {

    // Генерация безопасного ключа для подписи JWT
    private final SecretKey SECRET_KEY = Keys.secretKeyFor(SignatureAlgorithm.HS256);

    // Время жизни токена (например, 10 часов)
    public static final long TOKEN_VALIDITY = 1000 * 60 * 60 * 10;

    /**
     * Извлечение имени пользователя из токена.
     */
    public String extractUsername(String token)
    {
        return extractClaim(token, Claims::getSubject);
    }

    /**
     * Извлечение даты истечения срока действия токена.
     */
    public Date extractExpiration(String token)
    {
        return extractClaim(token, Claims::getExpiration);
    }

    /**
     * Извлечение конкретного утверждения (claim) из токена.
     */
    public <T> T extractClaim(String token, Function<Claims, T> claimsResolver)
    {
        final Claims claims = extractAllClaims(token);
        return claimsResolver.apply(claims);
    }

    /**
     * Извлечение всех утверждений (claims) из токена.
     */
    private Claims extractAllClaims(String token)
    {
        return Jwts.parser()
                .setSigningKey(SECRET_KEY)
                .build()
                .parseClaimsJws(token)
                .getBody();
    }

    /**
     * Проверка, истек ли срок действия токена.
     */
    private Boolean isTokenExpired(String token)
    {
        return extractExpiration(token).before(new Date());
    }

    /**
     * Генерация JWT-токена на основе данных пользователя.
     */
    public String generateToken(String username)
    {
        Map<String, Object> claims = new HashMap<>();
        return createToken(claims, username);
    }

    /**
     * Создание JWT-токена.
     */
    private String createToken(Map<String, Object> claims, String subject)
    {
        return Jwts.builder()
                .setClaims(claims)
                .setSubject(subject)
                .setIssuedAt(new Date(System.currentTimeMillis()))
                .setExpiration(new Date(System.currentTimeMillis() + TOKEN_VALIDITY))
                .signWith(SECRET_KEY)
                .compact();
    }

    /**
     * Валидация токена без использования UserDetails.
     */
    public Boolean validateToken(String token)
    {
        try
        {
            Jwts.parser()
                    .setSigningKey(SECRET_KEY)
                    .build()
                    .parseClaimsJws(token);
            return !isTokenExpired(token);
        }
        catch (Exception e)
        {
            return false;
        }
    }
}