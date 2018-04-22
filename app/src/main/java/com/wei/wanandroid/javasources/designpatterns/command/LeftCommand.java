package com.wei.wanandroid.javasources.designpatterns.command;

public class LeftCommand implements Command {
    Game game;

    public LeftCommand(Game game)
    {
        this.game = game;
    }

    @Override
    public void execute() {
        game.toLeft();
    }
}
