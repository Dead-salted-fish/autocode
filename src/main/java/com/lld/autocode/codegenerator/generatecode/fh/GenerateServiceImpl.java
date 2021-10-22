package com.lld.autocode.codegenerator.generatecode.fh;

import com.lld.autocode.codegenerator.entity.GenerateDate;
import com.lld.autocode.codegenerator.entity.TableMetaData;
import com.lld.autocode.codegenerator.generatecode.CurrencyMethods;

import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.OutputStream;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;

/**
 * @description:
 * @author: wzl
 * @date 2021/8/18 15:43
 */
public class GenerateServiceImpl {
    private String head;
    private String annotation;
    private String codeBody;

    public void generate(String upClassName, String lowerClassName, GenerateDate date) throws IOException, IllegalAccessException {
        getHead();
        getAnnotation();
        getCodeBody(upClassName, lowerClassName, date);

        String servcieImpl = head + annotation + codeBody.replaceAll("@@@", upClassName)
                .replaceAll("###", lowerClassName);

        File file = new File(BaseInfo.basepath + upClassName + File.separator + upClassName + "ServiceImpl" + ".java");
        if (!file.getParentFile().exists()) {
            file.getParentFile().mkdirs();
        }
        //2: 实例化OutputString 对象
        OutputStream output = new FileOutputStream(file);

        //3: 准备好实现内容的输出

        //将字符串变为字节数组
        byte data[] = servcieImpl.getBytes();
        output.write(data);
        //4: 资源操作的最后必须关闭
        output.close();

    }

    private void getHead() {
        String head = null;
        head = "import com.ifohoo.common.ifms.common.base.ReturnMessage;" + "\n"
                + "import com.ifohoo.common.ifms.common.enums.ErrorCodeEnum;" + "\n"
                + "import com.ifohoo.common.ifms.dto.PageDto;" + "\n"
                + "import org.springframework.stereotype.Service;" + "\n"
                + "\n";
        this.head = head;
    }

    private void getAnnotation() {
        String annotation = "/**\n" +
                " * @description:\n" +
                " * @author: wzl\n" +
                " * @date" + CurrencyMethods.getFormatDate() + "\n" +
                " */" + "\n"
                + "\n";

        this.annotation = annotation;
    }



    private void getCodeBody(String upClassName, String lowerClassName, GenerateDate date) throws IllegalAccessException {
        //String lowerCaseClassName = (new StringBuilder()).append(Character.toLowerCase(className.charAt(0))).append(className.substring(1)).toString();

        String codeBody = null;
        codeBody = "@Service(\"###Service\")\n" +
                "public class @@@ServiceImpl implements @@@Service {\n" +
                "\n" +
                "    @@@Assembler ###Assembler = @@@Assembler.INSTANCE;\n" +
                "\n" +
                "    @Override\n" +
                "    public ReturnMessage findList(@@@QueryDto QueryDto) {\n" +
                "        PageDto<@@@Dto> dtoPage = new @@@Query()";
        String queryBody = "";

        List<TableMetaData> attributeDates = date.getAttributeDate();
        for (TableMetaData item : attributeDates) {
            if (item.isQueryDtoAttribute()) {
                String attributeNameWithFirstUp = CurrencyMethods.firstLetterUppercaseAfterSplit(item.getColumnName(),"_");
                queryBody = queryBody + ".queryBy" + attributeNameWithFirstUp + "(QueryDto.get" + attributeNameWithFirstUp + "())" + "\n";
            }
        }
        queryBody = queryBody + ".listPageDomain(QueryDto);" + "\n"
                + "\n"
                + " ReturnMessage returnMsg = new ReturnMessage();\n" +
                "        returnMsg.setReturnData(dtoPage);\n" +
                "        returnMsg.modifyMsg(ErrorCodeEnum.NORMAL);\n" +
                "        return returnMsg;\n" +
                "    }" + "\n"
                + "\n";

        codeBody = codeBody + queryBody
                + " @Override\n" +
                "    public ReturnMessage save(@@@Dto ###Dto) {\n" +
                "        @@@ ### = ###Assembler.dtoToDomain(###Dto);\n" +
                "        ###.save();\n" +
                "\n" +
                "        ReturnMessage returnMsg = new ReturnMessage();\n" +
                "        returnMsg.modifyMsg(ErrorCodeEnum.NORMAL);\n" +
                "        return returnMsg;\n" +
                "    }\n" +
                "\n" +
                "    @Override\n" +
                "    public ReturnMessage update(@@@Dto ###Dto) {\n" +
                "        @@@ ### = ###Assembler.dtoToDomain(###Dto);\n" +
                "        ###.update();\n" +
                "\n" +
                "        ReturnMessage returnMsg = new ReturnMessage();\n" +
                "        returnMsg.modifyMsg(ErrorCodeEnum.NORMAL);\n" +
                "        return returnMsg;\n" +
                "    }\n" +
                "\n" +
                "    @Override\n" +
                "    public ReturnMessage delete(@@@Dto ###Dto) {\n" +
                "        @@@ ### = ###Assembler.dtoToDomain(###Dto);\n" +
                "        ###.delete();\n" +
                "\n" +
                "        ReturnMessage returnMsg = new ReturnMessage();\n" +
                "        returnMsg.modifyMsg(ErrorCodeEnum.NORMAL);\n" +
                "        return returnMsg;\n" +
                "    }\n" +
                "}";

        this.codeBody = codeBody;
    }




}
