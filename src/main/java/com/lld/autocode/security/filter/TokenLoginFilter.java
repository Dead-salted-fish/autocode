package com.lld.autocode.security.filter;


import com.fasterxml.jackson.databind.ObjectMapper;
import com.lld.autocode.security.entity.User;
import com.lld.autocode.utils.JwtTokenUtil;
import com.lld.autocode.utils.ResponseUtil;
import com.lld.autocode.utils.ReturnMessage;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.AuthenticationException;

import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;
import org.springframework.security.web.util.matcher.AntPathRequestMatcher;
import org.springframework.stereotype.Component;

import javax.servlet.FilterChain;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;

/**
 * @ClassName: TokenLoginFilter
 * @Description: 认证的 filter
 * @Author oyc
 * @Date 2020/12/29 10:09
 * @Version 1.0
 */

public class TokenLoginFilter extends UsernamePasswordAuthenticationFilter {

    private AuthenticationManager authenticationManager;

    private JwtTokenUtil jwtTokenUtil;

    public TokenLoginFilter(AuthenticationManager authenticationManager, JwtTokenUtil jwtTokenUtil) {
        this.authenticationManager = authenticationManager;
        this.jwtTokenUtil = jwtTokenUtil;
        this.setPostOnly(false);
        this.setRequiresAuthenticationRequestMatcher(new AntPathRequestMatcher("/login", "POST"));
    }

    @Override
    public Authentication attemptAuthentication(HttpServletRequest req, HttpServletResponse res) throws AuthenticationException {

            Map<String, String[]> map = req.getParameterMap();
            String username = map.get("username")[0];
            String password = map.get("password")[0];
            return authenticationManager.authenticate(new UsernamePasswordAuthenticationToken(username, password, new ArrayList<>()));

    }

    /**
     * 登录成功
     */
    @Override
    protected void successfulAuthentication(HttpServletRequest request, HttpServletResponse response, FilterChain chain, Authentication auth) throws IOException, ServletException {
        //User user = (User)auth.getPrincipal();
        User user  = (User) auth.getPrincipal();
        String token = jwtTokenUtil.createToken(user.getUsername());
        HashMap<Object, Object> map = new HashMap<>();
        map.put("token",token);
        map.put("user",user);
        map.put("loginName",user.getUsername());
        ResponseUtil.out(response, ReturnMessage.ok(map));
    }

    /**
     * 登录失败
     */
    @Override
    protected void unsuccessfulAuthentication(HttpServletRequest request, HttpServletResponse response, AuthenticationException e) throws IOException, ServletException {
        ResponseUtil.out(response, ReturnMessage.error("登录失败"));
    }

}