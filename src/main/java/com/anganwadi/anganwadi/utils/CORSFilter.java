package com.anganwadi.anganwadi.utils;

import com.anganwadi.anganwadi.service_impl.impl.CommonMethodsService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import javax.servlet.*;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

@Slf4j
@Component
public class CORSFilter implements Filter {

    @Autowired
    private CommonMethodsService commonMethodsService;

    @Override
    public void init(FilterConfig filterConfig) throws ServletException {

    }

    @Override
    public void doFilter(ServletRequest servletRequest, ServletResponse servletResponse, FilterChain filterChain) throws IOException, ServletException {
        HttpServletResponse httpServletResponse = (HttpServletResponse) servletResponse;
        httpServletResponse.addHeader("Access-Control-Allow-Origin", "*");
        httpServletResponse.addHeader("Access-Control-Allow-Methods", "POST, PUT, GET, DELETE, PATCH, OPTIONS");
        httpServletResponse.addHeader("Access-Control-Max-Age", "3600");
        httpServletResponse.addHeader("Access-Control-Allow-Credentials", "true");
        httpServletResponse.setHeader("Access-Control-Allow-Headers", "Origin, Content-Type, Accept");

        filterChain.doFilter(servletRequest, servletResponse);

    }

    public void validateCenterId(HttpServletResponse httpServletResponse){

        commonMethodsService.findCenterName(httpServletResponse.getHeader("centerId"));

    }


    @Override
    public void destroy() {

    }
}
