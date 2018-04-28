package com.wei.wanandroid.javasources.designpatterns.proxy.staticproxy;

/**
 * 诉讼接口
 * @author: WEI
 * @date: 2018/4/27
 */
public interface ILawsuit {
    /** 提交申请 **/
    void submit();

    /** 进行举证 **/
    void burden();

    /** 开始辩护 **/
    void defend();

    /** 诉讼完成 **/
    void finish();
}
