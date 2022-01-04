package com.lld.autocode.security.filter;


import com.fasterxml.jackson.core.JsonParser;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.lld.autocode.security.entity.User;
import com.lld.autocode.utils.BaseConstant;
import com.lld.autocode.utils.JwtTokenUtil;
import com.lld.autocode.utils.ResponseUtil;
import com.lld.autocode.utils.ReturnMessage;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.web.authentication.www.BasicAuthenticationFilter;
import org.springframework.util.StringUtils;

import javax.servlet.FilterChain;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Collection;
import java.util.concurrent.TimeUnit;

/**
 * @ClassName: TokenAuthenticationFilter
 * @Description: TokenAuthenticationFilter
 * @Author oyc
 * @Date 2021/1/18 10:59
 * @Version 1.0
 */
public class TokenAuthenticationFilter extends BasicAuthenticationFilter {
    private RedisTemplate redisTemplate;


    public TokenAuthenticationFilter(AuthenticationManager authManager, RedisTemplate redisTemplate) {
        super(authManager);
        this.redisTemplate = redisTemplate;
    }

    @Override
    protected void doFilterInternal(HttpServletRequest request, HttpServletResponse response, FilterChain chain) throws IOException, ServletException {
        System.out.println("URL======"+request.getRequestURI());
        UsernamePasswordAuthenticationToken authentication = null;
        try {
            authentication = getAuthentication(request);
        } catch (Exception e) {
            String url =  request.getRequestURI();
            if(url.startsWith("/main")){
                chain.doFilter(request, response);
            }else {
                e.printStackTrace();
                ResponseUtil.out(response, ReturnMessage.error(e.getMessage()));
            }
        }
        if (authentication != null) {
            SecurityContextHolder.getContext().setAuthentication(authentication);
            chain.doFilter(request, response);
        }

    }

    private UsernamePasswordAuthenticationToken getAuthentication(HttpServletRequest request) throws JsonProcessingException {
        // 获取Token字符串，token 置于 header 里
        String token = request.getHeader("token");
        if (!StringUtils.hasText(token)) {
            token = request.getParameter("token");
        }
        if (token != null && !"".equals(token.trim())) {
            // 从Token中解密获取用户名
//            System.out.println(token);
            Long userId = JwtTokenUtil.getUserRoIdFromToken(token);
            String tokenFromRedis = (String)redisTemplate.opsForValue().get(BaseConstant.REDIS_TOKEN_PREFIX+userId);
            if(tokenFromRedis ==null){
                throw new RuntimeException("token已过期");
            }else if(!tokenFromRedis.equals(token)){
                throw new RuntimeException("无效token");
            }
            Object obj = redisTemplate.opsForValue().get(BaseConstant.REDIS_USERENTITY_PREFIX + userId);
            String userEntityStr = new ObjectMapper().writeValueAsString(obj);

            User userEntity = new ObjectMapper().readValue(userEntityStr,User.class);

            if (userEntity != null) {
                //获取用户角色
                Collection<? extends GrantedAuthority> authorities = userEntity.getAuthorities();
                //重置redis 缓存时间
                redisTemplate.expire(BaseConstant.REDIS_TOKEN_PREFIX+userId,60*30,TimeUnit.SECONDS);
                redisTemplate.expire(BaseConstant.REDIS_USERENTITY_PREFIX+userId,60*30,TimeUnit.SECONDS);
                return new UsernamePasswordAuthenticationToken(userEntity, token, authorities);
            }
             throw new RuntimeException("token已过期");
        }
        throw new RuntimeException("token为空");
    }
}

