package com.wei.wanandroid.javasources.designpatterns.command;

public class RightCommand implements Command {
    Game game ;

    public RightCommand(Game game)
    {
        this.game = game;
    }

    @Override
    public void execute() {
        game.toRight();
    }
}
