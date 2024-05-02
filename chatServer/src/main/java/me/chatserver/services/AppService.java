package me.chatserver.services;

import at.favre.lib.crypto.bcrypt.BCrypt;
import lombok.extern.slf4j.Slf4j;
import me.chatserver.database.MessageRepository;
import me.chatserver.database.RelationsRepository;
import me.chatserver.entities.Color;
import me.chatserver.entities.Message;
import me.chatserver.entities.Relation;
import me.chatserver.entities.User;
import me.chatserver.enums.Events;
import me.chatserver.database.ColorRepository;
import me.chatserver.database.UserRepository;

import java.math.BigInteger;
import java.sql.Timestamp;
import java.util.ArrayList;
import java.util.Comparator;
import java.util.Iterator;
import java.util.List;
import java.util.stream.Collectors;

/**
 * Třída služby aplikace - je služba uchovavající entity a databazové repozitáře
 * potřebné pro práci s aplikací. Dostane požadavek na zprácování logiky podle dat od příkazu, a po
 * zprácovaní vrátí nějakou odpověď, pokud je potřeba.
 *
 * @author Aleksei Baiukov
 * @version 02.05.2024
 */
@Slf4j
public class AppService {

    /**
     * Instance této třídy, potřebná pro realizaci patternu SINGLETON
     */
    private static AppService appService;

    /**
     * Instance databazového repositáře uživatelů
     */
    private final UserRepository userRepository = new UserRepository();

    /**
     * Instance databazového repositáře zpráv
     */
    private final MessageRepository messageRepository = new MessageRepository();

    /**
     * Instance databazového repositáře vztahů
     */
    private final RelationsRepository relationsRepository = new RelationsRepository();

    /**
     * Privatní konstruktor třídy
     */
    private AppService() {
        log.info("AppService has been successfully initialized");
    }

    /**
     * Metoda pro získání instance třídy, pokud nebyla ještě vytvořená, vytvoří novou, založí ji a vrátí
     *
     * @return instance třídy
     */
    public static AppService getAppService() {
        if (appService == null) {
            appService = new AppService();
        }
        return appService;
    }

    /**
     * Metoda pro zpracování logiky příhlášení.
     * Ověří jestli přijatá data jsou postačující a jestli uživatel s takovým uživatelským jménem
     * existuje, pak vrátí identifikační číslo
     *
     * @param data název příkazu, uživatelské jméno, heslo
     * @return buď chyba nebo úspěšný vchod a identifikační číslo uživatele
     */
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

    /**
     * Metoda pro zpracování logiky registrace.
     * Ověří jestli přijatá data jsou postačující, jestli není chybná, jestli heslo obsahují více jak 6 znaků
     * jestli uživatel s takovým uživatelským jménem neexistuje, jestli uživatelské jméno obsahuje více jak 4 znaky,
     * pak vytvoří nového a uloží do databáze
     *
     * @param data název příkazu, uživatelské jméno, jméno, křestní jméno, heslo
     * @return buď chyba nebo úspěšná registrace a identifikační číslo uživatele
     */
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

        // pro uložení uživatele musíme náhodně vybrat barvu
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

    /**
     * Metoda pro zpracování logiky hledání uživatelů podle části uživatelského jména, nebo jména, nebo křestního jména.
     * Ověří jestli přijatá data jsou postačující, pak pokusí se najít v databáze uživatele podle
     * patternu a vypíše seznam
     *
     * @param data řádek obsahující jméno uživatele
     * @return buď chyba nebo seznam uživatelů
     */
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

    /**
     * Metoda pro zpracování logiky odeslání zprávy.
     * Ověří jestli přijatá data jsou postačující, pak pokusí se vyhledat jejích vztah, pokud nenajde uloží nový vztah
     * mezi nimi, vytvoží a uloží zprávu
     *
     * @param data ID odesílatele, ID přijemce, všechny další prvky jsou považovany za slova zprávy
     */
    public void sendMessage(String[] data) {
        if (data.length < 4) return;
        String senderID = data[1];
        User sender = userRepository.getById(senderID);
        String receiverID = data[2];
        User receiver = userRepository.getById(receiverID);

        boolean isRelation = !relationsRepository.getByUsers(senderID, receiverID).isEmpty();
        if (!isRelation) {
            Relation relation = new Relation();
            relation.setUser1(sender);
            relation.setUser2(receiver);
            relationsRepository.save(relation);
        }

        // každý další prvky po třetím přídá do jednoho textového řádku s mezerami
        StringBuilder sb = new StringBuilder();
        String delimiter = "";
        for (int i = 3; i < data.length; i++) {
            sb.append(delimiter).append(data[i]);
            delimiter = " ";
        }
        String messageText = sb.toString();

        Message message = new Message();
        message.setSender(sender);
        message.setReceiver(receiver);
        message.setMessage(messageText);
        message.setIsRead(false);
        messageRepository.save(message);
    }

    /**
     * Metoda pro zpracování získání zpráv.
     * Ověří jestli přijatá data jsou postačující, pak pokusí se vyhledat jejích zpravy, a vypíše je seznam
     *
     * @param args ID odesílatele, ID přijemce
     * @return řádek obsahující ID zprávy, ID odesílatele, ID příjemce, textu zprávy a datumu odeslání
     */
    public String getMessages(String[] args) {
        if (args.length < 3) return null;
        String id = args[1];
        String partnerID = args[2];
        List<Object[]> rawMessages = messageRepository.getMessagesByUserID(id, partnerID);
        StringBuilder sb = new StringBuilder();
        String del = " ";
        for (Object[] messageObject : rawMessages) {
            if (messageObject.length < 4) continue;
            String messageID = (String) messageObject[0];
            String senderID = (String) messageObject[1];
            String message = ((String) messageObject[2]).replaceAll(" ", "/+");
            Timestamp createdAt = (Timestamp) messageObject[3];
            sb.append(del).append(messageID)
                .append(del).append(senderID)
                .append(del).append(message)
                .append(del).append(createdAt);
        }
        return sb.toString();
    }

    /**
     * Metoda pro zpracování získání chatů.
     * Ověří jestli přijatá data jsou postačující, pak pokusí se vyhledat chaty, ke kterým uživatel má přístup
     *
     * @param data ID uživatele
     * @return řádek obsahující ID partnera, jméno partnera, křestní jméno partnera, hex kód jeho barvy,
     *          poslední zprávu, počet nepřečtených zpráv od něho a jestli je chat s nim připnutý
     */
    public String getDialogues(String[] data) {
        if (data.length < 2) return null;
        String id = data[1];

        List<Relation> relations = relationsRepository.getByUser(id);
        List<Relation> sortedRelations = relations.stream()
                .sorted(Comparator.comparing(Relation::getIsPinned).reversed())
                .toList();
        User user = userRepository.getById(id);

        StringBuilder sb = new StringBuilder();
        String del = " ";
        for (Relation relation : sortedRelations) {
            if (relation.getIsBlocked()) continue;

            User user1 = relation.getUser1();
            User user2 = relation.getUser2();
            User partner = user.equals(user1) ? user2 : user1;

            String partnerID = partner.getID();
            String lastMessage = messageRepository.getLastMessagesByUsers(id, partnerID).get(0);

            BigInteger amount = messageRepository.getAmountUnreadMessages(id, partnerID).get(0);

            sb.append(del).append(partnerID)
                    .append(del).append(partner.getFirstName())
                    .append(del).append(partner.getLastName())
                    .append(del).append(partner.getColor().getHexcode())
                    .append(del).append(lastMessage.replaceAll(" ", "/+"))
                    .append(del).append(amount)
                    .append(del).append(relation.getIsPinned());
        }

        return sb.toString();
    }

    /**
     * Metoda pro zpracování logiky přečtení zpráv
     * Ověří jestli přijatá data jsou postačující, pak označí všechny zpravy přijaté uživatelem v tomto chatu
     * za přečtené
     *
     * @param args ID uživatele, ID partnera
     */
    public void readMessages(String[] args) {
        if (args.length < 3) return;
        String id = args[1];
        String partnerID = args[2];

        messageRepository.setMessagesRead(id, partnerID);
    }

    /**
     * Metoda pro zpracování zablokování uživatele
     * Ověří jestli přijatá data jsou postačující, jestli existuje vztah, pokud ne, tak ho uloží,
     * pak označí vztah za zablokováný
     *
     * @param args ID uživatele, ID partnera
     */
    public void block(String[] args) {
        if (args.length < 3) return;
        String userID = args[1];
        String partnerID = args[2];

        List<Relation> relations = relationsRepository.getByUsers(userID, partnerID);
        if (relations.isEmpty()) {
            Relation relation = new Relation();
            User user = userRepository.getById(userID);
            User partner = userRepository.getById(partnerID);
            relation.setUser1(user);
            relation.setUser2(partner);
            relation.setIsBlocked(true);
            relationsRepository.save(relation);
            return;
        }
        relationsRepository.block(userID, partnerID, true);
    }

    /**
     * Metoda pro zpracování připnutí chatu s uživatelem
     * Ověří jestli přijatá data jsou postačující, pak přepošle ji na metodu nastavení připnutí
     *
     * @param args ID uživatele, ID partnera
     */
    public void pin(String[] args) {
        if (args.length < 3) return;
        String userID = args[1];
        String partnerID = args[2];
        this.pin(userID, partnerID, true);
    }

    /**
     * Metoda pro zpracování odepnutí chatu s uživatelem
     * Ověří jestli přijatá data jsou postačující, pak přepošle ji na metodu nastavení připnutí
     *
     * @param args ID uživatele, ID partnera
     */
    public void unpin(String[] args) {
        if (args.length < 3) return;
        String userID = args[1];
        String partnerID = args[2];
        this.pin(userID, partnerID, false);
    }

    /**
     * Metoda pro nastavení připnutí chatu s uživatelem
     * Dostane uživatele, pokusí se vyhledat jejich vztah, pokud neexistuje, tak se vytvoří a uloží,
     * pak chat bude připnout nebo odepnout
     *
     * @param userID identifikační číslo uživatele
     * @param partnerID identifikační číslo partnera
     * @param isPinned jestli se musí jejich chat připnout
     */
    public void pin(String userID, String partnerID, boolean isPinned) {
        List<Relation> relations = relationsRepository.getByUsers(userID, partnerID);
        if (relations.isEmpty()) {
            Relation relation = new Relation();
            User user = userRepository.getById(userID);
            User partner = userRepository.getById(partnerID);
            relation.setUser1(user);
            relation.setUser2(partner);
            relation.setIsPinned(isPinned);
            relationsRepository.save(relation);
            return;
        }
        relationsRepository.pin(userID, partnerID, isPinned);
    }

    /**
     * Metoda pro zpracování logiky obnovení dat uživatele
     * Ověří jestli přijatá data jsou postačující, jestli heslo obsahuje více jak 6 znaků,
     * jestli některá položka obsahuje null nebo "null" vymění ji za stará data uživatele, pak uloží nová uživatelská
     * data do databáze
     *
     * @param data ID uživatele, jméno, křestní jméno, heslo
     * @return buď chyba nebo nic
     */
    public String update(String[] data) {
        if (data.length < 5) {
            return Events.ERROR + "Fill the form properly";
        }

        String id = data[1];

        User user = userRepository.getById(id);
        String firstName = data[2] != null && data[2].equals("null") ? user.getFirstName() : data[2] ;
        String lastName = data[3] != null && data[3].equals("null") ? user.getLastName() : data[3];
        String passwordStr = data[4];

        if (passwordStr == null) {
            return Events.ERROR + "Fill the form properly";
        }

        String password = passwordStr.equals("null") ? user.getPassword() : BCrypt.withDefaults()
                .hashToString(12, passwordStr.toCharArray());

        if (firstName == null || lastName == null || password == null) {
            return Events.ERROR + "Fill the form properly";
        }

        if (passwordStr.length() < 6) {
            return Events.ERROR + "Password doesnt contain 6 chars";
        }

        userRepository.update(id, firstName, lastName, password);
        return null;
    }

    /**
     * Metoda pro zpracování získání celého jména
     * Ověří jestli podle přijatých dat existuje uživatel, pokud ano, vrátí jeho Jméno Přijmení, jinak vrátí
     * standardní Jméno Přijmení
     *
     * @param data data
     * @return Jméno Přijmení uživatele
     */
    public String getUserFullName(String[] data) {
        User user = getUser(data);
        if (user == null) {
            return "Unknown Unknown";
        }
        return user.getFirstName() + " " + user.getLastName();
    }

    /**
     * Metoda pro zpracování získání barvy uživatele
     * Ověří jestli podle přijatých dat existuje uživatel, pokud ano, vrátí jeho barvu, jinak vrátí
     * standardní barvu (modrá)
     *
     * @param data data
     * @return instance barvy
     */
    public Color getUserColor(String[] data) {
        User user = getUser(data);
        if (user == null) {
            return new Color("F2C4DE");
        }
        return user.getColor();
    }

    /**
     * Metoda pro zpracování získání instance uživatele
     * Ověří jestli podle přijatých dat existuje uživatel, pokud ano, vrátí jeho instanci
     *
     * @param data identifikační číslo uživatele
     * @return instance uživatele nebo null
     */
    public User getUser(String[] data) {
        if (data.length < 2) return null;
        String id = data[1];
        return userRepository.getById(id);
    }

    /**
     * Metoda pro zpracování získání data uživatele úpraveného pro partnera
     * Ověří jestli přijatá data jsou postačující, pokud ano, pokusí se získat jeho celé jméno a barvu a vratit
     * to jako "ID CeléJméno Barva"
     *
     * @param args data
     * @return řádek partneru
     */
    public String getPartnerData(String[] args) {
        if (args.length < 2) return null;
        return args[1] + " " + appService.getUserFullName(args) + " " + appService.getUserColor(args).getHexcode();
    }

}
