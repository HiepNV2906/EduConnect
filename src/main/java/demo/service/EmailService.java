package demo.service;

import demo.response.EmailDetails;

public interface EmailService {
    public String sendSimpleMail(EmailDetails details);
}
