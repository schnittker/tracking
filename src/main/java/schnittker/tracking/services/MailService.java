package schnittker.tracking.services;

import lombok.extern.slf4j.Slf4j;
import schnittker.tracking.helper.PropertiesLoader;

import javax.mail.Message;
import javax.mail.MessagingException;
import javax.mail.PasswordAuthentication;
import javax.mail.Session;
import javax.mail.Transport;
import javax.mail.internet.InternetAddress;
import javax.mail.internet.MimeMessage;
import java.util.Properties;
import java.util.logging.Logger;

/**
 * For google mail
 * @see "https://support.google.com/accounts/answer/185833?p=InvalidSecondFactor"
 */
@Slf4j
public class MailService {
    private final Properties properties;

    public MailService() {
        properties = new PropertiesLoader().loadProperties("mail.properties");
    }

    public void send(String subject, String content) {
        final String fromAddress=properties.getProperty("mail.from_address");
        final String toAddress=properties.getProperty("mail.to_address");
        final String password=properties.getProperty("mail.password");

        Session session = Session.getDefaultInstance(properties, new javax.mail.Authenticator() {
            protected PasswordAuthentication getPasswordAuthentication() {
                return new PasswordAuthentication(fromAddress,password);
            }
        });

        try {
            MimeMessage message = new MimeMessage(session);
            message.setFrom(new InternetAddress(fromAddress));
            message.addRecipient(Message.RecipientType.TO,new InternetAddress(toAddress));
            message.setSubject(subject);
            message.setText(content);

            Transport.send(message);
        } catch (MessagingException e) {
            log.error(e.getMessage());
        }
    }
}
