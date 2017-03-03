package com.pgrela.wlunch.mailer;

import com.pgrela.wlunch.hunger.MenuBoard;
import com.pgrela.wlunch.restaurants.utils.TimeSource;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import javax.mail.*;
import javax.mail.internet.InternetAddress;
import javax.mail.internet.MimeMessage;
import java.io.UnsupportedEncodingException;
import java.util.Properties;

@Component
public class MenuMailer {
    @Autowired
    private TimeSource timeSource;
    @Autowired
    private ConfigurationProvider config;

    private final static Logger LOG = LoggerFactory.getLogger(MenuBoard.class);

    public void send(String body) {

        if (!config.isMailingEnabled()) {
            return;
        }

        LOG.info("Mailing enabled, sending e-mails");

        final String username = config.getMailingUsername();
        final String password = config.getMailingPassword();
        String fromName = config.getMailingFromName();
        String fromEmail = config.getMailingFromEmail();
        String toEmails = config.getMailingEmailAddresses();
        String toCcEmails = config.getMailingCcEmailAddresses();
        String toBccEmails = config.getMailingBccEmailAddresses();

        String subject = "menu na " + timeSource.getTodayDayName();

        Properties props = new Properties();
        props.put("mail.smtp.auth", "true");
        props.put("mail.smtp.starttls.enable", "true");
        props.put("mail.smtp.host", config.getMailingHost());
        props.put("mail.smtp.port", config.getMailingPort());

        if (config.isSecretKeyEnabled()) {
            body = body + ("\n\n---- " + config.getSecretKey() + " ----\n");
        }

        Session session = Session.getInstance(props,
                new javax.mail.Authenticator() {
                    protected PasswordAuthentication getPasswordAuthentication() {
                        return new PasswordAuthentication(username, password);
                    }
                });

        try {

            Message message = new MimeMessage(session);
            message.setFrom(new InternetAddress(fromEmail, fromName));
            message.setRecipients(Message.RecipientType.TO, InternetAddress.parse(toEmails));
            message.setRecipients(Message.RecipientType.CC, InternetAddress.parse(toCcEmails));
            message.setRecipients(Message.RecipientType.BCC, InternetAddress.parse(toBccEmails));
            message.setReplyTo(InternetAddress.parse(fromEmail));
            message.setSubject(subject);
            message.setText(body);

            Transport.send(message);

            LOG.info("Mailing successfully finished");

        } catch (MessagingException | UnsupportedEncodingException e) {
            throw new RuntimeException(e);
        }
    }
}
