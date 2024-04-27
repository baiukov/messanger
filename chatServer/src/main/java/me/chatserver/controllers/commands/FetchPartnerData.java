package me.chatserver.controllers.commands;

import me.chatserver.enums.Commands;
import me.chatserver.services.AppService;

public class FetchPartnerData implements ICommand {
    private final String name = Commands.FETCHPARTNERDATA;

    private final AppService appService = AppService.getAppService();

    @Override
    public String getName() {
        return name;
    }

    @Override
    public String execute(String[] args) {
        return this.name + " " + appService.getUserFullName(args) + " " + appService.getUserColor(args).getHexcode();
    }
}
