package com.zihexin.mail;

import org.thymeleaf.context.Context;

import java.io.File;
import java.util.List;
import java.util.Map;

/**
 * Created by Administrator on 2018/6/11.
 */
public interface MailService {

    void sendSimpleMail(String to,String subject,String content);

    void sendAttachmentsMail(String to, String subject, String content, Map<String,File> attachments);

    void sendTemplateMail(String to, String subject,String template, Context content, Map<String,File> attachments);


}
