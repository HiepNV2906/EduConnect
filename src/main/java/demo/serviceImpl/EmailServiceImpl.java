package demo.serviceImpl;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.mail.javamail.MimeMessageHelper;
import org.springframework.stereotype.Service;

import demo.exception.EmailException;
import demo.response.EmailDetails;
import demo.service.EmailService;
import jakarta.mail.internet.MimeMessage;

@Service
public class EmailServiceImpl implements EmailService{

	@Autowired
	JavaMailSender javaMailSender;
	@Value("${spring.mail.username}") 
	private String sender;
	
	@Override
	public String sendSimpleMail(EmailDetails details) {
		try {
			MimeMessage message = javaMailSender.createMimeMessage();
	        MimeMessageHelper helper = new MimeMessageHelper(message, true, "UTF-8");
			
	        helper.setFrom(sender);
	        helper.setTo(details.getRecipient());
	        helper.setSubject(details.getSubject());
	        helper.setText(details.getMsgBody(), true);

	        javaMailSender.send(message);
            
            return "Successful";
        }
        catch (Exception e) {
            throw new EmailException("Error while Sending Mail");
        }
	}

}
