package me.chatserver.controllers.commands;

import me.chatserver.services.AppService;

public class Find implements ICommand {

    private final String name = "FIND";

    private final AppService appService = AppService.getAppService();

    @Override
    public String getName() {
        return name;
    }

    @Override
    public String execute(String[] args) {
        return appService.findUsers(args);
    }
}
