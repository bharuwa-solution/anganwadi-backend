package com.anganwadi.anganwadi.config;

import org.springframework.web.servlet.support.ServletUriComponentsBuilder;


public class ApplicationConstants {

    public static final String SIGNING_KEY = "family_key";
    public static final String ChildId = "AGCI_";
    public static final String familyId = "AGFI_";
    public static final String TOKEN_PREFIX = "Bearer ";
    public static final long ACCESS_TOKEN_VALIDITY_SECONDS = 5 * 60 * 60;
    public static final String HEADER_STRING = "Authorization";
    public static final String SECRET_KEY = "404E635266556A586E3272357538782F413F4428472B4B6250645367566B5970";
    public static final String AUTHORITIES_KEY = "username";
    public static final String ROLE_USER = "USER";
    public static final String fileSeparator = "/";
    public static final long ONE_MINUTE_IN_MILLIS = 60000;
    public static final String childrenPicType = "children";
    public static final String householdsPicType = "households";
    public static final String memberPicType = "members";
    public static final String USER_ROLE = "USER";
    public static final String USER_ADMIN = "ADMIN";
    public static final String baseUrl = ServletUriComponentsBuilder.fromCurrentContextPath().build().toUriString().replaceAll("anganwadi-0.0.1-SNAPSHOT", "");
    public static final String baseUrlTesting = ServletUriComponentsBuilder.fromCurrentContextPath().build().toUriString().replaceAll("anganwadi-0.0.1-TESTING", "");
    public static final String serverUploadPath = "/data/apache-tomcat-9.0.38/webapps";
    public static final String localUploadPath = "C:/Prabeer/project/Anganwadi/code";
}
