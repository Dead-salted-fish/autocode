package com.lld.autocode.security.filter;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.lld.autocode.utils.ParameterRequestWrapper;
import org.springframework.security.web.util.matcher.AntPathRequestMatcher;
import org.springframework.web.filter.OncePerRequestFilter;

import javax.servlet.FilterChain;
import javax.servlet.ServletException;
import javax.servlet.ServletRequest;
import javax.servlet.ServletResponse;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.util.HashMap;
import java.util.Map;

/**
 * @description:
 * @author: wzl
 * @date 2021/12/22 15:56
 */
public class CaptchaFilter extends OncePerRequestFilter {



    @Override
    protected void doFilterInternal(HttpServletRequest request, HttpServletResponse response, FilterChain filterChain) throws ServletException, IOException {
        if (!new AntPathRequestMatcher("/userlogin", "POST").matches(request)){
            filterChain.doFilter(request,response);
        }else {
            ParameterRequestWrapper requestWrapper = new ParameterRequestWrapper(request);
            System.out.println(requestWrapper.getParameter("captcha"));
            filterChain.doFilter(requestWrapper,response);
        }
    }

}
