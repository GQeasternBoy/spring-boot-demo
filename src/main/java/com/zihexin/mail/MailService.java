package com.zihexin.mail;

import java.util.List;

/**
 * Created by Administrator on 2018/6/11.
 */
public interface MailService {

    void sendSimpleMail(String to,String subject,String content);

    void sendAttachmentsMail(String to, String subjeject, String content, List<Pair>)
}
