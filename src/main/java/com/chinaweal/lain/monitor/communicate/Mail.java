package com.chinaweal.lain.monitor.communicate;

import org.apache.commons.mail.DefaultAuthenticator;
import org.apache.commons.mail.HtmlEmail;

import java.awt.*;

/**
 * Created with IntelliJ IDEA.
 * User: Lain
 * Date: 15-12-9
 */
public class Mail {
    private String[] sendTo;
    private String[] ccTo;
    private String subject;
    private String content;

    public Mail(String[] sendTo, String[] ccTo, String subject, String content) {
        this.sendTo = sendTo;
        this.ccTo = ccTo;
        this.subject = subject;
        this.content = content;
    }


    public void sendMail() {
        try {
            HtmlEmail email = new HtmlEmail();
            email.setHostName("mail.chinaweal.com.cn");
            email.setAuthenticator(new DefaultAuthenticator("zhzhy", "011235"));
            email.setFrom("zhzhy@chinaweal.com.cn");    //������

            email.setSubject(subject);   //�ʼ�����
            email.addTo(sendTo); //������
            email.addCc(ccTo); //������


            // set the html message
            email.setCharset("gbk");
            email.setHtmlMsg(content);
            System.out.println(content);
            email.send();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public static void main(String[] args) throws Throwable {

//        Email email = new SimpleEmail();
        HtmlEmail email = new HtmlEmail();
        email.setHostName("mail.chinaweal.com.cn");
        email.setAuthenticator(new DefaultAuthenticator("zhzhy", "011235"));
        email.setFrom("zhzhy@chinaweal.com.cn");    //������
        email.setSubject("TestMail");   //�ʼ�����
        String[] sendTo = new String[]{"zhouzy@chinaweal.com.cn"};
        String[] ccTo = new String[]{"zhzhy@chinaweal.com.cn"};
        email.addTo(sendTo); //������
//        email.addCc(ccTo); //������

//        email.setMsg("This is a test mail ... :-)");

//        String htmlMsg = "<html><h1>The apache logo</h1>" +
//                "<table>" +
//                "<tr>" +
//                "<td>�����쳣</td>" +
//                "<td>123</td>" +
//                "</tr>" +
//                "</table> </html>";
        String htmlMsg = "<html>" +
                "<head>" +
                "<title>��ҳֱ������</title>" +
                "</head>" +
                "<body>" +
                "<table   align ='center' border='1'>" +
                "<tr> <td   height= '10'> first</td> </tr> " +
                "<tr> <td   height= '10'> second</td> </tr> " +
                "</table> " +
//                "<table   align='center' width= 0.1%>" +
//                "<tr> <td   height='200'>second </td> </tr> " +
//                "</table> " +
                "</body>" +
                "</html>";

        // set the html message
        email.setCharset("gbk");
        email.setHtmlMsg(htmlMsg);


        // set the alternative message
        email.setTextMsg("Your email client does not support HTML messages");

        email.send();
    }
}
