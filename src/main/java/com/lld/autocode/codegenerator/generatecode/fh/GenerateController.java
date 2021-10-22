package com.lld.autocode.codegenerator.generatecode.fh;

import com.lld.autocode.codegenerator.entity.GenerateDate;

import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.OutputStream;
import java.text.SimpleDateFormat;
import java.util.Date;

/**
 * @description:
 * @author: wzl
 * @date 2021/8/18 15:43
 */
public class GenerateController {
    private String head;
    private String annotation;
    private String codeBody;

    public void generate(String upClassName, String lowerClassName, GenerateDate date) throws IOException, IllegalAccessException {
        getHead();
        getAnnotation();
        getCodeBody(upClassName, lowerClassName, date);

        String assembler = head + annotation + codeBody.replaceAll("@@@", upClassName)
                .replaceAll("###", lowerClassName);

        File file = new File(BaseInfo.basepath + upClassName + File.separator + upClassName + "Controller" + ".java");
        if (!file.getParentFile().exists()) {
            file.getParentFile().mkdirs();
        }
        //2: 实例化OutputString 对象
        OutputStream output = new FileOutputStream(file);

        //3: 准备好实现内容的输出

        //将字符串变为字节数组
        byte data[] = assembler.getBytes();
        output.write(data);
        //4: 资源操作的最后必须关闭
        output.close();

    }

    private void getHead() {
        String head = null;
        head = "import com.ifohoo.common.ifms.baseinfo.controller.BaseController;" + "\n"
                + "import com.ifohoo.common.ifms.common.base.ReturnMessage;" + "\n"
                + "import org.springframework.beans.factory.annotation.Autowired;" + "\n"
                + "import org.springframework.web.bind.annotation.GetMapping;" + "\n"
                + "import org.springframework.web.bind.annotation.PostMapping;" + "\n"
                + "import org.springframework.web.bind.annotation.RequestBody;" + "\n"
                + "import org.springframework.web.bind.annotation.RestController;" + "\n"
                + "import javax.servlet.http.HttpServletRequest;" + "\n"
                + "\n";
        this.head = head;
    }

    private void getAnnotation() {
        String annotation = "/**\n" +
                " * @description:\n" +
                " * @author: wzl\n" +
                " * @date" + getFormatDate() + "\n" +
                " */" + "\n"
                + "\n";

        this.annotation = annotation;
    }

    private String getFormatDate() {
        Date now = new Date();
        SimpleDateFormat f = new SimpleDateFormat("yyyy/MM/dd HH:mm:ss");
        String formatDate = f.format(now);
        return formatDate;
    }

    private void getCodeBody(String upClassName, String lowerClassName, GenerateDate date) throws IllegalAccessException {
        //String lowerCaseClassName = (new StringBuilder()).append(Character.toLowerCase(className.charAt(0))).append(className.substring(1)).toString();
        String swaggerAnno = "";
        String codeBody = null;
        if (date.isSwagger()) {
            this.head = "import io.swagger.annotations.Api;" + "\n" + this.head;
            swaggerAnno = "@Api(tags = \"@@@\")\n";
        }
        codeBody = swaggerAnno+
                "@RestController\n" +
                "public class @@@Controller extends BaseController {\n" +
                "    @Autowired\n" +
                "    private @@@Service ###Service;\n" +
                "    \n" +
                "    /** \n" +
                "     * 描述说明:  \n" +
                "     * 参数: @@@QueryDto \n" +
                "     * 返回值类型: com.ifohoo.common.ifms.common.base.ReturnMessage \n" +
                "     * 作者: wzl\n" +
                "     * 创建时间: " + getFormatDate() + "\n" +
                "     * 禅道任务ID: 见service\n" +
                "     */\n" +
                "    @GetMapping(\"/###/list\")\n" +
                "    public ReturnMessage find@@@List(@@@QueryDto ###QueryDto){\n" +
                "        return ###Service.findList(###QueryDto);\n" +
                "    }\n" +
                "\n" +
                "    /** \n" +
                "     * 描述说明:  \n" +
                "     * 参数: @@@Dto\n" +
                "    request \n" +
                "     * 返回值类型: com.ifohoo.common.ifms.common.base.ReturnMessage \n" +
                "     * 作者: wzl\n" +
                "     * 创建时间: " + getFormatDate() + "\n" +
                "     * 禅道任务ID: 见service\n" +
                "     */\n" +
                "    @PostMapping(\"/###/update\")\n" +
                "    public ReturnMessage update(@RequestBody @@@Dto ###Dto, HttpServletRequest request){\n" +
                "        ###Dto.setUpdateBy(findStaffIdByReq(request));\n" +
                "        return ###Service.update(###Dto);\n" +
                "    }\n" +
                "\n" +
                "    /** \n" +
                "     * 描述说明:  \n" +
                "     * 参数: @@@Dto\n" +
                "    request \n" +
                "     * 返回值类型: com.ifohoo.common.ifms.common.base.ReturnMessage \n" +
                "     * 作者: wzl\n" +
                "     * 创建时间: " + getFormatDate() + "\n" +
                "     * 禅道任务ID: 见service\n" +
                "     */\n" +
                "    @PostMapping(\"/###/delete\")\n" +
                "    public ReturnMessage delete(@RequestBody @@@Dto ###Dto, HttpServletRequest request){\n" +
                "        return ###Service.delete(###Dto);\n" +
                "    }\n" +
                "    \n" +
                "    /** \n" +
                "     * 描述说明:  \n" +
                "     * 参数: @@@Dto\n" +
                "    request \n" +
                "     * 返回值类型: com.ifohoo.common.ifms.common.base.ReturnMessage \n" +
                "     * 作者: wzl\n" +
                "     * 创建时间: " + getFormatDate() + "\n" +
                "     * 禅道任务ID: 见service\n" +
                "     */\n" +
                "    @PostMapping(\"/###/save\")\n" +
                "    public ReturnMessage save(@RequestBody @@@Dto ###Dto, HttpServletRequest request) {\n" +
                "        ###Dto.setUpdateBy(findStaffIdByReq(request));\n" +
                "        return ###Service.save(###Dto);\n" +
                "    }\n" +
                "}"
        ;


        this.codeBody = codeBody;
    }


    private String captureName(String attributeName) {
        char[] cs = attributeName.toCharArray();
        cs[0] -= 32;
        return String.valueOf(cs);
    }

}
