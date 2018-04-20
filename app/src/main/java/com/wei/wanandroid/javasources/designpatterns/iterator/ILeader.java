package com.wei.wanandroid.javasources.designpatterns.iterator;

/**
 * @author: WEI
 * @date: 2018/4/20
 */
public abstract class ILeader {
    protected ILeader mNextHandler;

    /**
     * 处理报账请求
     * @param money 希望批复的金额
     */
    public void handleRequest(int money)
    {
        if (money <= limit())
        {
            handle(money);
        }
        else
        {
            if (null != mNextHandler)
            {
                mNextHandler.handleRequest(money);
            }
        }
    }

    /**
     * 处理报账行为
     * @param money  报账金额
     */
    protected abstract void handle(int money);

    /**
     * 自身能批复的额度权限
     * @return 额度
     */
    protected abstract int limit();
}
