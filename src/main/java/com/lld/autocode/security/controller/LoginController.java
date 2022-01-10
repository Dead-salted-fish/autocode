package com.lld.autocode.security.controller;

import com.google.code.kaptcha.Producer;
import com.lld.autocode.security.entity.SysCaptcha;
import com.lld.autocode.utils.ReturnMessage;
import com.xiaoju.uemc.tinyid.client.utils.TinyId;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.stereotype.Controller;
import org.springframework.util.AntPathMatcher;
import org.springframework.util.FastByteArrayOutputStream;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import javax.imageio.ImageIO;
import javax.servlet.ServletOutputStream;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.awt.image.BufferedImage;
import java.io.IOException;
import java.util.Base64;
import java.util.concurrent.TimeUnit;


/**
 * @description:
 * @author: wzl
 * @date 2021/8/30 15:14
 */
@Controller
public class LoginController {
    @Autowired
    private Producer producer;

    @Autowired
    private RedisTemplate redisTemplate;


    @GetMapping("/")
    public String autocode2(){
        return "App";
    }

    @GetMapping("/login")
    public String autocode(){
        //return "login/login";
        return "redirect:/";
    }


    @GetMapping("/captcha")
    @ResponseBody
    public ReturnMessage captcha(HttpServletRequest request, HttpServletResponse response) throws IOException {

        response.setContentType("image/jpeg");
        //验证码文本
        String text = producer.createText();
        BufferedImage image = producer.createImage(text);
        FastByteArrayOutputStream os = new FastByteArrayOutputStream();
        ImageIO.write(image,"jpg",os);
        String imgBase64 = Base64.getEncoder().encodeToString(os.toByteArray());

        Long tinyid = TinyId.nextId("experiment");
        redisTemplate.opsForValue().set("captcha"+tinyid.toString(),text,60*5, TimeUnit.SECONDS);
        return ReturnMessage.ok(new SysCaptcha(imgBase64,tinyid.toString()));
    }


}
