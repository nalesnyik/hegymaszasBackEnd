package progmatic.hegymaszas.services;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.mail.MailException;
import org.springframework.mail.SimpleMailMessage;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.stereotype.Service;
import progmatic.hegymaszas.modell.MyUser;

@Service
public class EmailService {

    @Autowired
    JavaMailSender mailSender;

    @Autowired
    SimpleMailMessage mailMessage;

    public void sendSimpleEmail(String mailTo, String subject, String text){
        SimpleMailMessage message = new SimpleMailMessage();
        try {
            message.setTo(mailTo);
            message.setSubject(subject);
            message.setText(text);
            mailSender.send(message);
        } catch (MailException me){
            me.printStackTrace();
        }
    }

    public void sendRegistrationEmail(MyUser user){
        String mailTo = user.getEmail();
        String subject = "Sikeres regisztráció";
        String text = String.format(
                "Üdvözlünk %s, sikeresen regisztráltál a sziklamászók weboldalán." +
                        "\n" + "Felhasználóddal létrehozhatsz, törölhetsz utakat " +
                        "és leírhatod a véleményed komment formájában", user.getName()
        );
        sendSimpleEmail(mailTo, subject, text);
    }

}
