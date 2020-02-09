package progmatic.hegymaszas.configs;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.core.env.Environment;
import org.springframework.mail.SimpleMailMessage;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.mail.javamail.JavaMailSenderImpl;
import org.springframework.security.crypto.codec.Base64;

import java.util.Properties;

@Configuration
public class MailSenderConfig {

    @Autowired
    private Environment env;

    @Bean
    public JavaMailSender getJavaMailSender() {
        JavaMailSenderImpl mailSender = new JavaMailSenderImpl();
        mailSender.setHost(env.getProperty("spring.mail.host"));
        mailSender.setPort(Integer.valueOf(env.getProperty("spring.mail.port")));

        mailSender.setUsername(env.getProperty("spring.mail.username"));
        mailSender.setPassword(base64Decode(env.getProperty("spring.mail.encodedpassword")));
        //mailSender.setPassword(base64Decode("YVdkNDU/UnRa"));

        Properties props = mailSender.getJavaMailProperties();
        props.put("mail.transport.protocol", "smtp");
        props.put("mail.smtp.auth", "true");
        props.put("mail.smtp.starttls.enable", "true");
        props.put("mail.debug", "true");

        return mailSender;
    }

    @Bean
    public SimpleMailMessage templateRegistrationMessage(){
        SimpleMailMessage message = new SimpleMailMessage();
        message.setText("Üdvözlünk %s, sikeresen regisztráltál a sziklamászók weboldalán." +
                "\n" + "Felhasználóddal létrehozhatsz, törölhetsz utakat " +
                "és leírhatod a véleményed komment formájában");
        return message;
    }

    private static String base64Decode(String token){
        byte[] decodeBytes = Base64.decode(token.getBytes());
        return new String(decodeBytes);
    }

}
