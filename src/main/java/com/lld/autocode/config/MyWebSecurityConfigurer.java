package com.lld.autocode.config;


import com.lld.autocode.security.filter.TokenAuthenticationFilter;
import com.lld.autocode.security.filter.TokenLoginFilter;
import com.lld.autocode.utils.JwtTokenUtil;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.builders.WebSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;
import org.springframework.security.config.http.SessionCreationPolicy;
import org.springframework.security.web.authentication.logout.LogoutFilter;

import javax.annotation.Resource;

/**
 * @description:
 * @author: wzl
 * @date 2021/10/26 14:24
 */
@EnableWebSecurity
public class MyWebSecurityConfigurer extends WebSecurityConfigurerAdapter {

    @Resource
    private JwtTokenUtil jwtTokenUtil;

    @Override
    protected void configure(HttpSecurity http) throws Exception {
        http
                .authorizeRequests()
//                   .antMatchers("/**").hasRole("admin")
                .antMatchers("/login").permitAll()
                   .anyRequest().authenticated()

                .and()
                .formLogin()
                .loginPage("/login")
                .and()
                .addFilterAfter(new TokenLoginFilter(authenticationManager(),jwtTokenUtil), LogoutFilter.class)
                .addFilterAfter(new TokenAuthenticationFilter(authenticationManager()), LogoutFilter.class)
                .httpBasic()
                .and()
                .csrf()
                    .disable()
                .sessionManagement()
                     .sessionCreationPolicy(SessionCreationPolicy.STATELESS)  //禁用session
                    ;

    }

    @Override
    protected void configure(AuthenticationManagerBuilder auth) throws Exception {
        super.configure(auth);
    }

    @Override
    public void configure(WebSecurity web) throws Exception {
       web.ignoring().antMatchers("/css/**","/js/**","/template/**","/favicon.ico","/testhttp","/autoCodeMain");
    }
}
