package me.chatserver.controllers.commands;

import me.chatserver.enums.Commands;
import me.chatserver.enums.Events;
import me.chatserver.services.AppService;

public class FetchName implements ICommand {

    private final String name = Commands.FETCHNAME;

    private final AppService appService = AppService.getAppService();

    @Override
    public String getName() {
        return name;
    }

    @Override
    public String execute(String[] args) {
        return this.name + " " + appService.getUserFullName(args);
    }
}
