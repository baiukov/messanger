package me.chatserver.controllers.commands;

import me.chatserver.controllers.ClientHandler;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.Optional;

public class CommandController {
    List<ICommand> commands = new ArrayList<>();

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
                new ReadMessages()
        ));
    }

    public void onCommand(String dataStr) {
        String[] data = dataStr.split(" ");
        String commandName = data[0];
        Optional<ICommand> command = commands.stream()
                .filter(c -> c.getName().equalsIgnoreCase(commandName))
                .findFirst();
        String response = command.map(iCommand -> iCommand.execute(data)).orElse(null);
        if (response == null) return;
        clientHandler.sendMessage(response);
    }

}
