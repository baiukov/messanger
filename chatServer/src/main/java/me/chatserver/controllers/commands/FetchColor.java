package me.chatserver.controllers.commands;

import lombok.extern.slf4j.Slf4j;
import me.chatserver.enums.Commands;
import me.chatserver.services.AppService;

@Slf4j
public class FetchColor implements ICommand {

    private final String name = Commands.FETCHCOLOR;

    private final AppService appService = AppService.getAppService();

    @Override
    public String getName() {
        return name;
    }

    @Override
    public String execute(String[] args) {
        log.info("Command " + name + " has been executed");
        return Commands.FETCHCOLOR + " " + appService.getUserColor(args).getHexcode();
    }

    @Override
    public String toString() {
        return name;
    }
}
