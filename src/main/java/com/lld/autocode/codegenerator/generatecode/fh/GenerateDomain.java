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
public class GenerateDomain {
    private String head;
    private String annotation;
    private String codeBody;

    public  void generate(String upClassName, String lowerClassName) throws IOException, IllegalAccessException {
        getHead();
        getAnnotation();
        getCodeBody(upClassName,lowerClassName);

        String domain = head + annotation + codeBody.replaceAll("@@@", upClassName).replaceAll("###", lowerClassName);
        File file = new File(BaseInfo.basepath  + upClassName + File.separator + upClassName + ".java");
        if (!file.getParentFile().exists()) {
            file.getParentFile().mkdirs();
        }
        //2: 实例化OutputString 对象
        OutputStream output = new FileOutputStream(file);

        //3: 准备好实现内容的输出

        //将字符串变为字节数组
        byte data[] = domain.getBytes();
        output.write(data);
        //4: 资源操作的最后必须关闭
        output.close();

    }

    private  void getHead() {
        String head = null;
        head = "import cn.hutool.extra.spring.SpringUtil;" + "\n"
                + "import com.baidu.fsg.uid.UidGenerator;" + "\n"
                + "import com.ifohoo.common.ifms.common.port.IBaseDomain;" + "\n"
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

    private  String getFormatDate() {
        Date now = new Date();
        SimpleDateFormat f = new SimpleDateFormat("yyyy/MM/dd HH:mm:ss");
        String formatDate = f.format(now);
        return formatDate;
    }

    private  void getCodeBody(String upClassName, String lowerClassName) throws IllegalAccessException {
        //String lowerCaseClassName = (new StringBuilder()).append(Character.toLowerCase(className.charAt(0))).append(className.substring(1)).toString();

        String codeBody = null;
        codeBody = "public class @@@ extends @@@Do implements IBaseDomain<@@@, Long> {" + "\n"
                + "\n"
                + "@@@Assembler ###Assembler = @@@Assembler.INSTANCE;" + "\n"
                + "\n"
                + " private @@@Repository ###Repository;" + "\n"
                + "\n"
                //==================
                + "   @Override\n" +
                "    public Long findId() {\n" +
                "        return null;\n" +
                "    }" + "\n"
                + "\n"
                //==================
                + "    @Override\n" +
                "    public void save() {\n" +
                "        @@@Do ###Do = ###Assembler.domainToDo(this);\n" +
                "\n" +
                "        UidGenerator uidGenerator = SpringUtil.getBean(UidGenerator.class);\n" +
                "        Long uid = uidGenerator.getUID();\n" +
                "        ###Do.setId(uid);\n" +
                "        ###Do.addUserAndTime();\n" +
                "\n" +
                "        findRepository().valiSave(###Do);\n" +
                "    }" + "\n"
                + "\n"
                //==================
                + "    @Override\n" +
                "    public void update() {\n" +
                "        @@@Do ###Do = ###Assembler.domainToDo(this);\n" +
                "\n" +
                "        @@@Do oldItem = findRepository().getOne(getId());\n" +
                "        ###Do.findItemInfoByOldItem(oldItem);\n" +
                "        ###Do.updateUserAndTime();\n" +
                "\n" +
                "        findRepository().valiSave(###Do);\n" +
                "    }" + "\n"
                + "\n"
                //==================
                + "   @Override\n" +
                "    public void delete() {\n" +
                "        findRepository().valiDel(getId());\n" +
                "    }" + "\n"
                + "\n"
                //====================
                + "    @Override\n" +
                "    public @@@ get(Long aLong) {\n" +
                "        return null;\n" +
                "    }" + "\n"
                + "\n"
                //=====================
                + "   @Override\n" +
                "    public @@@Repository findRepository() {\n" +
                "        if (###Repository == null) {\n" +
                "            this.###Repository = SpringUtil.getBean(@@@Repository.class);\n" +
                "        }\n" +
                "        return ###repository;\n" +
                "    }" + "\n"
                //======
                + "}"
        ;


        this.codeBody = codeBody;
    }


    private   String captureName(String attributeName) {
        char[] cs = attributeName.toCharArray();
        cs[0] -= 32;
        return String.valueOf(cs);
    }

}
