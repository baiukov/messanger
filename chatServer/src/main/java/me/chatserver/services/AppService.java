package me.chatserver.services;

import at.favre.lib.crypto.bcrypt.BCrypt;
import me.chatserver.entities.User;
import me.chatserver.enums.Events;
import me.chatserver.repositories.UserRepository;

import java.util.List;

public class AppService {

    private static AppService appService;

    private final UserRepository userRepository = new UserRepository();

    private AppService() {}

    public static AppService getAppService() {
        if (appService == null) {
            appService = new AppService();
        }
        return appService;
    }

    public String login(String[] data) {
        String login = data[1];
        String password = data[2];

        return null;
    }

    public String register(String[] data) {
        if (data.length != 5) {
            return Events.ERROR + "You didn't fill the form properly";
        }

        String login = data[1];
        String firstName = data[2];
        String lastName = data[3];
        String password = data[4];

        if (login == null || firstName == null || lastName == null || password == null) {
            return Events.ERROR + "You didn't fill the form properly";
        }

        if (password.length() < 6) {
            return Events.ERROR + "Password should contain at least 6 characters";
        }

        if (login.length() < 4) {
            return Events.ERROR + "User name should contain at least 4 characters";
        }

        List<User> existingUsers = userRepository.getByUserName(login);
        if (existingUsers == null || !existingUsers.isEmpty()) {
            return Events.ERROR + "This user name is already taken";
        }

        User user = new User();
        user.setUserName(login);
        user.setFirstName(firstName);
        user.setLastName(lastName);
        user.setPassword(BCrypt.withDefaults().hashToString(12, password.toCharArray()));

        userRepository.save(user);
        return Events.SUCCESSREGISTER + user.getID();
    }

}
