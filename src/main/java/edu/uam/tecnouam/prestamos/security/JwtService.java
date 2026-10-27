package edu.uam.tecnouam.prestamos.security;


import edu.uam.tecnouam.prestamos.model.Rol;
import io.jsonwebtoken.Claims;
import io.jsonwebtoken.JwtException;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.security.Keys;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;


import javax.crypto.SecretKey;
import java.nio.charset.StandardCharsets;
import java.time.Instant;
import java.util.Date;
import java.util.Optional;


@Service
public class JwtService {


    private static final int MIN_SECRET_BYTES = 32;


    private final SecretKey key;
    private final long expirationMinutes;


    public JwtService(@Value("${app.jwt.secret:}") String secret,
                      @Value("${app.jwt.expiration-minutes:60}") long expirationMinutes) {
        if (secret == null || secret.getBytes(StandardCharsets.UTF_8).length < MIN_SECRET_BYTES) {
            throw new IllegalStateException(
                    "Falta la variable de entorno JWT_SECRET o es demasiado corta (minimo 32 caracteres). "
                            + "Genere una con: openssl rand -base64 48");
        }
        this.key = Keys.hmacShaKeyFor(secret.getBytes(StandardCharsets.UTF_8));
        this.expirationMinutes = expirationMinutes;
    }


    public String generarToken(String username, Rol rol) {
        Instant ahora = Instant.now();
        return Jwts.builder()
                .subject(username)
                .claim("rol", rol.name())
                .issuedAt(Date.from(ahora))
                .expiration(Date.from(ahora.plusSeconds(expirationMinutes * 60)))
                .signWith(key)
                .compact();
    }


    /** Devuelve los datos del token si es valido (firma correcta y no expirado); vacio en caso contrario. */
    public Optional<TokenInfo> validar(String token) {
        try {
            Claims claims = Jwts.parser().verifyWith(key).build().parseSignedClaims(token).getPayload();
            String username = claims.getSubject();
            String rol = claims.get("rol", String.class);
            if (username == null || rol == null) {
                return Optional.empty();
            }
            return Optional.of(new TokenInfo(username, Rol.valueOf(rol)));
        } catch (JwtException | IllegalArgumentException e) {
            return Optional.empty();
        }
    }


    public long getExpirationMinutes() {
        return expirationMinutes;
    }


    public record TokenInfo(String username, Rol rol) {
    }
}
