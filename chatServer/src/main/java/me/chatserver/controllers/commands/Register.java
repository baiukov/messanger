package me.chatserver.controllers.commands;

import me.chatserver.services.AppService;

public class Register implements ICommand {

    private final String name = "REGISTER";

    private final AppService appService = AppService.getAppService();

    @Override
    public String getName() {
        return name;
    }

    @Override
    public String execute(String[] args) {
        return appService.register(args);
    }

}
