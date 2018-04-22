package com.wei.wanandroid.javasources.designpatterns.command;

public class Buttons
{
    private LeftCommand leftCommand;
    private RightCommand rightCommand;

    public void setLeftCommand(LeftCommand leftCommand) {
        this.leftCommand = leftCommand;
    }

    public void setRightCommand(RightCommand rightCommand) {
        this.rightCommand = rightCommand;
    }

    public void toleft()
    {
        leftCommand.execute();
    }

    public void toRight()
    {
        rightCommand.execute();
    }
}
