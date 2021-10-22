package com.lld.autocode.codegenerator.generatecode.fh;

import com.lld.autocode.codegenerator.generatecode.CurrencyMethods;

import java.io.*;
import java.text.SimpleDateFormat;
import java.util.Date;

/**
 * @description:
 * @author: wzl
 * @date 2021/8/18 15:43
 */
public class GenerateRepository {
    private String head;
    private String annotation;
    private String codeBody;


    public  void generate(String upClassName, String lowerClassName) throws IOException {
        getHead();
        getAnnotation();
        getCodeBody();

        String repository = head + annotation + codeBody.replaceAll("@@@", upClassName).replaceAll("###", lowerClassName);

        File file = new File(BaseInfo.basepath + upClassName + File.separator + upClassName + "Repository" + ".java");
        if (!file.getParentFile().exists()) {
            file.getParentFile().mkdirs();
        }
        //2: 实例化OutputString 对象
        OutputStream output = new FileOutputStream(file);
        //3: 准备好实现内容的输出
        //将字符串变为字节数组
        byte data[] = repository.getBytes();
        output.write(data);
        //4: 资源操作的最后必须关闭
        output.close();
    }

    private  void getHead() {
        String head = null;
        head = "import com.ifohoo.common.ifms.dao.repository.FieldValiRepository;" + "\n" +
                "import org.springframework.data.jpa.repository.JpaSpecificationExecutor;" + "\n"
                + "\n";
        this.head =  head;
    }

    private  void getAnnotation() {
        String annotation = "/**\n" +
                " * @description:\n" +
                " * @author: wzl\n" +
                " * @date" + CurrencyMethods.getFormatDate() + "\n" +
                " */" + "\n"
                + "\n";

         this.annotation = annotation;
    }

    private  void getCodeBody() {
        String codeBody = null;
        codeBody = "public interface @@@Repository extends FieldValiRepository<@@@Do, Long>, JpaSpecificationExecutor<@@@Do> {" + "\n"
                + "}" + "\n";
         this.codeBody = codeBody;
    }



}
