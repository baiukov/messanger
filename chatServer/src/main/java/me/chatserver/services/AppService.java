package me.chatserver.services;

import me.chatserver.entities.User;
import me.chatserver.repositories.UserRepository;

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

    public String register(String[] data) throws NullPointerException {
        String login = data[1];
        String firstName = data[2];
        String lastName = data[3];
        String password = data[4];

        User user = new User();
        user.setUserName(login);
        user.setFirstName(firstName);
        user.setLastName(lastName);
        user.setPassword(password);

        return userRepository.save(user);
    }

}
