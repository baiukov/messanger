package me.chatserver.controllers.commands;

import lombok.extern.slf4j.Slf4j;
import me.chatserver.controllers.ClientHandler;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

/**
 * Třída, která zpracová zpracování příkazu tak, že dostane zparsovány název příkazu a data
 * od klienta a pokusí se vyhledat a zpracovat příkaz
 *
 * @author Aleksei Baiukov
 * @version 01.05.2024
 */
@Slf4j
public class CommandController {

    // seznam znamých příkazů
    private final List<ICommand> commands = new ArrayList<>();

    // uložení posloucháče klientů
    private final ClientHandler clientHandler;

    /**
     * Konstruktor třídy specifikující posloucháče klientů a intializující příkazy
     */
    public CommandController(ClientHandler clientHandler) {
        this.clientHandler = clientHandler;
        this.init();
    }

    /**
     * Metoda pro initializaci příkazů a uložení je do seznamů znamých
     * příkazů
     */
    public void init() {
        commands.addAll(List.of(
                new Login(),
                new Register(),
                new FetchName(),
                new FetchUsers(),
                new FetchColor(),
                new FetchPartnerData(),
                new Send(),
                new FetchMessages(),
                new FetchDialogues(),
                new ReadMessages(),
                new Pin(),
                new Unpin(),
                new Block(),
                new Update()
        ));
        log.info("Command controller has been initialized with following command: " + commands);
    }

    /**
     * Metoda vyvolána po získání zprávy od klienta. Zpráva obsahuje balík dat
     * prostřednictvím řádku. Tato metoda naparsuje je do pole s jednotlivými prvky, jake první je název příkazu,
     * podle něj si pak zkusí vyhledat příkaz v seznamu znamých příkazů, zpracovat tento příkaz a
     * poslat odpověď klientovi
     *
     * @param dataStr zpráva od klienta
     *
     */
    public void onCommand(String dataStr) {
        String[] data = dataStr.split(" ");
        if (data.length < 1) {
            log.debug("Received incorrect data to execute command " + dataStr);
            return;
        }
        String commandName = data[0];
        Optional<ICommand> command = commands.stream()
                .filter(c -> c.getName().equalsIgnoreCase(commandName))
                .findFirst();
        String response = command.map(iCommand -> iCommand.execute(data)).orElse(null);
        log.info("Command " + commandName + " has been recognized. Response: " + response);
        if (response == null) return;
        clientHandler.sendMessage(response);
    }

}
