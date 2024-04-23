package me.chatserver.controllers.commands;

public class Login implements ICommand{
    private final String name = "LOGIN";

    @Override
    public String getName() {
        return name;
    }

    @Override
    public String execute(String[] args) {
        return null;
    }
}
