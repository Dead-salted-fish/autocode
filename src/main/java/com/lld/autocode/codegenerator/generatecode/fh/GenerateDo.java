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
public class GenerateDo {
    private String head;
    private String annotation;
    private String codeBody;

    public  void generate(String upClassName, String lowerClassName, GenerateDate date, Map<String, JavaTypeDo> typeMap) throws IOException, IllegalAccessException {
        getHead();
        getAnnotation();
        getCodeBody(upClassName,lowerClassName,date,typeMap);
        String assembler = head + annotation + codeBody.replaceAll("@@@", upClassName)
                .replaceAll("###", lowerClassName);

        File file = new File(BaseInfo.basepath + upClassName + File.separator + upClassName + "Do" + ".java");
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

    private  void getHead() {
        String head = null;
        head = "import com.ifohoo.common.ifms.dao.dbo.BaseDo;\n" +
                "\n" +
                "import javax.persistence.Column;\n" +
                "import javax.persistence.Entity;\n" +
                "import javax.persistence.Id;\n" +
                "import javax.persistence.Table;"+
                "\n";
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

    private  void getCodeBody(String upClassName, String lowerClassName, GenerateDate date, Map<String, JavaTypeDo> typeMap) throws IllegalAccessException {
        //String lowerCaseClassName = (new StringBuilder()).append(Character.toLowerCase(className.charAt(0))).append(className.substring(1)).toString();

        String codeBody = null;
         String getAndSet= "";
        String tableName = date.getTableName();
        String[] tableNameArr = tableName.split("_");
        StringBuffer tableNameUp = new StringBuffer();
        for(int i =0,j=tableNameArr.length;i<j;i++){
            tableNameUp.append(captureName(tableNameArr[i]));
        }

        codeBody = "@Entity\n" +
                "@Table(name = \""+date.getTableName()+"\")\n" +
                "public class "+tableNameUp.toString()+"Do extends BaseDo {\n";
        List<TableMetaData> attributeDate = date.getAttributeDate();
        for(TableMetaData item:attributeDate){
           String  normalAnnotation = "";
           String  japAnnotation = " @Column(name = \""+item.getColumnName()+"\" ";
            String attribute = " private ";
           if(date.isNormalAnnotation()&&(item.getRemark()!=null&&!item.getRemark().equals(""))){
               normalAnnotation = " /**\n" +
                       "* "+item.getRemark()+"\n" +
                       "*/\n";
           }
           if(item.isCanNullable()){
               japAnnotation = japAnnotation + ")\n";
           }else {
               japAnnotation = japAnnotation +", nullable = false)\n";
           }

           if(item.getColumnKey()!=null&&item.getColumnKey().equals("PRI")){
               japAnnotation =" @Id\n"+japAnnotation;
           }

            String columnName = item.getColumnName();
            String[] columnNameArr = columnName.split("_");
            StringBuffer strBuffFirstLower = new StringBuffer();
            StringBuffer strBuffFirstUp = new StringBuffer();
            for(int i =0,j=columnNameArr.length;i<j;i++){
                if(i>0){
                    strBuffFirstLower.append(captureName(columnNameArr[i]));
                }else {
                    strBuffFirstLower.append(columnNameArr[i]);
                }
            }

            for(int i =0,j=columnNameArr.length;i<j;i++){
                strBuffFirstUp.append(captureName(columnNameArr[i]));
            }


            JavaTypeDo javaTypeDo = typeMap.get(item.getJavaType());
            if(javaTypeDo.getImportPackage().equals("1")){
                this.head = "import "+javaTypeDo.getJavaTypePackage()+";" + "\n"+ this.head;
            }
            String javaType = javaTypeDo.getJavaTypeClassName();
            attribute = attribute + javaType +" "+ strBuffFirstLower.toString()+";\n\n";
            codeBody = codeBody + normalAnnotation +  japAnnotation + attribute;

            getAndSet = getAndSet + " public "+javaType+" "+(javaType.equals("Boolean")||javaType.equals("boolean")?"is":"get")+""+strBuffFirstUp.toString()+"() {\n" +
                    "        return "+strBuffFirstLower.toString()+";\n" +
                    "    }\n"
                     +"public void set"+strBuffFirstUp.toString()+"("+javaType+" "+strBuffFirstLower.toString()+") {\n" +
                    "        this."+strBuffFirstLower.toString()+" = "+strBuffFirstLower.toString()+";\n" +
                    "    }\n";
        }

        this.codeBody = codeBody +getAndSet+ "}\n";
    }


    public  String captureName(String attributeName) {
        char[] cs = attributeName.toCharArray();
        cs[0] -= 32;
        return String.valueOf(cs);
    }

}
