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
    private String content;

    public EmailUtil(String code, String email, JavaMailSender javaMailSender, String content, int operation) {
        this.code = code;
        this.email = email;
        this.javaMailSender = javaMailSender;
        this.operation = operation;
        this.content = content;
    }

    public void run() {
        javaMailSender.send(new MimeMessagePreparator() {
            public void prepare(MimeMessage mimeMessage) throws Exception {
                MimeMessageHelper mimeMessageHelper = new MimeMessageHelper(mimeMessage,true,"UTF-8");
                mimeMessageHelper.setFrom(Constant.MAIL_FROM);
                mimeMessageHelper.setTo(email);
                mimeMessageHelper.addCc(Constant.MAIL_FROM);
                StringBuilder sb  = new StringBuilder();
                sb.append("<html><head></head><body>");

                if(operation==1){
                    mimeMessageHelper.setSubject("欢迎注册CloudNote在线云笔记系统");
                    sb.append("<div>感谢注册该在线云笔记系统，请点击链接进行激活，我们将竭力带给你最好的体验！！！</div>");
                    sb.append("<a href="+Constant.DOMAIN_NAME+"/user/activate/");
                    sb.append(code);
                    sb.append(">点击激活</a></body>");
                }else if (operation == 0){
                    sb.append("是否将您的密码修改为:");
                    sb.append(code.substring(0,8));
                    sb.append("，<a href="+Constant.DOMAIN_NAME+"verify.do?code="+code+">");
                    sb.append("点击是</a></body>");
                }else if (operation == 2){
                    mimeMessageHelper.setSubject("您好，这是云笔记回复你的邮箱，请查看");
                    sb.append("<div>"+content+"</div>");
                }
                mimeMessageHelper.setText(sb.toString(),true);
//                System.out.println("结束发邮件...");
            }
        });
    }
}
