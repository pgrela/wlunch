package com.pgrela.wlunch.utils;

import java.io.UnsupportedEncodingException;
import java.util.Properties;

import javax.mail.Message;
import javax.mail.MessagingException;
import javax.mail.PasswordAuthentication;
import javax.mail.Session;
import javax.mail.Transport;
import javax.mail.internet.InternetAddress;
import javax.mail.internet.MimeMessage;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

@Component
public class MenuMailer {
    @Autowired
    private TimeSource timeSource;
    @Autowired
    private ConfigurationProvider config;

    public void send(String body) {

        if (!config.isMailingEnabled()) {
            return;
        }

        final String username = config.getMailingUsername();
        final String password = config.getMailingPassword();
        String fromName = config.getMailingFromName();
        String fromEmail = config.getMailingFromEmail();
        String toEmail = config.getMailingEmailAddress();

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
            message.setRecipients(Message.RecipientType.TO, InternetAddress.parse(toEmail));
            message.setReplyTo(InternetAddress.parse(fromEmail));
            message.setSubject(subject);
            message.setText(body);

            Transport.send(message);

        } catch (MessagingException e) {
            throw new RuntimeException(e);
        } catch (UnsupportedEncodingException e) {
            throw new RuntimeException(e);
        }
    }
}
