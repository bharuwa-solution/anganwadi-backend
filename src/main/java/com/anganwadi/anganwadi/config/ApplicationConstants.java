package com.anganwadi.anganwadi.config;
import org.springframework.web.servlet.support.ServletUriComponentsBuilder;

public interface ApplicationConstants {

    String SIGNING_KEY = "family_key";
    long ACCESS_TOKEN_VALIDITY_SECONDS = 5 * 60 * 60;
    String SECRET_KEY = "404E635266556A586E3272357538782F413F4428472B4B6250645367566B5970";
    String AUTHORITIES_KEY = "username";
    String ROLE_USER = "USER";
    String fileSeparator = "/";
    long ONE_MINUTE_IN_MILLIS = 60000;
    String childrenPicType = "children";
    String householdsPicType = "households";
    String memberPicType = "members";
    String USER_ROLE = "USER";
    String USER_ADMIN = "ADMIN";
    String baseUrl = ServletUriComponentsBuilder.fromCurrentContextPath().build().toUriString().replaceAll("vishwakarna-0.0.1-SNAPSHOT", "");
    String serverUploadPath = "/opt/apache-tomcat-9.0.50/webapps";
    String localUploadPath = "C:/Prabeer/project/Anganwadi/code";
}
