package cn.white.bysj.utils;


import cn.white.bysj.commons.Constant;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.mail.javamail.MimeMessageHelper;
import org.springframework.mail.javamail.MimeMessagePreparator;

import javax.mail.internet.MimeMessage;

/**
 * Create by @author white
 *
 * @date 2017-12-28 14:57
 */
//邮箱验证工具类
public class EmailUtil implements Runnable{

    private String code;
    private String email;
    private JavaMailSender javaMailSender;
    private int operation;

    public EmailUtil(String code, String email, JavaMailSender javaMailSender, int operation) {
        this.code = code;
        this.email = email;
        this.javaMailSender = javaMailSender;
        this.operation = operation;
    }

    public void run() {
        javaMailSender.send(new MimeMessagePreparator() {
            public void prepare(MimeMessage mimeMessage) throws Exception {
//                System.out.println("开始发邮件...");
                MimeMessageHelper mimeMessageHelper = new MimeMessageHelper(mimeMessage,true,"UTF-8");
                mimeMessageHelper.setFrom(Constant.MAIL_FROM);
                mimeMessageHelper.setTo(email);
                mimeMessageHelper.setSubject("一封激活邮件");
                StringBuilder sb  = new StringBuilder();
                sb.append("<html><head></head><body>");

                if(operation==1){
                    sb.append("<a href="+Constant.DOMAIN_NAME+"user/activate.do?code=");
                    sb.append(code);
                    sb.append(">点击激活</a></body>");
                }else{
                    sb.append("是否将您的密码修改为:");
                    sb.append(code.substring(0,8));
                    sb.append("，<a href="+Constant.DOMAIN_NAME+"verify.do?code="+code+">");
                    sb.append("点击是</a></body>");
                }
                mimeMessageHelper.setText(sb.toString(),true);
//                System.out.println("结束发邮件...");
            }
        });
    }
}
