package me.chatserver.controllers.commands;

/**
 * Rozhrání nastavující metody pro všechny příkazy.
 * Každý příkaz musí implementovat toto rozhrání a zahrnovat
 * všechna uvedená nastavení pro správné zpracování.
 *
 * @author Aleksei Baiukov
 * @version 01.05.2024
 */
public interface ICommand {

     /**
      * Spustí se zpracování příkazu. Každý balík dat, který dostaneme po úpravení od kontrolera, obsahuje
      * data pro zpracování službou.
      *
      * @param args data získaná od klienta
      * @return řádek s odpovědí obsahující vysledek provedeného příkazu, který se pak
      * naparsuje frontend klienta
      */
     String execute(String[] args);

     /**
      * Vrátí uložený název tohoto příkazu
      *
      * @return název příkazu
      */
     String getName();
}
