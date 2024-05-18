package code;

import java.security.*;


public class HashUtil {
	public static String generateMD5Hash(String input) throws NoSuchAlgorithmException {
        return hashString(input, "MD5");
    }

    public static String generateSHA128Hash(String input) throws NoSuchAlgorithmException {
        return hashString(input, "SHA-1");
    }

    public static String generateSHA256Hash(String input) throws NoSuchAlgorithmException {
        return hashString(input, "SHA-256");
    }

    private static String hashString(String input, String algorithm) throws NoSuchAlgorithmException {
        MessageDigest md = MessageDigest.getInstance(algorithm);
        byte[] hashBytes = md.digest(input.getBytes());
        StringBuilder sb = new StringBuilder();
        for (byte b : hashBytes) {
            sb.append(String.format("%02x", b));
        }
        return sb.toString();
    }

}
