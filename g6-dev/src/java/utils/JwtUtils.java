package utils;



import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import dto.UserPayload;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.SignatureAlgorithm;
import java.io.IOException;
import java.util.Date;

public class JwtUtils {

    private static final String SECRET_KEY = "my_secret_key";
    private static final ObjectMapper OBJECT_MAPPER = new ObjectMapper();

    public static String generateToken(UserPayload user) {
        String subject;
        try {
            subject = OBJECT_MAPPER.writeValueAsString(user);
        } catch (JsonProcessingException e) {
            throw new IllegalStateException("Failed to convert user to JSON", e);
        }

        return Jwts.builder()
                .setSubject(subject)
                .setIssuedAt(new Date(System.currentTimeMillis()))
                .setExpiration(new Date(System.currentTimeMillis() + 600000))
                .signWith(SignatureAlgorithm.HS512, SECRET_KEY)
                .compact();
    }

    public static UserPayload validateToken(String token) {
        String subject = Jwts.parser()
                .setSigningKey(SECRET_KEY)
                .parseClaimsJws(token)
                .getBody().getSubject();

        try {
            return OBJECT_MAPPER.readValue(subject, UserPayload.class);
        } catch (JsonProcessingException e) {
            throw new IllegalStateException("Failed to convert JSON to user", e);
        } catch (IOException ex){
            throw new IllegalStateException("Failed to convert JSON to user", ex);
        }
    }
}
