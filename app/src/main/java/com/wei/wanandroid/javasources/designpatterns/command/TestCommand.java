package com.wei.wanandroid.javasources.designpatterns.command;

public class TestCommand {
    public static void main(String[] args) {
        Game game = new Game();
        Command leftCmd = new LeftCommand(game);
        Command rightCmd = new RightCommand(game);

        Buttons buttons = new Buttons();
        buttons.setLeftCommand((LeftCommand) leftCmd);
        buttons.setRightCommand((RightCommand) rightCmd);

        // 具体按下哪个按钮由玩家决定
        buttons.toleft();
        buttons.toRight();
    }
}
