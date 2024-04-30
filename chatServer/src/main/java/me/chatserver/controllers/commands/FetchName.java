package me.chatserver.controllers.commands;

import lombok.extern.slf4j.Slf4j;
import me.chatserver.enums.Commands;
import me.chatserver.enums.Events;
import me.chatserver.services.AppService;

@Slf4j
public class FetchName implements ICommand {

    private final String name = Commands.FETCHNAME;

    private final AppService appService = AppService.getAppService();

    @Override
    public String getName() {
        return name;
    }

    @Override
    public String execute(String[] args) {
        log.info("Command " + name + " has been executed");
        return this.name + " " + appService.getUserFullName(args);
    }

    @Override
    public String toString() {
        return name;
    }
}
