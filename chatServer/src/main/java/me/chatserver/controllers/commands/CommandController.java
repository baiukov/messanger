package me.chatserver.controllers.commands;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.Optional;

public class CommandController {
    List<ICommand> commands = new ArrayList<>();
    private CommandController commandController;

    public CommandController() {
        this.init();
    }

    public void init() {
        commands.addAll(List.of(
                new Login(),
                new Register()
        ));
    }

    public String onCommand(String dataStr) {
        String[] data = dataStr.split(" ");
        String commandName = data[0];
        Optional<ICommand> command = commands.stream()
                .filter(c -> c.getName().equalsIgnoreCase(commandName))
                .findFirst();
        return command.map(iCommand -> iCommand.execute(data)).orElse(null);
    }

}
