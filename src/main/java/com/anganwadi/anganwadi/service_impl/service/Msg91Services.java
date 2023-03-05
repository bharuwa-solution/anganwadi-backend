package com.anganwadi.anganwadi.service_impl.service;

import com.anganwadi.anganwadi.domains.entity.OtpDetails;
import org.apache.http.HttpResponse;
import org.apache.http.client.methods.HttpGet;
import org.apache.http.impl.client.CloseableHttpClient;
import org.apache.http.impl.client.HttpClientBuilder;
import org.springframework.scheduling.annotation.Async;

import java.io.IOException;
import java.net.URLEncoder;


public class Msg91Services {

    @Async("threadPoolTaskExecutor")
    public static OtpDetails sendRegSms(String otp, String mobileNumber) throws IOException {
        OtpDetails otpDetails = new OtpDetails();
        String authKey = "279572AJN05mY0S7i85ff695b3P1";
        String message = "Dear User,Your OTP Pin is " + otp + ".Enter this pin to login your account.Thanks-Patanjali";
        String url = "http://api.msg91.com/api/sendhttp.php?sender=PTNJAL&route=4&country=91&mobiles=" + mobileNumber + "&authkey=" + authKey + "&" + //
                "message=" + URLEncoder.encode(message, "UTF-8") + "&DLT_TE_ID=1307163116530482383";


//        + authKey + "&mobiles=" + mobileNumber +
//                "&message=" + URLEncoder.encode(  message , "UTF-8" ) + "&sender=ANDATA&route=4&country=91&unicode=1";


        CloseableHttpClient httpClient = HttpClientBuilder.create().build();
        try {
            HttpGet getRequest = new HttpGet(url);
            getRequest.setHeader("Accept", "application/json");
            getRequest.setHeader("Content-Type", "application/json");
            HttpResponse httpResponse = httpClient.execute(getRequest);
            int statusCode = httpResponse.getStatusLine().getStatusCode();
            System.out.println("======statusCode=======" + statusCode);


            otpDetails.setStatusCode(String.valueOf(statusCode));
            otpDetails.setMessageResponse(checkMsgResponse(statusCode));
            otpDetails.setMobileNumber(mobileNumber);
            otpDetails.setMessage(message);


            return otpDetails;
        } catch (Exception e) {
            throw e;
        }

    }

    private static String checkMsgResponse(int code) {

        switch (code) {
            case 200:
                return "success";
            case 101:
                return "Missing mobile no.";
            case 102:
                return "Missing message";
            case 106:
                return "Missing Authentication Key";
            case 202:
                return "Invalid mobile number. You must have entered either less than 10 digits or there is an alphabetic character in the mobile number field in API.";
            case 207:
                return "Invalid authentication key. Crosscheck your authentication key from your account’s API section.";
            case 208:
                return "IP is blacklisted. We are getting SMS submission requests other than your whitelisted IP list.";
            case 301:
                return "Insufficient balance to send SMS";
            case 302:
                return "Expired user account. You need to contact your account manager.";
            case 310:
                return "SMS is too long. System paused this request automatically.";
        }


        return "Need to check code";
    }
}
