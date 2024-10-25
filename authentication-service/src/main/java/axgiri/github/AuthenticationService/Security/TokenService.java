package axgiri.github.AuthenticationService.Security;

import java.nio.charset.StandardCharsets;
import java.security.Key;
import java.util.Date;
import java.util.HashMap;
import java.util.Map;
import java.util.function.Function;
import java.util.stream.Collectors;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.stereotype.Service;

import io.jsonwebtoken.Claims;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.SignatureAlgorithm;
import io.jsonwebtoken.security.Keys;

@Service
public class TokenService {

    private static final Logger logger = LoggerFactory.getLogger(TokenService.class);

    @Value("${jwt.secret}")
    private String KEY;
        
    public String extractUsername(String token) {
        try {
            return extractClaim(token, Claims::getSubject);
        } catch (Exception e) {
            logger.error("failed to extract username from token", e);
            throw new RuntimeException("invalid token");
        }
    }
    
    public Claims extractAllClaims(String token) {
        try {
            logger.info("extracting claims from token: {}", token);
            return Jwts.parserBuilder()
                    .setSigningKey(getSignInKey())
                    .build()
                    .parseClaimsJws(token)
                    .getBody();
        } catch (Exception e) {
            logger.error("token validation failed: {}", e.getMessage());
            throw new RuntimeException("token validation failed");
        }
    }

    private Key getSignInKey() {
        byte[] keyBytes = KEY.getBytes(StandardCharsets.UTF_8);
        return Keys.hmacShaKeyFor(keyBytes);
    }


    public <T> T extractClaim(String token, Function<Claims, T> claimsResolver){
        final Claims claim = extractAllClaims(token);
        return claimsResolver.apply(claim);
    }

    public String generateToken(Map<String, Object> extraClaims, UserDetails userDetails){
        logger.info("generating token for user: {}", userDetails.getUsername());
        extraClaims.put("roles", userDetails.getAuthorities().stream()
        .map(authority -> authority.getAuthority().replace("ROLE_", ""))
        .collect(Collectors.toList()));

        return Jwts
         .builder()
         .setClaims(extraClaims)
         .setSubject(userDetails.getUsername())
         .setIssuedAt(new Date(System.currentTimeMillis()))
         .setExpiration(new Date(System.currentTimeMillis() + 1000 * 60 * 24))
         .signWith(getSignInKey(), SignatureAlgorithm.HS256)
         .compact();
    }

    public String generateToken(UserDetails userDetails){
        return generateToken(new HashMap<>(), userDetails);
    }

    public boolean isTokenValid(String token, UserDetails userDetails){
        final String username = extractUsername(token);
        return (username.equals(userDetails.getUsername())) && !isTokenExpired(token);
    }

    private boolean isTokenExpired(String token){
        return extractExpiration(token).before(new Date());
    }

    private Date extractExpiration(String token) {
        return extractClaim(token, Claims::getExpiration);
    }
}