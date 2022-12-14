package com.example.clientcomputer.mail;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.experimental.SuperBuilder;

@Data
@AllArgsConstructor
@NoArgsConstructor
@SuperBuilder
public class EmailDetail {
	private String recipient;
	private String msgBody;
	private String subject;
	private String attachment;
	
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
    			+ "                            <h3>B???n ???? y??u c???u c???p l???i m???t kh???u</h3>\r\n"
    			+ "                            <span style=\"display:inline-block;vertical-align:middle;margin:29px 0 26px;border-bottom:1px solid #cecece;width:100px\"></span>\r\n"
    			+ "                        	<p>????? kh??i ph???c m???t kh???u vui l??ng b???m x??c th???c</p>\r\n"
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
    			+ "                                                	<a href=\"http://localhost:8080/reset-password?token=hash\" target=\"_blank\" style=\"font-size: 20px; font-family: 'Montserrat Bold'Helvetica, Arial, sans-serif; color: #ffffff; text-decoration: none; color: #ffffff; text-decoration: none; padding: 10px 55px; border-radius: 2px; display: inline-block;\">\r\n"
    			+ "                                                		X??C TH???C\r\n"
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
    			+ "                            <p style=\"margin: 0;\">Ho???c, b???n c?? th??? ch??p ???????ng d???n n??y v??o tr??nh duy???t c???a b???n:</p>\r\n"
    			+ "                        </td>\r\n"
    			+ "                    </tr> <!-- COPY -->\r\n"
    			+ "                    <tr>\r\n"
    			+ "                        <td bgcolor=\"#ffffff\" align=\"center\" style=\"padding: 20px 30px 20px 30px; color: #666666; font-family:'Montserrat'Helvetica, Arial, sans-serif; font-size: 14px; font-weight: 550; line-height: 25px;\">\r\n"
    			+ "                            <p style=\"margin: 0;\"><a href=\"#\" target=\"_blank\" style=\"color: #29ABE2;\">https://www.google.com/</a></p>\r\n"
    			+ "                        </td>\r\n"
    			+ "                    </tr>\r\n"
    			+ "                    <tr>\r\n"
    			+ "                        <td bgcolor=\"#ffffff\" align=\"center\" style=\"padding: 0px 30px 20px 30px; color: #000000; font-family:'Montserrat'Helvetica, Arial, sans-serif; font-size: 14px; font-weight: 400; line-height: 25px;\">\r\n"
    			+ "                            <p style=\"margin: 0;\">Li??n k???t s??? m???t hi???u l???c sau 24 ti???ng.</p>\r\n"
    			+ "                        </td>\r\n"
    			+ "                    </tr>\r\n"
    			+ "                    <tr>\r\n"
    			+ "                        <td bgcolor=\"#ffffff\" align=\"center\" style=\"padding: 0px 30px 40px 30px; border-radius: 0px 0px 4px 4px; color: #000000; font-family:'Montserrat'Helvetica, Arial, sans-serif; font-size: 14px; font-weight: 400; line-height: 25px;\">\r\n"
    			+ "                            <p style=\"margin: 0;\">Li??n h???: <a href=\"#\" target=\"_blank\" style=\"color: #29ABE2;\">pvbcomputer@gmail.com</a></p>\r\n"
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
