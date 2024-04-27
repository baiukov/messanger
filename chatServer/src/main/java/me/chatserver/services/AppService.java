package me.chatserver.services;

import at.favre.lib.crypto.bcrypt.BCrypt;
import me.chatserver.entities.Color;
import me.chatserver.entities.User;
import me.chatserver.enums.Events;
import me.chatserver.repositories.ColorRepository;
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
        if (data.length < 3) {
            return Events.ERROR + "You did not fill the form properly";
        }

        String login = data[1];
        String password = data[2];

        List<User> existingUsers = userRepository.getByUserName(login);
        if (existingUsers == null || existingUsers.isEmpty()) {
            return Events.ERROR + "User does not exist";
        }
        User user = existingUsers.get(0);

        BCrypt.Result result = BCrypt.verifyer().verify(password.toCharArray(), user.getPassword());

        return result.verified ? Events.SUCCESSLOGIN + user.getID() : Events.ERROR + "Wrong password";
    }

    public String register(String[] data) {
        if (data.length < 5) {
            return Events.ERROR + "You did not fill the form properly";
        }

        String login = data[1];
        String firstName = data[2];
        String lastName = data[3];
        String password = data[4];

        if (login == null || firstName == null || lastName == null || password == null) {
            return Events.ERROR + "You did not fill the form properly";
        }

        if (password.length() < 6) {
            return Events.ERROR + "Password should contain at least 6 characters";
        }

        if (login.length() < 4) {
            return Events.ERROR + "User name should contain at least 4 characters";
        }

        List<User> existingUsers = userRepository.getByUserName(login);
        if (existingUsers != null && !existingUsers.isEmpty()) {
            return Events.ERROR + "This user name is already taken";
        }

        ColorRepository colorRepository = new ColorRepository();
        int colorsAmount = colorRepository.getAvailableAmount();
        long randomColor = Math.round(Math.random() * (colorsAmount - 1) + 1);
        Color color = colorRepository.getColor((int) randomColor);

        User user = new User();
        user.setUserName(login);
        user.setFirstName(firstName);
        user.setLastName(lastName);
        user.setPassword(BCrypt.withDefaults().hashToString(12, password.toCharArray()));
        user.setColor(color);

        userRepository.save(user);
        return Events.SUCCESSREGISTER + user.getID();
    }

    public String findUsers(String[] data) {
        if (data.length < 2) return null;
        String startsWith = data[1];
        List<User> users = userRepository.getUsersByStartsWith(startsWith);
        StringBuilder sb = new StringBuilder();
        for (User user : users) {
            sb.append(" ").append(user.getFirstName())
                    .append(" ").append(user.getLastName())
                    .append(" ").append(user.getID());
        }
        return sb.toString();
    }

    public String getUserFullName(String[] data) {
        User user = getUser(data);
        if (user == null) {
            return "Unknown Unknown";
        }
        return user.getFirstName() + " " + user.getLastName();
    }

    public Color getUserColor(String[] data) {
        User user = getUser(data);
        if (user == null) {
            return new Color("F2C4DE");
        }
        return user.getColor();
    }

    public User getUser(String[] data) {
        if (data.length < 2) return null;
        String id = data[1];
        return userRepository.getById(id);
    }

}
