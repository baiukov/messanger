package me.chatserver.controllers.commands;

public interface ICommand {

     String execute(String[] args);
     String getName();
}
