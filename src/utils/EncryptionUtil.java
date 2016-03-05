package utils;


import java.io.UnsupportedEncodingException;
import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;
import java.util.Base64;

public class EncryptionUtil {
    public static String encrypt(String str)
    {
        String encrypted_pass = null;
        try {
            MessageDigest messageDigest = MessageDigest.getInstance("SHA-256");
            messageDigest.update(str.getBytes("UTF-8"));
            byte[] messageDigestMD5 = messageDigest.digest();
            encrypted_pass = Base64.getEncoder().encodeToString(messageDigestMD5);
        } catch (NoSuchAlgorithmException | UnsupportedEncodingException e) {
            e.printStackTrace();
        }
        return encrypted_pass;
    }
}
