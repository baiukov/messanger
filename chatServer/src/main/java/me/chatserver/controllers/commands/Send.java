package me.chatserver.controllers.commands;

import me.chatserver.enums.Commands;
import me.chatserver.services.AppService;

public class Send implements ICommand {

    private final String name = Commands.SEND;

    private final AppService appService = AppService.getAppService();

    @Override
    public String getName() {
        return name;
    }

    @Override
    public String execute(String[] args) {
        appService.sendMessage(args);
        return Commands.SEND + " ";
    }

}
