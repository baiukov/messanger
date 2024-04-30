package me.chatserver.controllers.commands;

import lombok.extern.slf4j.Slf4j;
import me.chatserver.enums.Commands;
import me.chatserver.services.AppService;

@Slf4j
public class FetchUsers implements ICommand {

    private final String name = Commands.FETCHUSERS;

    private final AppService appService = AppService.getAppService();

    @Override
    public String getName() {
        return name;
    }

    @Override
    public String execute(String[] args) {
        log.info("Command " + name + " has been executed");
        return Commands.FETCHUSERS + appService.findUsers(args);
    }

    @Override
    public String toString() {
        return name;
    }
}
