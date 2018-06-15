package com.zihexin.mail;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.mail.SimpleMailMessage;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.mail.javamail.MimeMessageHelper;
import org.springframework.stereotype.Service;
import org.thymeleaf.Template;
import org.thymeleaf.context.Context;
import org.thymeleaf.spring4.SpringTemplateEngine;

import javax.mail.MessagingException;
import javax.mail.internet.MimeMessage;
import java.io.File;
import java.util.Map;

/**
 * Created by Administrator on 2018/6/11.
 */
@Service
public class MailServiceImpl implements MailService{

    @Autowired
    private JavaMailSender mailSender;

    @Autowired
    private SpringTemplateEngine templateEngine;

    @Value("${spring.mail.username}")
    private String mailFrom;

    /**
     * 发送简单邮件
     * @param to
     * @param subject
     * @param content
     */
    @Override
    public void sendSimpleMail(String to, String subject, String content) {
        SimpleMailMessage mailMessage = new SimpleMailMessage();
        mailMessage.setFrom(mailFrom);
        mailMessage.setTo(to);
        mailMessage.setSubject(subject);
        mailMessage.setText(content);
        mailSender.send(mailMessage);
    }

    /**
     * 发送带附件的邮件
     * @param to
     * @param subject
     * @param content
     * @param attachments
     */
    @Override
    public void sendAttachmentsMail(String to, String subject, String content, Map<String, File> attachments) {
        MimeMessage mimeMessage = mailSender.createMimeMessage();

        MimeMessageHelper helper = null;
        try {
            helper = new MimeMessageHelper(mimeMessage,true);

            helper.setFrom(mailFrom);
            helper.setTo(to);
            helper.setSubject(subject);
            helper.setText(content);

            for (Map.Entry<String,File> attachment : attachments.entrySet()){
                String key = attachment.getKey();
                File value = attachment.getValue();
                helper.addAttachment(key,value);
            }
        } catch (MessagingException e) {
            e.printStackTrace();
        }
        mailSender.send(mimeMessage);
    }

    @Override
    public void sendTemplateMail(String to, String subject, String template,Context content, Map<String, File> attachments) {
        MimeMessage mimeMessage = mailSender.createMimeMessage();

        try {
            MimeMessageHelper helper = new MimeMessageHelper(mimeMessage,true);

            helper.setFrom(mailFrom);
            helper.setTo(to);
            helper.setSubject(subject);
            String text = templateEngine.process(template, content);
            helper.setText(text,true);
        } catch (Exception e) {
            e.printStackTrace();
        }
        mailSender.send(mimeMessage);
    }


}
