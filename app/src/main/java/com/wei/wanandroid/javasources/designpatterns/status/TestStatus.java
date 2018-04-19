package com.wei.wanandroid.javasources.designpatterns.status;

/**
 * @author: WEI
 * @date: 2018/4/19
 */
public class TestStatus
{
    public static void main(String[] args) {
        LoginManager loginManager = new LoginManager();
        loginManager.setUserStatus(new LogoutStatus());
        loginManager.share(null);
        loginManager.transpond(null);
        loginManager.comment(null);

        System.out.println("===============================================");

        loginManager.setUserStatus(new LoginStatus());
        loginManager.share(null);
        loginManager.transpond(null);
        loginManager.comment(null);
    }

}
