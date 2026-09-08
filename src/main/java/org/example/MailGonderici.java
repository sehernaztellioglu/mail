import jakarta.mail.Authenticator;
import jakarta.mail.Message;
import jakarta.mail.MessagingException;
import jakarta.mail.PasswordAuthentication;
import jakarta.mail.Session;
import jakarta.mail.Transport;
import jakarta.mail.internet.InternetAddress;
import jakarta.mail.internet.MimeMessage;

import java.util.Properties;

public class MailGonderici {

    static Session smtpAyarlari() {
        Properties ayarlar = new Properties();

        ayarlar.put("mail.smtp.auth", "true");
        ayarlar.put("mail.smtp.starttls.enable", "true");
        ayarlar.put("mail.smtp.starttls.required", "true");
        ayarlar.put("mail.smtp.host", "smtp.gmail.com");
        ayarlar.put("mail.smtp.port", "587");

        String gmailAdresi = System.getenv("GMAIL_USER");
        String uygulamaSifresi =
                System.getenv("GMAIL_APP_PASSWORD");

        if (gmailAdresi == null || uygulamaSifresi == null) {
            throw new IllegalStateException(
                    "Gmail ortam değişkenleri eksik."
            );
        }

        return Session.getInstance(
                ayarlar,
                new Authenticator() {
                    @Override
                    protected PasswordAuthentication
                    getPasswordAuthentication() {

                        return new PasswordAuthentication(
                                gmailAdresi,
                                uygulamaSifresi
                        );
                    }
                }
        );
    }

    static void mailGonder(
            String alici,
            String baslik,
            String icerik
    ) throws MessagingException {

        Session session = smtpAyarlari();
        String gmailAdresi = System.getenv("GMAIL_USER");

        MimeMessage mesaj = new MimeMessage(session);

        mesaj.setFrom(new InternetAddress(gmailAdresi));
        mesaj.setRecipient(
                Message.RecipientType.TO,
                new InternetAddress(alici, true)
        );
        mesaj.setSubject(baslik, "UTF-8");
        mesaj.setText(icerik, "UTF-8");

        Transport.send(mesaj);
    }
}