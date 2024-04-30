package me.chatserver.controllers.commands;

import me.chatserver.enums.Commands;
import me.chatserver.services.AppService;

public class Login implements ICommand{
    private final String name = Commands.LOGIN;

    private final AppService appService = AppService.getAppService();

    @Override
    public String getName() {
        return name;
    }

    @Override
    public String execute(String[] args) {
        return appService.login(args);
    }
}
