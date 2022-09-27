package com.example.clientcomputer.mail;

import java.io.File;

import javax.mail.Message;
import javax.mail.MessagingException;
import javax.mail.internet.InternetAddress;
import javax.mail.internet.MimeMessage;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.core.io.FileSystemResource;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.mail.javamail.MimeMessageHelper;
import org.springframework.stereotype.Service;

@Service
public class EmailServiceImpl implements EmailService {
	@Autowired
	private JavaMailSender javaMailSender;
	@Value("${spring.mail.username}")
	private String sender;

	@Override
	public String sendSimpleMail(EmailDetail detail) {
		try {
			MimeMessage mimeMessage = javaMailSender.createMimeMessage();
			MimeMessageHelper mimeMessageHelper = new MimeMessageHelper(mimeMessage, true);

			mimeMessageHelper.setFrom(sender, "Electro Shop Support");
			mimeMessageHelper.setTo(detail.getRecipient());
			mimeMessageHelper.setText(detail.getMsgBody(), true);
			mimeMessageHelper.setSubject(detail.getSubject());

			javaMailSender.send(mimeMessage);
			return "Mail Sent Successfully...";
		} catch (Exception e) {
			return "Error while Sending Mail";
		}
	}

	@Override
	public String sendMailWithAttachment(EmailDetail detail) {
		MimeMessage mimeMessage = javaMailSender.createMimeMessage();
		MimeMessageHelper mimeMessageHelper;

		try {
			mimeMessageHelper = new MimeMessageHelper(mimeMessage, true);
			mimeMessageHelper.setFrom(sender);
			mimeMessageHelper.setTo(detail.getRecipient());
			mimeMessageHelper.setText(detail.getMsgBody());
			mimeMessageHelper.setSubject(detail.getSubject());

			// Adding the attachment
			FileSystemResource file = new FileSystemResource(new File(detail.getAttachment()));

			mimeMessageHelper.addAttachment(file.getFilename(), file);

			// Sending the mail
			javaMailSender.send(mimeMessage);
			return "Mail sent Successfully";
		}

		// Catch block to handle MessagingException
		catch (MessagingException e) {

			// Display message when exception occurred
			return "Error while sending mail!!!";
		}
	}

	public static String bodyRP(String hash) {//reset password
    	String bodyString = "<body style=\"background-color: #f4f4f4; margin: 0 !important; padding: 0 !important;\">\r\n"
    			+ "    <table border=\"0\" cellpadding=\"0\" cellspacing=\"0\" width=\"100%\">\r\n"
    			+ "        <!-- LOGO -->\r\n"
    			+ "        <tr>\r\n"
    			+ "            <td bgcolor=\"#f4f4f4\" align=\"center\">\r\n"
    			+ "                <table border=\"0\" cellpadding=\"0\" cellspacing=\"0\" width=\"100%\" style=\"max-width: 600px;\">\r\n"
    			+ "                    <tr>\r\n"
    			+ "                        <td align=\"center\" valign=\"top\" style=\"padding: 40px 10px 40px 10px;\"> </td>\r\n"
    			+ "                    </tr>\r\n"
    			+ "                </table>\r\n"
    			+ "            </td>\r\n"
    			+ "        </tr>\r\n"
    			+ "        <tr>\r\n"
    			+ "            <td bgcolor=\"#f4f4f4\" align=\"center\" style=\"padding: 0px 10px 0px 10px;\">\r\n"
    			+ "                <table border=\"0\" cellpadding=\"0\" cellspacing=\"0\" width=\"100%\" style=\"max-width: 600px;\">\r\n"
    			+ "                    <tr>\r\n"
    			+ "                        <td bgcolor=\"#ffffff\" align=\"center\" valign=\"top\" style=\"padding: 40px 20px 20px 20px; border-radius: 2px 2px 0px 0px; color: #AADB1E; font-family: 'Londrina Solid'Helvetica, Arial, sans-serif; font-size: 45px; font-weight: 700; letter-spacing: 2px; line-height: 48px;\">\r\n"
    			+ "                            <img alt=\"\" src=\"<c:url value ='/templates/web/images/logo.png'/>\">\r\n"
    			+ "                            <h1 style=\"font-size: 40px; font-weight:700; margin: w-50;\"></h1>\r\n"
    			+ "                        </td>\r\n"
    			+ "                    </tr>\r\n"
    			+ "                </table>\r\n"
    			+ "            </td>\r\n"
    			+ "        </tr>\r\n"
    			+ "        <tr>\r\n"
    			+ "            <td bgcolor=\"#f4f4f4\" align=\"center\" style=\"padding: 0px 10px 0px 10px;\">\r\n"
    			+ "                <table border=\"0\" cellpadding=\"0\" cellspacing=\"0\" width=\"100%\" style=\"max-width: 600px;\">\r\n"
    			+ "                    <tr>\r\n"
    			+ "                        <td bgcolor=\"#ffffff\" align=\"center\" style=\"padding: 20px 30px 40px 30px; color: #000000; font-family:'Montserrat bold' Helvetica, Arial, sans-serif; font-size: 16px; font-weight:600; line-height: 25px;\">\r\n"
    			+ "                            <h3>Bạn đã yêu cầu cấp lại mật khẩu</h3>\r\n"
    			+ "                            <span style=\"display:inline-block;vertical-align:middle;margin:29px 0 26px;border-bottom:1px solid #cecece;width:100px\"></span>\r\n"
    			+ "                        	<p>Để khôi phục mật khẩu vui lòng bấm xác thực</p>\r\n"
    			+ "                        </td>\r\n"
    			+ "                    </tr>\r\n"
    			+ "                    <tr>\r\n"
    			+ "                        <td bgcolor=\"#ffffff\" align=\"left\">\r\n"
    			+ "                            <table width=\"100%\" border=\"0\" cellspacing=\"0\" cellpadding=\"0\">\r\n"
    			+ "                                <tr>\r\n"
    			+ "                                    <td bgcolor=\"#ffffff\" align=\"center\" style=\"padding: 20px 30px 60px 30px;\">\r\n"
    			+ "                                        <table border=\"0\" cellspacing=\"0\" cellpadding=\"0\">\r\n"
    			+ "                                            <tr>\r\n"
    			+ "                                                <td align=\"center\" style=\"border-radius: 30px;\" bgcolor=\"#000000\">\r\n"
    			+ "                                                	<a href=\"http://localhost:9999/LTWEB/lam-moi-mat-khau?hash\" target=\"_blank\" style=\"font-size: 20px; font-family: 'Montserrat Bold'Helvetica, Arial, sans-serif; color: #ffffff; text-decoration: none; color: #ffffff; text-decoration: none; padding: 10px 55px; border-radius: 2px; display: inline-block;\">\r\n"
    			+ "                                                		XÁC THỰC\r\n"
    			+ "                                                	</a>	\r\n"
    			+ "                                                </td>\r\n"
    			+ "                                            </tr>\r\n"
    			+ "                                        </table>\r\n"
    			+ "                                    </td>\r\n"
    			+ "                                </tr>\r\n"
    			+ "                            </table>\r\n"
    			+ "                        </td>\r\n"
    			+ "                    </tr> <!-- COPY -->\r\n"
    			+ "                    <tr>\r\n"
    			+ "                        <td bgcolor=\"#ffffff\" align=\"center\" style=\"padding: 0px 30px 0px 30px; color: #000000; font-family:'Montserrat'Helvetica, Arial, sans-serif; font-size: 14px; font-weight:550; line-height: 25px;\">\r\n"
    			+ "                            <p style=\"margin: 0;\">Hoặc, bạn có thể chép đường dẫn này vào trình duyệt của bạn:</p>\r\n"
    			+ "                        </td>\r\n"
    			+ "                    </tr> <!-- COPY -->\r\n"
    			+ "                    <tr>\r\n"
    			+ "                        <td bgcolor=\"#ffffff\" align=\"center\" style=\"padding: 20px 30px 20px 30px; color: #666666; font-family:'Montserrat'Helvetica, Arial, sans-serif; font-size: 14px; font-weight: 550; line-height: 25px;\">\r\n"
    			+ "                            <p style=\"margin: 0;\"><a href=\"#\" target=\"_blank\" style=\"color: #29ABE2;\">https://www.google.com/</a></p>\r\n"
    			+ "                        </td>\r\n"
    			+ "                    </tr>\r\n"
    			+ "                    <tr>\r\n"
    			+ "                        <td bgcolor=\"#ffffff\" align=\"center\" style=\"padding: 0px 30px 20px 30px; color: #000000; font-family:'Montserrat'Helvetica, Arial, sans-serif; font-size: 14px; font-weight: 400; line-height: 25px;\">\r\n"
    			+ "                            <p style=\"margin: 0;\">Liên kết sẽ mất hiệu lực sau 24 tiếng.</p>\r\n"
    			+ "                        </td>\r\n"
    			+ "                    </tr>\r\n"
    			+ "                    <tr>\r\n"
    			+ "                        <td bgcolor=\"#ffffff\" align=\"center\" style=\"padding: 0px 30px 40px 30px; border-radius: 0px 0px 4px 4px; color: #000000; font-family:'Montserrat'Helvetica, Arial, sans-serif; font-size: 14px; font-weight: 400; line-height: 25px;\">\r\n"
    			+ "                            <p style=\"margin: 0;\">Liên hệ: <a href=\"#\" target=\"_blank\" style=\"color: #29ABE2;\">pvbcomputer@gmail.com</a></p>\r\n"
    			+ "                        </td>\r\n"
    			+ "                    </tr>\r\n"
    			+ "                    <tr>\r\n"
    			+ "                        <td bgcolor=\"#ffffff\" align=\"center\" style=\"padding: 0px 30px 40px 30px; border-radius: 0px 0px 4px 4px; color: #333333; font-family:'Montserrat'Helvetica, Arial, sans-serif; font-size: 14px; font-weight: 400; line-height: 25px;\"> <img src=\"https://img.icons8.com/ios-glyphs/30/000000/facebook-new.png\" /> <img src=\"https://img.icons8.com/material-outlined/30/000000/instagram-new.png\" /> </td>\r\n"
    			+ "                    </tr>\r\n"
    			+ "                </table>\r\n"
    			+ "            </td>\r\n"
    			+ "        </tr>\r\n"
    			+ "        <tr>\r\n"
    			+ "            <td bgcolor=\"#f4f4f4\" align=\"center\" style=\"padding: 0px 10px 0px 10px;\">\r\n"
    			+ "                <table border=\"0\" cellpadding=\"0\" cellspacing=\"0\" width=\"100%\" style=\"max-width: 600px;\">\r\n"
    			+ "                    <tr>\r\n"
    			+ "                        <td bgcolor=\"#f4f4f4\" align=\"center\" style=\"padding: 0px 30px 30px 30px; color: #666666; font-family: 'Lato', Helvetica, Arial, sans-serif; font-size: 14px; font-weight: 400; line-height: 18px;\"> <br>\r\n"
    			+ "                            <p style=\"margin: ;\">\r\n"
    			+ "                            	<a href=\"#\" target=\"_blank\" style=\"color: #111111; font-weight: 700;\"></a>\r\n"
    			+ "                            </p> \r\n"
    			+ "                        </td> \r\n"
    			+ "                  	</tr> \r\n"
    			+ "               	</table> \r\n"
    			+ "           	</td> \r\n"
    			+ "       	</tr> \r\n"
    			+ "	</table> \r\n"
    			+ "</body> ";
    	return bodyString.replace("hash",hash);
    }
}
