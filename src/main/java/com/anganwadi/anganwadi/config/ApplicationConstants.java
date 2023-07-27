package com.anganwadi.anganwadi.config;

import org.springframework.web.servlet.support.ServletUriComponentsBuilder;

import java.text.DateFormat;
import java.text.SimpleDateFormat;


public class ApplicationConstants {

    public static final String SIGNING_KEY = "family_key";
    public static final DateFormat df = new SimpleDateFormat("dd-MM-yyyy");
    public static final String default_Otp = "1105";
    public static final String birth_visit_category = "2";
    public static final Long MealsFixedQuantity = 1L;
    public static final Long MealsFixedCalorie = 500L;
    public static final Long MealsFixedProtein = 13L;
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
    public static final String[] ignoreCenters = {"642fdf6152de1975898f8fbb","649a671ea33ba56f3df3f828","6426cfcfa4479557b4c4fb6c","6426d3c5a4479557b4c4fb70"};
    public static final String vaccineCodePrefix="V-";
}
