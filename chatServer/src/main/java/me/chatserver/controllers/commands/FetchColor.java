package me.chatserver.controllers.commands;

import me.chatserver.enums.Commands;
import me.chatserver.services.AppService;

public class FetchColor implements ICommand {

    private final String name = Commands.FETCHCOLOR;

    private final AppService appService = AppService.getAppService();

    @Override
    public String getName() {
        return name;
    }

    @Override
    public String execute(String[] args) {
        return Commands.FETCHCOLOR + " " + appService.getUserColor(args).getHexcode();
    }
}
