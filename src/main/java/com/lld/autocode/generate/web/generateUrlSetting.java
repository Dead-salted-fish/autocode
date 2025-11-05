package com.lld.autocode.generate.web;

import org.apache.velocity.Template;
import org.apache.velocity.VelocityContext;
import org.apache.velocity.app.VelocityEngine;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;

import java.io.IOException;
import java.io.StringWriter;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.nio.file.StandardOpenOption;
import java.util.Map;
@Component
public class generateUrlSetting implements WebGenerate{
    private VelocityEngine velocityEngine;


    @Value("${autocode.generate-path}")
    private String generatePath;

    public generateUrlSetting(VelocityEngine velocityEngine) {
        this.velocityEngine = velocityEngine;
    }

    @Override
    public String getGenerateType() {
        return "UrlSetting";
    }

    @Override
    public void doGenerate(Map<String, Object> model) {
        String className = (String) model.get("className");
        // 引入模板
        Template template = velocityEngine.getTemplate("templates/UrlSetting.vm", "UTF-8");
        VelocityContext context = new VelocityContext(model);

        try (StringWriter writer = new StringWriter()) {
            // 渲染模板
            template.merge(context, writer);
            String contentStr = writer.toString();

            // 写入文件
            String targetPath = generatePath + "/"  + "UrlSetting" + ".ts";
            Path path = Paths.get(targetPath);
            Files.createDirectories(path.getParent());
            Files.write(path, contentStr.getBytes(),
                    StandardOpenOption.CREATE,
                    StandardOpenOption.WRITE,
                    StandardOpenOption.TRUNCATE_EXISTING);


        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}
