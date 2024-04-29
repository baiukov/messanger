package me.chatserver.controllers.commands;

import me.chatserver.enums.Commands;
import me.chatserver.services.AppService;

public class FetchDialogues implements ICommand{

    private final String name = Commands.FETCHDIALOGUES;

    private final AppService appService = AppService.getAppService();


    @Override
    public String getName() {
        return name;
    }

    @Override
    public String execute(String[] args) {
        return this.name + appService.getDialogues(args);
    }
}
