package ForgotPassword;

import Utility.RandomGenerator;
import javax.mail.*;
import javax.mail.internet.*;
import java.util.Properties;
import java.security.SecureRandom;

public class NewPasswordEmail {

    private static final String fromEmail = "kayabaaki123@gmail.com";
    private static final String emailPassword = "eoeu nvxq pzfw azxy";

    public static void sendPasswordResetEmail(String toEmail, String userFullName, String newPassword) {
        Properties properties = new Properties();
        properties.put("mail.smtp.host", "smtp.gmail.com");
        properties.put("mail.smtp.port", "587");
        properties.put("mail.smtp.auth", "true");
        properties.put("mail.smtp.starttls.enable", "true");

        Session session = Session.getInstance(properties, new Authenticator() {
            protected PasswordAuthentication getPasswordAuthentication() {
                return new PasswordAuthentication(fromEmail, emailPassword);
            }
        });

        try {
            MimeMessage message = new MimeMessage(session);
            message.setFrom(new InternetAddress(fromEmail));
            message.addRecipient(Message.RecipientType.TO, new InternetAddress(toEmail));
            message.setSubject("Password Reset Request");

            String emailContent =
                "Dear " + userFullName + ",\n\n" +
                    "We received a request to reset your password for your account. Below is your new password:\n\n" +
                    "New Password: " + newPassword + "\n\n" +
                    "Please use this new password to log in to your account. For security reasons, we recommend that you change your password after logging in.\n\n" +
                    "If you did not request a password reset, please disregard this email. Your account will not be affected unless you take action to reset the password.\n\n" +
                    "If you encounter any issues or have any questions, feel free to reach out to our support team at your-email@example.com.\n\n" +
                    "Thank you for using our service!\n\n" +
                    "Best regards,\n" +
                    "The Support Team\n";

            message.setText(emailContent);

            Transport.send(message);
            System.out.println("Password reset email sent successfully!");
        } catch (MessagingException e) {
            e.printStackTrace();
        }
    }
}
