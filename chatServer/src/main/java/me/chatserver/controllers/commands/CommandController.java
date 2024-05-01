package me.chatserver.controllers.commands;

import lombok.extern.slf4j.Slf4j;
import me.chatserver.controllers.ClientHandler;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.Optional;

@Slf4j
public class CommandController {
    private final List<ICommand> commands = new ArrayList<>();

    private final ClientHandler clientHandler;

    public CommandController(ClientHandler clientHandler) {
        this.clientHandler = clientHandler;
        this.init();
    }

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
        log.info("Command " + commandName + " has been recognized");
        if (response == null) return;
        clientHandler.sendMessage(response);
    }

}
