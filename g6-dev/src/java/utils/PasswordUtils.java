package utils;

import at.favre.lib.crypto.bcrypt.BCrypt;

/**
 *
 * @author macbook
 */
public class PasswordUtils {
    public static String createHashPassword(String password){
        return  BCrypt.withDefaults().hashToString(6, password.toCharArray());
    }
    
    public static boolean verifyPasswordHash(String password, String passwordHash){
        BCrypt.Result result = BCrypt.verifyer().verify(password.toCharArray(), passwordHash);
        return result.verified;
    }
}
