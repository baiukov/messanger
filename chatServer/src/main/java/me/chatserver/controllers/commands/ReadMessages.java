package me.chatserver.controllers.commands;

import lombok.extern.slf4j.Slf4j;
import me.chatserver.enums.Commands;
import me.chatserver.services.AppService;

/**
 * Třída implementující rozhrání ICommand, pro nastavení příkazů.
 * Tento příkaz zpracuje přečtení zpráv v chatu jedným z uživatelů
 *
 * @author Aleksei Baiukov
 * @version 01.05.2024
 */
@Slf4j
public class ReadMessages implements ICommand {

    // uložení názvu příkazu
    private final String name = Commands.READMESSAGES;

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

    /**
     * Spustí se zpracování příkazu. Každý balík dat, který dostaneme po úpravení od kontrolera, obsahuje
     * data pro zpracování službou.
     *
     * @param args data získaná od klienta
     * @return řádek s odpovědí obsahující vysledek provedeného příkazu, který se pak
     * naparsuje frontend klienta
     */
    @Override
    public String execute(String[] args) {
        appService.readMessages(args);
        log.info("Command " + name + " has been executed");
        return null;
    }

    @Override
    public String toString() {
        return name;
    }
}
