package com.lld.autocode.codegenerator.generatecode.fh;

import com.lld.autocode.codegenerator.entity.GenerateDate;
import com.lld.autocode.codegenerator.entity.JavaTypeDo;
import com.lld.autocode.codegenerator.entity.TableMetaData;

import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.OutputStream;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;
import java.util.Map;

/**
 * @description:
 * @author: wzl
 * @date 2021/8/18 15:43
 */
public class GenerateDomainQuery {
    private String head;
    private String annotation;
    private String codeBody;

    public  void generate(String upClassName, String lowerClassName, GenerateDate date, Map<String, JavaTypeDo> typeMap) throws IOException, IllegalAccessException {
        getHead();
        getAnnotation();
        getCodeBody(upClassName,lowerClassName,date,typeMap);

        String domainQuery = head + annotation + codeBody.replaceAll("@@@", upClassName).replaceAll("###", lowerClassName);
        File file = new File(BaseInfo.basepath + upClassName + File.separator + upClassName + "Query" + ".java");
        if (!file.getParentFile().exists()) {
            file.getParentFile().mkdirs();
        }
        //2: 实例化OutputString 对象
        OutputStream output = new FileOutputStream(file);

        //3: 准备好实现内容的输出

        //将字符串变为字节数组
        byte data[] = domainQuery.getBytes();
        output.write(data);
        //4: 资源操作的最后必须关闭
        output.close();
    }

    private  void getHead() {
        String head = null;
        head = "import cn.hutool.extra.spring.SpringUtil;" + "\n"
                + "import com.ifohoo.common.ifms.dao.query.BaseDomainQuery;" + "\n"
                + "import com.ifohoo.common.ifms.dto.BaseQueryDto;" + "\n"
                + "import com.ifohoo.common.ifms.dto.PageDto;" + "\n"
                + "import com.ifohoo.common.ifms.query.enums.QueryTypeEnum;" + "\n"
                + "import com.ifohoo.common.ifms.query.query.Page;" + "\n"
                + "import com.ifohoo.common.ifms.query.query.QueryCondition;" + "\n"
                + "import org.springframework.data.jpa.repository.JpaSpecificationExecutor;" + "\n"
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

         this.annotation =annotation;
    }



    private  void getCodeBody(String upClassName, String lowerClassName, GenerateDate date, Map<String, JavaTypeDo> typeMap) throws IllegalAccessException {

        String codeBody = null;
        codeBody = "public class @@@Query extends BaseDomainQuery<@@@Do, @@@Query> {" + "\n"
                + "\n"
                + "@@@Assembler ###Assembler = @@@Assembler.INSTANCE;" + "\n"
                + "\n"
                + "@Override" + "\n"
                + " public JpaSpecificationExecutor<@@@Do> getSpecRepository() {\n"
                + "        return SpringUtil.getBean(@@@Repository.class);\n"
                + "    }" + "\n"
                + "\n";

        List<TableMetaData> attributeDate = date.getAttributeDate();


        for (TableMetaData item:attributeDate) {
            if(item.isQueryDtoAttribute()){
                String columnName = item.getColumnName();
                String[] columnNameArr = columnName.split("_");
                StringBuffer strBuff = new StringBuffer();
                for(int i =0,j=columnNameArr.length;i<j;i++){
                    if(i>0){
                        strBuff.append(captureName(columnNameArr[i]));
                    }else {
                        strBuff.append(columnNameArr[i]);
                    }
                }
                String variableName = strBuff.toString();
                JavaTypeDo javaTypeDo = typeMap.get(item.getJavaType());
                if(javaTypeDo.getImportPackage().equals("1")){
                    this.head = "import "+javaTypeDo.getJavaTypePackage()+";" + "\n"+ this.head;
                }
                String javaType = javaTypeDo.getJavaTypeClassName();

                String queryBy = "   public  @@@Query queryBy" + captureName(variableName) + "(" + javaType + " " + variableName + "){\n" +
                        "        if(null != " + variableName + "){\n" +
                        "            addQueryCon(new QueryCondition(\"" + variableName + "\", QueryTypeEnum.EQ, " + variableName + "));\n" +
                        "        }\n" +
                        "        return  this;\n" +
                        "    }" + "\n"
                        + "\n";
                codeBody = codeBody + queryBy;
            }
        }

        String listPageDomainCodeBody = "    public PageDto<@@@Dto> listPageDomain(BaseQueryDto queryDto){\n" +
                "        Page<@@@Do> doPage = this.listPage(queryDto);\n" +
                "        PageDto<@@@Dto> " + "dtoPage = ###Assembler.toPageDto(doPage);\n" +
                "        //translateByDict(dtoPage);\n" +
                "        return dtoPage;\n" +
                "    }" + "\n"
                + "\n";

        codeBody = codeBody + listPageDomainCodeBody + "}";


        this.codeBody =codeBody;
    }


    //首字母大写
    private  String captureName(String attributeName) {
        char[] cs = attributeName.toCharArray();
        cs[0] -= 32;
        return String.valueOf(cs);

    }

    private  String getFormatDate() {
        Date now = new Date();
        SimpleDateFormat f = new SimpleDateFormat("yyyy/MM/dd HH:mm:ss");
        String formatDate = f.format(now);
        return formatDate;
    }



}
