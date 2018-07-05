package com.wei.wanandroid.javasources.regexp;

import java.util.regex.Matcher;
import java.util.regex.Pattern;

/**
 * 正则表达式
 * @author: WEI
 * @date: 2018/7/2
 */
public class TestMobilePhone
{
    public static void main(String[] args)
    {
        String value = "020-5252950";
        new TestMobilePhone().test(value);
    }

    /**
     * 国际区号：(?:\(?[0\+]?\d{1,3}\)?)[\s-]?  例子：001-、1-、+1-、
     * 国内区号：(?:0|\d{1,4})[\s-]? 例子：0、"0 "、"0-"、"626-"、""、
     * @param value
     */
    private void test(String value)
    {
        // (?:pattern)
        //匹配 pattern 但不捕获该匹配的子表达式，即它是一个非捕获匹配，不存储供以后使用的匹配。
        // 这对于用"or"字符 (|) 组合模式部件的情况很有用。例如，'industr(?:y|ies) 是比 'industry|industries' 更经济的表达式。
//        String regex = "(?:\\(?[0\\+]?\\d{1,3}\\)?)[\\s-]?(?:0|\\d{1,4})[\\s-]?(?:(?:13\\d{9})|(?:\\d{7,8}))";
        String regex = "(^0\\d{2,3}[-]([2-9]\\d{6,7})+(\\d{1,4})?$)";
        if (!value.matches(regex))
        {
            System.out.println("添加失败，所填号码超出呼叫范围！");
        }
        else
        {
            System.out.println("所填号码格式正确！");
        }

        Pattern pattern = Pattern.compile(regex);
        Matcher matcher = pattern.matcher(value);
        System.out.println(matcher.matches() ? "格式匹配！" : "格式不匹配！");
    }
}
