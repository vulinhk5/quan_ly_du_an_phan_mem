package Utility;

import java.security.SecureRandom;

public class RandomGenerator {
    public static String generateVerificationCode() {
        final int verificationCodeLength = 9;
        SecureRandom random = new SecureRandom();
        StringBuilder builder = new StringBuilder(verificationCodeLength);
        for (int i = 0; i < verificationCodeLength; i++) {
            builder.append(random.nextInt(10));
        }

        return builder.toString();
    }

    public static String generateRandomUserId() {
        final int UserIDLength = 12;
        SecureRandom random = new SecureRandom();
        StringBuilder builder = new StringBuilder(UserIDLength);
        for (int i = 0; i < UserIDLength; i++) {
            builder.append(random.nextInt(10));
        }

        return builder.toString();
    }

    public static String generateNewPassword() {
        String chars = "ABCDEFGHIJKLMNOPQRSTUVWXYZabcdefghijklmnopqrstuvwxyz0123456789!@#$%^&*";
        SecureRandom random = new SecureRandom();
        StringBuilder password = new StringBuilder(12);
        for (int i = 0; i < 12; i++) {
            password.append(chars.charAt(random.nextInt(chars.length())));
        }
        return password.toString();
    }


}
