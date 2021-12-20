package com.lld.autocode.security.filter;


import com.fasterxml.jackson.core.JsonParser;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.lld.autocode.security.entity.User;
import com.lld.autocode.utils.JwtTokenUtil;
import com.lld.autocode.utils.ResponseUtil;
import com.lld.autocode.utils.ReturnMessage;
import com.sun.javafx.collections.MappingChange;
import org.springframework.data.redis.core.RedisTemplate;
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
import java.util.*;
import java.util.concurrent.TimeUnit;
import java.util.stream.Collectors;

/**
 * @ClassName: TokenLoginFilter
 * @Description: 认证的 filter
 * @Author oyc
 * @Date 2020/12/29 10:09
 * @Version 1.0
 */

public class TokenLoginFilter extends UsernamePasswordAuthenticationFilter {
    private RedisTemplate redisTemplate;

    private AuthenticationManager authenticationManager;

    private JwtTokenUtil jwtTokenUtil;

    public TokenLoginFilter(AuthenticationManager authenticationManager, JwtTokenUtil jwtTokenUtil,RedisTemplate redisTemplate) {
        this.authenticationManager = authenticationManager;
        this.jwtTokenUtil = jwtTokenUtil;
        this.redisTemplate = redisTemplate;
        this.setPostOnly(false);
        this.setRequiresAuthenticationRequestMatcher(new AntPathRequestMatcher("/userlogin", "POST"));
    }

    @Override
    public Authentication attemptAuthentication(HttpServletRequest req, HttpServletResponse res) throws AuthenticationException {

        String contentType = req.getHeader("Content-Type");

        Map<String,String> userMap = new HashMap<>();

        if(contentType.contains("application/json")){
            userMap =  parseUserWithJson(req);
        }else if (contentType.contains("application/x-www-form-urlencoded")){
            userMap =  parseUserWithForm(req);
        }
            return authenticationManager.authenticate(new UsernamePasswordAuthenticationToken(userMap.get("username"), userMap.get("password"), new ArrayList<>()));
    }

    private Map<String, String> parseUserWithForm(HttpServletRequest req) {

        Map<String, String> map = new HashMap<>();
        map.put("username",req.getParameter("username")) ;
        map.put("password",req.getParameter("password")) ;
     return map;
    }

    private  Map<String,String> parseUserWithJson(HttpServletRequest req) {
        Map<String,String> map = new HashMap<>();
        try {
            map = new ObjectMapper().readValue(req.getInputStream(),Map.class);

        } catch (IOException e) {
            e.printStackTrace();
        }
        return map;
    }

    /**
     * 登录成功
     */
    @Override
    protected void successfulAuthentication(HttpServletRequest request, HttpServletResponse response, FilterChain chain, Authentication auth) throws IOException, ServletException {
        //User user = (User)auth.getPrincipal();
        User user  = (User) auth.getPrincipal();
        String token = jwtTokenUtil.createToken(user.getUsername(),user.getId());
        HashMap<Object, Object> map = new HashMap<>();
        map.put("token",token);

        map.put("roles",user.getAuthorities().stream().map(item->{return item.getAuthority();}).collect(Collectors.toList()));
        map.put("loginName",user.getUsername());

        redisTemplate.opsForValue().set(user.getId(),token,60*30, TimeUnit.SECONDS);
        redisTemplate.opsForValue().set(user.getUsername(),user,60*30, TimeUnit.SECONDS);
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