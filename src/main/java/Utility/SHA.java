package Utility;

import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;

public class SHA {
    public static String convertToSHA512(String input) {
        try {
            MessageDigest md = MessageDigest.getInstance("SHA-512");

            byte[] hashBytes = md.digest(input.getBytes());

            StringBuilder hexString = new StringBuilder();
            for (byte b : hashBytes) {
                hexString.append(String.format("%02x", b));
            }
            return hexString.toString();
        } catch (NoSuchAlgorithmException exception) {
            throw new RuntimeException("SHA-512 algorithm not available", exception);
        }
    }

    public static void main(String[] args) {
        System.out.println(convertToSHA512("1"));
    }
}
