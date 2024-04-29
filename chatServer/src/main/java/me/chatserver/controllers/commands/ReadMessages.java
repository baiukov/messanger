package me.chatserver.controllers.commands;

import me.chatserver.enums.Commands;
import me.chatserver.services.AppService;

public class ReadMessages implements ICommand {

    private final String name = Commands.READMESSAGES;

    private final AppService appService = AppService.getAppService();

    @Override
    public String getName() {
        return name;
    }

    @Override
    public String execute(String[] args) {
        appService.readMessages(args);
        return null;
    }
}
