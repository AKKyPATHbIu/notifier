/**
 * Класс для email повідомлення.
 * Для відправки використовується javax.mail.
 * Викликаэться через рефлексію в конструкторі Task.java
 * Реалізує конкретний механізм відправки цього типа повідомлення
 */
package msgsender;

import enums.SenderType;
import org.springframework.beans.factory.annotation.Autowired;
import senderparams.EmailParams;

import javax.mail.*;
import javax.mail.internet.InternetAddress;
import javax.mail.internet.MimeMessage;
import java.util.Properties;
import java.util.logging.Logger;

/** Сповіщувач email. */
public class EmailMsgSender extends BaseMsgSender {

    private static Logger logger = Logger.getLogger(EmailMsgSender.class.getName());

    /** Параметри відправника електроних листів. */
    private EmailParams params;

    /** Конструктор за замовчанням. */
    public EmailMsgSender() {
        super(SenderType.EMAIL);
    }

    /**
     * Встановити параметри відправника електроних листів
     * @param params параметри відправника електроних листів
     */
    @Autowired
    public void setParams(EmailParams params) {
        this.params = params;
    }

    @Override
    public void send(MsgDetail msgDetail) {
        Properties mailProps = new Properties();
        mailProps.put("mail.smtp.ssl.enable", "true");
        mailProps.setProperty("mail.smtp.auth", "true");

        Session session = Session.getInstance(mailProps, new Authenticator() {
            protected PasswordAuthentication getPasswordAuthentication() {
            return new PasswordAuthentication(params.getLogin(), params.getPassword());
            }
        });

        try {
            Message message = new MimeMessage(session);
            message.setFrom(new InternetAddress(params.getFrom()));
            message.setRecipients(Message.RecipientType.TO, InternetAddress.parse(msgDetail.getAddress()));
            message.setSubject(msgDetail.getSubject());
            message.setText(msgDetail.getBody());

            Transport transport = session.getTransport("smtps");
            transport.connect(params.getSmtpServer(), params.getSmptPort(), params.getLogin(), params.getPassword());
            transport.sendMessage(message, message.getAllRecipients());
            transport.close();
            logger.info("Mail sent.");

        } catch (MessagingException e) {
            e.printStackTrace();
            throw new RuntimeException(e);
        }
    }

    @Override
    public boolean isEnabled() {
        return params.isEnabled();
    }
}
