package com.example.clientcomputer.payment;

import java.io.UnsupportedEncodingException;
import java.net.URLEncoder;
import java.nio.charset.StandardCharsets;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Collections;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Map;
import java.util.TimeZone;

import javax.servlet.http.HttpServletRequest;

import org.springframework.stereotype.Service;

import com.example.clientcomputer.model.Bill;

@Service
public class PaymentService {
	
	public String payWithVNPAY(Bill bill, HttpServletRequest request) throws UnsupportedEncodingException {
        Calendar cld = Calendar.getInstance(TimeZone.getTimeZone("Etc/GMT+7"));

        SimpleDateFormat formatter = new SimpleDateFormat("yyyyMMddHHmmss");
        String vnp_CreateDate = formatter.format(cld.getTime());

        cld.add(Calendar.MINUTE,15);
        String vnp_ExpireDate = formatter.format(cld.getTime());
        
        Integer vnp_TxnRef = (int) (Math.random() * 100000);

        Map<String,String> vnp_Params = new HashMap<>();
        vnp_Params.put("vnp_Version",PaymentConfig.vnp_Version);
        vnp_Params.put("vnp_Command",PaymentConfig.vnp_Command);
        vnp_Params.put("vnp_TmnCode",PaymentConfig.vnp_TmnCode);
        vnp_Params.put("vnp_Amount",bill.getAmount().toString());
        vnp_Params.put("vnp_BankCode", PaymentConfig.vnp_BankCode);
        vnp_Params.put("vnp_CreateDate", vnp_CreateDate);
        vnp_Params.put("vnp_CurrCode",PaymentConfig.vnp_CurrCode);
        vnp_Params.put("vnp_IpAddr", PaymentConfig.getIpAddress(request));
        vnp_Params.put("vnp_Locale",PaymentConfig.vnp_Locale);
        vnp_Params.put("vnp_OrderInfo",PaymentConfig.vnp_OrderInfo);
        vnp_Params.put("vnp_OrderType", PaymentConfig.vnp_OrderType);
        vnp_Params.put("vnp_ReturnUrl", PaymentConfig.vnp_ReturnUrl);
        vnp_Params.put("vnp_TxnRef", String.valueOf(vnp_TxnRef));
        vnp_Params.put("vnp_ExpireDate", vnp_ExpireDate);

        List fieldList = new ArrayList(vnp_Params.keySet());
        Collections.sort(fieldList);

        StringBuilder hashData = new StringBuilder();
        StringBuilder query = new StringBuilder();

        Iterator itr =  fieldList.iterator();
        while (itr.hasNext()){
            String fieldName = (String) itr.next();
            String fieldValue = vnp_Params.get(fieldName);
            if(fieldValue!=null && (fieldValue.length()>0)){
                hashData.append(fieldName);
                hashData.append("=");
                hashData.append(URLEncoder.encode(fieldValue, StandardCharsets.US_ASCII.toString()));

                query.append(URLEncoder.encode(fieldName, StandardCharsets.US_ASCII.toString()));
                query.append("=");
                query.append(URLEncoder.encode(fieldValue, StandardCharsets.US_ASCII.toString()));

                if(itr.hasNext()){
                    query.append("&");
                    hashData.append("&");
                }
            }
        }
        String queryUrl = query.toString();
        String vnp_SecureHash = PaymentConfig.hmacSHA512(PaymentConfig.vnp_HashSecret,hashData.toString());
        queryUrl += "&vnp_SecureHash=" + vnp_SecureHash;
        String paymentUrl = PaymentConfig.vnp_Url + "?" + queryUrl;
        return paymentUrl;
    }
}
