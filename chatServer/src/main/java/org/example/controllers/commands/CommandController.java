package org.example.controllers.commands;

import java.util.ArrayList;
import java.util.List;

public class CommandController {
    List<ICommand> names = new ArrayList<>();
    private CommandController commandController;

    private CommandController() {
        setNames();
    }

    public CommandController getController() {
        if (commandController == null){
            commandController = new CommandController();
        }
        return commandController;
    }


    public void setNames() {
        this.names.addAll(List.of(new Login(), new Register()));
    }

    public String[] useCommand(String line) {
        String[] words = line.split(" ");
        names.forEach( name -> {
            if(name.getName().equals(words[0])){
                name.execute(words);
            }
        });
        return words;
    }

}
