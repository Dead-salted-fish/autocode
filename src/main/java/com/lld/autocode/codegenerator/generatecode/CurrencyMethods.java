package com.lld.autocode.codegenerator.generatecode;

import java.text.SimpleDateFormat;
import java.util.Date;

/**
 * @description:
 * @author: wzl
 * @date 2021/10/22 16:58
 */
public class CurrencyMethods {
    public static String firstLetterLowercaseAfterSplit(String str,String splitCharacter){
        String[] arrAfterSplit = str.split(splitCharacter);
        StringBuffer strBuffer = new StringBuffer();

        for(int i =0,j=arrAfterSplit.length;i<j;i++){
            if(i>0){
                strBuffer.append(firstLetterUppercase(arrAfterSplit[i]));
            }else {
                strBuffer.append(arrAfterSplit[i]);
            }
        }
        return strBuffer.toString();
    }

    public static String firstLetterUppercaseAfterSplit(String str,String splitCharacter){
        String[] arrAfterSplit = str.split(splitCharacter);
        StringBuffer strBuffer = new StringBuffer();

        for(int i =0,j=arrAfterSplit.length;i<j;i++){
            strBuffer.append(firstLetterUppercase(arrAfterSplit[i]));
        }
        return strBuffer.toString();
    }

    //首字母大写
    public static String firstLetterUppercase(String str) {
        char[] cs = str.toCharArray();
        cs[0] -= 32;
        return String.valueOf(cs);
    }

    public static  String getFormatDate() {
        Date now = new Date();
        SimpleDateFormat f = new SimpleDateFormat("yyyy/MM/dd HH:mm:ss");
        String formatDate = f.format(now);
        return formatDate;
    }
}
