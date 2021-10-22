package com.lld.autocode.codegenerator.generatecode.fh;

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
public class GenerateService {

    private String head;
    private String annotation;
    private String codeBody;


    public  void generate(String upClassName, String lowerClassName) throws IOException, IllegalAccessException {
        getHead();
        getAnnotation();
        getCodeBody(upClassName,lowerClassName);

        String assembler = head + annotation + codeBody.replaceAll("@@@", upClassName)
                .replaceAll("###", lowerClassName);

        File file = new File(BaseInfo.basepath + upClassName + File.separator + upClassName + "Service" + ".java");
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

    private  void  getHead() {
        String head = null;
        head = "import com.ifohoo.common.ifms.common.base.ReturnMessage;" + "\n"
                + "import org.mapstruct.Mapper;" + "\n"
                + "import org.mapstruct.factory.Mappers;" + "\n"
                + "\n";
        this.head = head;
    }

    private  void getAnnotation() {
        String annotation = "/**\n" +
                " * @description:\n" +
                " * @author: wzl\n" +
                " * @date" + getFormatDate() + "\n" +
                " */" + "\n"
                + "\n";

        this.annotation = annotation;
    }

    public  String getFormatDate() {
        Date now = new Date();
        SimpleDateFormat f = new SimpleDateFormat("yyyy/MM/dd HH:mm:ss");
        String formatDate = f.format(now);
        return formatDate;
    }

    private  void getCodeBody(String upClassName, String lowerClassName) throws IllegalAccessException {
        //String lowerCaseClassName = (new StringBuilder()).append(Character.toLowerCase(className.charAt(0))).append(className.substring(1)).toString();

        String codeBody = null;
        codeBody = "public interface @@@Service {\n" +
                "\n" +
                "    /** \n" +
                "     * 描述说明:\n" +
                "     * 参数: @@@QueryDto \n" +
                "     * 返回值类型: com.ifohoo.common.ifms.common.base.ReturnMessage \n" +
                "     * 作者: wzl\n" +
                "     * 创建时间: " + getFormatDate() + "\n" +
                "     * 禅道任务ID: 674\n" +
                "     */\n" +
                "    ReturnMessage findList(@@@QueryDto ###QueryDto);\n" +
                "\n" +
                "    /**\n" +
                "     * 描述说明:\n" +
                "     * 参数: @@@Dto\n" +
                "     * 返回值类型: com.ifohoo.common.ifms.common.base.ReturnMessage\n" +
                "     * 作者: wzl\n" +
                "     * 创建时间: " + getFormatDate() + "\n" +
                "     * 禅道任务ID: 674\n" +
                "     */\n" +
                "    ReturnMessage save(@@@Dto ###Dto);\n" +
                "\n" +
                "    /**\n" +
                "     * 描述说明:\n" +
                "     * 参数: @@@Dto\n" +
                "     * 返回值类型: com.ifohoo.common.ifms.common.base.ReturnMessage\n" +
                "     * 作者: wzl\n" +
                "     * 创建时间: " + getFormatDate() + "\n" +
                "     * 禅道任务ID: 674\n" +
                "     */\n" +
                "    ReturnMessage update(@@@Dto ###Dto);\n" +
                "\n" +
                "    /**\n" +
                "     * 描述说明:\n" +
                "     * 参数: @@@Dto\n" +
                "     * 返回值类型: com.ifohoo.common.ifms.common.base.ReturnMessage\n" +
                "     * 作者: wzl\n" +
                "     * 创建时间: " + getFormatDate() + "\n" +
                "     * 禅道任务ID: 674\n" +
                "     */\n" +
                "    ReturnMessage delete(@@@Dto ###Dto);\n" +
                "}"
        ;
        this.codeBody =codeBody;
    }


    public  String captureName(String attributeName) {
        char[] cs = attributeName.toCharArray();
        cs[0] -= 32;
        return String.valueOf(cs);
    }

}
