package com.prism.common.util;

import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class MatchUtil
{
    /**
     * 返回匹配到字符串
     * @param str
     * @param regex
     * @return
     */
    public static String match(String str, String regex)
    {
        String result = null;
        Pattern p = Pattern.compile(regex);
        Matcher m = p.matcher(str);

        while (m.find())
        {
            result = m.group(0);
        }
        return result;
    }

    /**
     * 返回匹配到的第二组字符串
     * @param str
     * @param regex
     * @return
     */
    public static String matchColumn(String str, String regex)
    {
        String result = null;
        Pattern p = Pattern.compile(regex);
        Matcher m = p.matcher(str);

        while (m.find())
        {
            if(m.groupCount() > 0)
            {
                result = m.group(1);
            }
        }
        return result;
    }

    /**
     * 返回匹配到的第N组字符串
     * @param str
     * @param regex
     * @param index
     * @return
     */
    public static String matchN(String str, String regex, Integer index)
    {
        String result = null;
        Pattern p = Pattern.compile(regex);
        Matcher m = p.matcher(str); // 获取 matcher 对象

        while (m.find())
        {
            result = m.group(index);
        }
        return result;
    }
}
