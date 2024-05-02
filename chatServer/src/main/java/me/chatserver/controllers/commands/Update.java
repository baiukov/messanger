package me.chatserver.controllers.commands;

import lombok.extern.slf4j.Slf4j;
import me.chatserver.enums.Commands;
import me.chatserver.services.AppService;

/**
 * Třída implementující rozhrání ICommand, pro nastavení příkazů.
 * Tento příkaz zpracuje obnovení dat uživatele
 *
 * @author Aleksei Baiukov
 * @version 01.05.2024
 */
@Slf4j
public class Update implements ICommand {

    // uložení názvu příkazu
    private final String name = Commands.UPDATE;

    // uložení služby aplikace
    private final AppService appService = AppService.getAppService();

    /**
     * Vrátí uložený název tohoto příkazu
     *
     * @return název příkazu
     */
    @Override
    public String getName() {
        return name;
    }

    @Override
    public String execute(String[] args) {
        log.info("Command " + name + " has been executed");
        return appService.update(args);
    }

    @Override
    public String toString() {
        return name;
    }

}
