package com.wei.wanandroid.javasources.algorithm;

/**
 * 面试时遇到的算法
 * @author: WEI
 * @date: 2018/3/19
 */

public class InterviewAlg extends BaseALG
{
    private static String sString = "I love this world! ";

    public static void main(String[] args) {
        InterviewAlg interviewAlg = new InterviewAlg();
        System.out.println("去空格前 ：" + sString);
        interviewAlg.deleteSpace(sString);
        interviewAlg.deleteSpaceWithApi(sString);
    }

    /**
     *  去掉字符串中的空格
      */
    private void deleteSpace(String str)
    {
        char[] chars = str.toCharArray();
        int len = chars.length;
        String resultStr = "";
        for (int i = 0; i < len; i ++)
        {
            char c = chars[i];
            String s = String.valueOf(c);
            if ( !" ".equals(s) )
            {
                resultStr += s;
            }
        }
        System.out.println("去空格后 ：" + resultStr);
    }

    /**
     * 用java的api去掉字符串空格
     * @param str
     */
    private void deleteSpaceWithApi(String str)
    {
        System.out.println("去空格后 ：" + str.replaceAll(" ", ""));
    }
}
