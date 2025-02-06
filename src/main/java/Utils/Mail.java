package Utils;

import java.io.File;
import java.util.Properties;
import javax.mail.*;
import javax.mail.internet.*;

import io.github.cdimascio.dotenv.Dotenv;

public class Mail {
	static Dotenv dotenv = Dotenv.configure().load();
	
	static String gmail_account = dotenv.get("gmail_account");
	static String gmail_public = dotenv.get("gmail_public");
	
    private static Session createSession() {
        Properties properties = new Properties();
        properties.put("mail.smtp.host", "smtp.gmail.com"); // Gmail SMTP server
        properties.put("mail.smtp.port", "587"); // Port for TLS
        properties.put("mail.smtp.auth", "true");
        properties.put("mail.smtp.starttls.enable", "true"); // Enable TLS

        return Session.getInstance(properties, new Authenticator() {
            @Override
            protected PasswordAuthentication getPasswordAuthentication() {
                return new PasswordAuthentication(gmail_account, gmail_public);
            }
        });
    }

    public static boolean sendEmail(String recipient, String subject, String body) {
        try {
            Session session = createSession();

            // Create the email message
            MimeMessage email = new MimeMessage(session);
            email.setFrom(new InternetAddress(gmail_account)); // Use the sender's email
            email.setRecipient(Message.RecipientType.TO, new InternetAddress(recipient));
            email.setSubject(subject);
            email.setText(body);

            // Send the email
            Transport.send(email);

            System.out.println("Email sent successfully!");
            return true;
            
        } catch (MessagingException e) {
            e.printStackTrace();
            System.out.println(e.getMessage());
            return false;
        }
    }
}
