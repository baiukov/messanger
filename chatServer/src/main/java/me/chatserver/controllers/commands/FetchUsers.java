package me.chatserver.controllers.commands;

import me.chatserver.enums.Commands;
import me.chatserver.services.AppService;

public class FetchUsers implements ICommand {

    private final String name = Commands.FETCHUSERS;

    private final AppService appService = AppService.getAppService();

    @Override
    public String getName() {
        return name;
    }

    @Override
    public String execute(String[] args) {
        return Commands.FETCHUSERS + appService.findUsers(args);
    }
}
