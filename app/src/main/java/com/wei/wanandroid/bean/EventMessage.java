package com.wei.wanandroid.bean;

import javax.inject.Inject;

import dagger.Module;

/**
 * @author: WEI
 * @date: 2018/6/15
 */
@Module
public class EventMessage
{
    public String mMsg;
    @Inject
    public EventMessage(String msg)
    {
        mMsg = msg;
    }

    @Override
    public String toString() {
        return "EventMessage{" +
                "mMsg='" + mMsg + '\'' +
                '}';
    }
}
