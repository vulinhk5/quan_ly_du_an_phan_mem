package Signup;

import Database.Database;
import javax.mail.*;
import javax.mail.internet.*;
import java.util.Properties;

public class SignupVerificationEmail {
    private static final String fromEmail = "kayabaaki123@gmail.com";
    private static final String emailPassword = "eoeu nvxq pzfw azxy";

    public static void sendVerificationEmail(String toEmail, String userFullName, String verificationCode) {
        Properties properties = new Properties();
        properties.put("mail.smtp.host", "smtp.gmail.com");
        properties.put("mail.smtp.port", "587");
        properties.put("mail.smtp.auth", "true");
        properties.put("mail.smtp.starttls.enable", "true");

        // Tạo session
        Session session = Session.getInstance(properties, new Authenticator() {
            protected PasswordAuthentication getPasswordAuthentication() {
                return new PasswordAuthentication(fromEmail, emailPassword);
            }
        });

        try {
            MimeMessage message = new MimeMessage(session);
            message.setFrom(new InternetAddress(fromEmail));
            message.addRecipient(Message.RecipientType.TO, new InternetAddress(toEmail));
            message.setSubject("Welcome to the Library Management System - Please Verify Your Email Address");

            String emailContent =
                "Dear " + userFullName + ",\n\n" +
                    "Thank you for signing up to become a member of our Library Management System. We are excited to have you as part of our community of readers, researchers, and knowledge seekers.\n\n" +
                    "Before you can start using your account, we need to verify your email address. Please use the following verification code to complete your registration:\n\n" +
                    "Your Verification Code: " + verificationCode + "\n\n" +
                    "Simply enter the code on the verification page to activate your account.\n\n" +
                    "Important:\n" +
                    "- This verification code is valid only for this session. Once you close the application or refresh the page, the code will no longer be valid.\n" +
                    "- If you did not request this account or believe this email was sent to you by mistake, please disregard this message. Your account will not be activated until the verification process is completed.\n\n" +
                    "Once verified, you will have full access to our system, where you can:\n" +
                    "- Borrow and return books\n" +
                    "- View and manage your reading history\n" +
                    "- Reserve books online\n" +
                    "- Explore our wide range of resources\n\n" +
                    "If you encounter any issues or have questions, feel free to reach out to our support team at kayabaaki123@gmail.com.\n\n" +
                    "Thank you for joining the Library Management System. We look forward to providing you with a seamless and enriching experience!\n\n" +
                    "Best regards,\n" +
                    "The Library Management Team\n";

            message.setText(emailContent);

            Transport.send(message);
            System.out.println("Verification email sent successfully!");
        } catch (MessagingException e) {
            e.printStackTrace();
        }
    }
}
