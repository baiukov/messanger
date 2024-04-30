package me.chatserver.controllers.commands;

import lombok.extern.slf4j.Slf4j;
import me.chatserver.enums.Commands;
import me.chatserver.services.AppService;

@Slf4j
public class Block implements ICommand {
    private final String name = Commands.BLOCK;

    private final AppService appService = AppService.getAppService();

    @Override
    public String getName() {
        return name;
    }

    @Override
    public String execute(String[] args) {
        appService.block(args);
        log.info("Command " + name + " has been executed");
        return null;
    }

    @Override
    public String toString() {
        return name;
    }
}
