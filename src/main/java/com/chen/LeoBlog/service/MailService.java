package com.chen.LeoBlog.service;

import com.chen.LeoBlog.dao.EmailDao;
import com.chen.LeoBlog.po.EmailConfirm;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.mail.SimpleMailMessage;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.stereotype.Service;

@Service
public class MailService {

    //定义邮件发送器
    @Autowired
    private JavaMailSender mailSender;
    @Autowired
    private EmailDao emailDao;

    public int sendConfirmCode(EmailConfirm emailConfirm){
        return emailDao.sendConfirmCode(emailConfirm);
    }
    public int updateCode(EmailConfirm emailConfirm){
        return emailDao.updateCode(emailConfirm);
    }
    public int getConfirmCode(String userEmail){
        return emailDao.getConfirmCode(userEmail);
    }
    public int getConfirmCodeLen(String userEmail){
        return emailDao.getConfirmCodeLen(userEmail);
    }
    public int deleteCode(EmailConfirm emailConfirm){
        return emailDao.deleteCode(emailConfirm);
    }

    //定义邮件发送者
    @Value("${spring.mail.username}")
    private String from;

    /**
     * @param to 接收者
     * @param subject 主题
     * @param content 内容
     * @Value注解读取配置文件中同名的配置值
     */

    public void sendSimpleMail(String to, String subject, String content) {
        System.out.println("发送邮件===");
        //创建一个简单文本邮件的对象
        SimpleMailMessage message = new SimpleMailMessage();
        //赋予相应的内容
        message.setTo(to);
        message.setSubject(subject);
        message.setText(content);
        message.setFrom(from);

        //将邮件对象赋予邮件发送器
        try{
            mailSender.send(message);
            System.out.println("发送邮件成功！");
        }catch (Exception e){
            e.printStackTrace();
            System.out.println("发送邮件失败！");
        }

    }

}
