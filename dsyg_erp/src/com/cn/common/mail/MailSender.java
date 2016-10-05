package com.cn.common.mail;

import java.io.File;
import java.util.Date;
import java.util.Properties;

import javax.activation.DataHandler;
import javax.activation.FileDataSource;
import javax.mail.BodyPart;
import javax.mail.Message;
import javax.mail.Multipart;
import javax.mail.Session;
import javax.mail.Transport;
import javax.mail.internet.InternetAddress;
import javax.mail.internet.MimeBodyPart;
import javax.mail.internet.MimeMessage;
import javax.mail.internet.MimeMultipart;
import javax.mail.internet.MimeUtility;

import com.cn.common.util.PropertiesConfig;
import com.cn.common.util.StringUtil;

/**
 * MailSender
 * @author Frank
 * @version 1.0
 * @create 2016-02-13下午2:54:48
 */
public class MailSender {

	private static String mailFlg = "";
	private static String mailHost = "";
	private static String mailAuthenUser = "";
	private static String mailAuthPwd = "";

	// 默认charset
	private static String defaultCharset = "GB2312";
	// 默认 content type
	private static String defaultContentType = "text/plain";
	// MAIL_FLG
	private final static String MAIL_FLG = PropertiesConfig.getPropertiesValueByKey("MAIL_FLG");
	// 默认HOST
	private static String MAIL_HOST = PropertiesConfig.getPropertiesValueByKey("MAIL_HOST");
	// 默认AUTHEN_USER
	private static String MAIL_AUTHEN_USER = PropertiesConfig.getPropertiesValueByKey("MAIL_AUTHEN_USER");
	// 默认AUTHEN_USER密码
	private static String MAIL_AUTHEN_PWD = PropertiesConfig.getPropertiesValueByKey("MAIL_AUTHEN_PWD");
	// FROM_DEFAULT
	private final static String MAIL_FROM_DEFAULT = PropertiesConfig.getPropertiesValueByKey("MAIL_AUTHEN_USER");
	// 默认邮件收件人
	private final static String MAIL_TO_DEFAULT = "";
	// SUBJECT_DEFAULT
	private final static String MAIL_SUBJECT_DEFAULT = "SUBJECT_DEFAULT";

	static {
		// HOST
		mailHost = PropertiesConfig.getPropertiesValueByKey("MAIL_HOST");
		if (StringUtil.isBlank(mailHost)) {
			mailHost = MAIL_HOST;
		}
		// AUTHEN_USER
		mailAuthenUser = PropertiesConfig.getPropertiesValueByKey("MAIL_AUTHEN_USER");
		if (StringUtil.isBlank(mailAuthenUser)) {
			mailAuthenUser = MAIL_AUTHEN_USER;
		}
		// AUTHEN_PWD
		mailAuthPwd = PropertiesConfig.getPropertiesValueByKey("MAIL_AUTHEN_PWD");
		if (StringUtil.isBlank(mailAuthPwd)) {
			mailAuthPwd = MAIL_AUTHEN_PWD;
		}
		// Mail flag
		mailFlg = PropertiesConfig.getPropertiesValueByKey("MAIL_FLG");
		if (StringUtil.isBlank(mailFlg)) {
			mailFlg= MAIL_FLG;
		}
	}
	
	/**
	 * 发送邮件，发送者为系统默认发送者：acc_alarm@sdo-service.com
	 * @param to 接收者
	 * @param subject 主题
	 * @param body 内容
	 * @param username 发件人名
	 * @param attachfile 附件集合，格式：filename1,filename2,filename3...
	 * @return
	 * @throws Exception
	 */
	public static boolean send(String to, String subject, String body,
			String attachfile) throws Exception {
		return send(MAIL_FROM_DEFAULT, to, subject, body, defaultContentType,
				"Notify", defaultCharset, attachfile);
	}

	/**
	 * 发送邮件
	 * @param to 接收者
	 * @param subject 主题
	 * @param body 内容
	 * @param username 发件人名
	 * @param attachfile 附件集合，格式：filename1,filename2,filename3...
	 * @return
	 * @throws Exception
	 */
	public static boolean send(String from, String to, String subject, String body,
			String username, String attachfile) throws Exception {
		if(from == null || "".equals(from.trim())) {
			from = MAIL_FROM_DEFAULT;
		}
		return send(from, to, subject, body, defaultContentType,
				username, defaultCharset, attachfile);
	}

	/**
	 * 发送邮件
	 * @param to 接收者
	 * @param subject 主题
	 * @param body 内容
	 * @param fileName 附件文件名
	 * @param attachfile 附件文件流
	 * @return
	 * @throws Exception
	 */
	public static boolean send(String to, String subject, String body,
			String fileName, byte[] attachfile) throws Exception {
		return send(MAIL_FROM_DEFAULT, to, subject, body, defaultContentType,
				defaultCharset, fileName, attachfile);
	}

	/**
	 * 发送邮件
	 * @param from 发送者
	 * @param to 接收者
	 * @param subject 主题
	 * @param body 内容
	 * @param contentType contentType
	 * @param username 发件人名（可为空）
	 * @param charset charset
	 * @param file 附件集合，格式：filename1,filename2,filename3...
	 * @return
	 * @throws Exception
	 */
	public static boolean send(String from, String to, String subject,
			String body, String contentType, String username, String charset, String file)
			throws Exception {
		System.out.println("正在运行MailSender send方法");
		if (subject == null) {
			subject = MAIL_SUBJECT_DEFAULT;
		}
		if (body == null) {
			body = "";
		}
		if (from == null || "".equals(from.trim())) {
			from = MAIL_FROM_DEFAULT;
		}
		if (to == null || "".equals(to.trim())) {
			to = MAIL_TO_DEFAULT;
		}
		if (StringUtil.isBlank(username)) {
			username = "";
		}
		
		if (!mailFlg.equals("1")){
			System.out.println("Mail Flg is null");
			System.out.println("From:" + from);
			System.out.println("To:" + to);
			System.out.println("Subject:" + subject);
			System.out.println("Body:" + body);
			return true;
		}
		System.out.println("Mail sent:"  + body);

		Properties props = new Properties();
		Session session = Session.getInstance(props, null);
		props.put("mail.smtp.host", mailHost);
		props.put("mail.smtp.auth", "true");
		
		Message msg = new MimeMessage(session);
		if (from != null) {
			msg.setFrom(new InternetAddress(from, username, charset));
		}

		// 发送附件
		// 后面的BodyPart将加入到此处创建的Multipart中
		Multipart mp = new MimeMultipart();
		// Create the message part
		BodyPart messageBodyPart = new MimeBodyPart();

		messageBodyPart.setContent(body, "text/html;charset=GBK");
		mp.addBodyPart(messageBodyPart);
		if (file != null && file.trim().length() > 0) {
			// 利用枚举器方便的遍历集合
			String[] files = file.split(",");
			String filename = "";
			// 检查序列中是否还有更多的对象
			for (int i = 0; i < files.length; i++) {
				MimeBodyPart mbp = new MimeBodyPart();
				filename = PropertiesConfig.getPropertiesValueByKey("ATTACHMENT_PATH") + files[i];
				File fileTmp = new File(filename);
				if (!fileTmp.exists()) {
					continue;
				}
				// 得到数据源
				FileDataSource fds = new FileDataSource(fileTmp);
				// 得到附件本身并至入BodyPart
				mbp.setDataHandler(new DataHandler(fds));
				// 得到文件名同样至入BodyPart
				mbp.setFileName(MimeUtility.encodeWord(fds.getName(), "GB2312",
						null));
				mp.addBodyPart(mbp);
			}
		}
		// Multipart加入到信件
		msg.setContent(mp);

		msg.setRecipient(Message.RecipientType.TO, new InternetAddress(to));
		msg.setSubject(subject);

		msg.setHeader("X-Mailer", "ebs WS Email Sender");
		msg.setSentDate(new Date());

		Transport transport = session.getTransport("smtp");
		// 添加认证信息
		transport.connect(mailHost, mailAuthenUser, mailAuthPwd);
		transport.sendMessage(msg, msg.getRecipients(Message.RecipientType.TO));
		transport.close();
		return true;
	}

	/**
	 * @param from 发送者
	 * @param to 接收者
	 * @param subject 主题
	 * @param body 内容
	 * @param contentType contentType
	 * @param charset charset
	 * @param fileName 附件名
	 * @param file file
	 * @return
	 * @throws Exception
	 */
	public static boolean send(String from, String to, String subject,
			String body, String contentType, String charset, String fileName,
			byte[] file) throws Exception {
		if (subject == null) {
			subject = MAIL_SUBJECT_DEFAULT;
		}
		if (body == null) {
			body = "";
		}
		if (from == null) {
			from = MAIL_FROM_DEFAULT;
		}
		if (to == null) {
			to = MAIL_TO_DEFAULT;
		}
		Properties props = new Properties();
		Session session = Session.getInstance(props, null);
		props.put("mail.smtp.host", mailHost);
		props.put("mail.smtp.auth", "true");
		Message msg = new MimeMessage(session);
		if (from != null) {
			msg.setFrom(new InternetAddress(from));
		}
		
		if (mailFlg != "1"){
			System.out.println("Mail Flg is null");
			System.out.println("From:" + from);
			System.out.println("To:" + to);
			System.out.println("Subject:" + subject);
			System.out.println("Body:" + body);
			return true;
		}
		
		// 发送附件
		// 后面的BodyPart将加入到此处创建的Multipart中
		Multipart mp = new MimeMultipart();
		// Create the message part
		BodyPart messageBodyPart = new MimeBodyPart();

		// Fill the message
		messageBodyPart.setText(body);
		mp.addBodyPart(messageBodyPart);
		if (file != null && file.length > 0) {
			// 利用枚举器方便的遍历集合
			MimeBodyPart mbp = new MimeBodyPart();
			// 得到附件本身并至入BodyPart
			mbp.setDataHandler(new DataHandler(new ByteArrayDataSource(file,
					"application/octet-stream")));
			// 得到文件名同样至入BodyPart
			mbp.setFileName(MimeUtility.encodeWord(fileName, "GB2312", null));
			mp.addBodyPart(mbp);
		}
		// Multipart加入到信件
		msg.setContent(mp);

		msg.setRecipient(Message.RecipientType.TO, new InternetAddress(to));
		msg.setSubject(subject);

		msg.setHeader("X-Mailer", "ebs WS Email Sender");
		msg.setSentDate(new Date());

		Transport transport = session.getTransport("smtp");
		// 添加认证信息
		transport.connect(mailHost, mailAuthenUser, mailAuthPwd);
		transport.sendMessage(msg, msg.getRecipients(Message.RecipientType.TO));
		transport.close();
		return true;
	}

	public static void main(String[] args) throws Exception {
		try {
			//邮件发送人，MailSender有默认发送人。
			String from = "";
			//收件人姓名，若不填则使用MailSender的默认收件人。
			String to = "15261688@qq.com";
			//发件人名
			String username = "Test11";
			//附件，格式：filename1,filename2,filename3...（这里需要在global.properties配置文件中指定附件目录）
			String attachfile = "";
			
			//邮件标题
			String subject = "系统提示";
			
			//邮件内容
			String body = "";
			body += "亲爱的aaaaaa您好！<br/><br/>";
			body += "　　感谢您注册脚丫网，请点击以下链接激活您的账户。如果无法点击，请将以下地址复制粘贴到浏览器的地址栏中进行访问。<br/><br/>";
			body += "http://127.0.0.1:8080/dsyg"
				+ "?activationMail=aaaa@bbb.ccc"
				+ "&activationCode=fdsafdsafdsafdsafdsdd2<br/><br/><br/><br/><br/>";
			body += "------------------------------------------<br/>";
			body += "脚丫网（这是一封自动产生的email，请勿回复。）";
			
			MailSender.send(from, to, subject, body, username, attachfile);
		} catch (Exception e) {
			e.printStackTrace();
		}
	}
}
