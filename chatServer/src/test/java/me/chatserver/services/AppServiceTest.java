package me.chatserver.services;

import at.favre.lib.crypto.bcrypt.BCrypt;
import me.chatserver.database.*;
import me.chatserver.entities.Color;
import me.chatserver.entities.Message;
import me.chatserver.entities.Relation;
import me.chatserver.entities.User;
import me.chatserver.enums.Events;
import me.chatserver.utils.HibernateUtil;
import org.h2.jdbcx.JdbcDataSource;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.Arguments;
import org.junit.jupiter.params.provider.MethodSource;
import org.junit.jupiter.params.provider.ValueSource;

import javax.sql.DataSource;
import java.math.BigInteger;
import java.util.List;
import java.util.Optional;
import java.util.UUID;
import java.util.stream.Stream;

import static org.junit.jupiter.api.Assertions.*;

public class AppServiceTest {

    private static final String testUserID = "c1dfd651-eff7-4113-9931-90780be76075";
    private static final String testUser2ID = "714b24c7-e001-4710-aff9-faef3cceb911";
    private static final String testUserForUpdateID = "5c4eff24-be95-42a8-9e76-280bc0db9032";

    private static User testUser1;
    private static User testUser2;
    private static User testUserForUpdate;


    private final MessageRepository messageRepository = new MessageRepository();
    private final RelationsRepository relationsRepository = new RelationsRepository();
    private static final UserRepository userRepository = new UserRepository();

    private final AppService appService = AppService.getAppService();

    @BeforeAll
    public static void setUpUsers() {
        HibernateUtil.buildSessionFactory(getDataSource());
        ColorRepository colorRepository = new ColorRepository();
        Color color = colorRepository.getColor(1);

        User user = new User();
        user.setID(UUID.fromString(testUserID));
        user.setUserName("userName");
        user.setFirstName("firstName");
        user.setLastName("lastName");
        user.setColor(color);
        user.setPassword(BCrypt.withDefaults().hashToString(12, "strongPassword".toCharArray()));
        userRepository.save(user);
        testUser1 = user;

        User user2 = new User();
        user2.setID(UUID.fromString(testUser2ID));
        user2.setUserName("bestNickName");
        user2.setFirstName("Jakub");
        user2.setLastName("Dvorak");
        user2.setColor(color);
        user2.setPassword(BCrypt.withDefaults().hashToString(12, "veryStrongPass".toCharArray()));
        userRepository.save(user2);
        testUser2 = user2;

        User user3 = new User();
        user3.setID(UUID.fromString(testUserForUpdateID));
        user3.setUserName("test");
        user3.setFirstName("oldName");
        user3.setLastName("oldSurname");
        user3.setColor(color);
        user3.setPassword(BCrypt.withDefaults().hashToString(12, "password".toCharArray()));
        userRepository.save(user3);
        testUserForUpdate = user3;
    }

    private static Stream<Arguments> getLoginCases() {
        return Stream.of(
                Arguments.of(
                        "Given incorrect data array should return error",
                        new String[]{},
                        Events.ERROR + "You did not fill the form properly"
                ),
                Arguments.of(
                        "Given not existing userName should return error",
                        new String[]{"", "randomName", "password"},
                        Events.ERROR + "User does not exist"
                ),
                Arguments.of(
                        "Given existing user with wrong password should return error",
                        new String[]{"", "userName", "wrongPassword"},
                        Events.ERROR + "Wrong password"
                ),
                Arguments.of(
                        "Given existing user with should success",
                        new String[]{"", "userName", "strongPassword"},
                        Events.SUCCESSLOGIN + testUserID
                )
        );
    }

    private static Stream<Arguments> getRegisterFailCases() {
        return Stream.of(
                Arguments.of(
                        "Given empty data array should return error",
                        new String[]{},
                        Events.ERROR + "You did not fill the form properly"
                ),
                Arguments.of(
                        "Given incorrect data array should return error",
                        new String[]{"", null, null, null, null},
                        Events.ERROR + "You did not fill the form properly"
                ),
                Arguments.of(
                        "Given weak password should return error",
                        new String[]{"", "userName", "name", "surname", "123"},
                        Events.ERROR + "Password should contain at least 6 characters"
                ),
                Arguments.of(
                        "Given short username should return error",
                        new String[]{"", "me", "name", "surname", "strongPassword"},
                        Events.ERROR + "User name should contain at least 4 characters"
                ),
                Arguments.of(
                        "Given existing user name should return error",
                        new String[]{"", "userName", "name", "surname", "strongPassword"},
                        Events.ERROR + "This user name is already taken"
                )
        );
    }

    private static Stream<Arguments> findUsersCases() {
        return Stream.of(
                Arguments.of(
                        "Given empty data array should return error",
                        new String[]{},
                        null
                ),
                Arguments.of(
                        "Given part of user name should return user data",
                        new String[]{"", "u"},
                        " firstName lastName c1dfd651-eff7-4113-9931-90780be76075"
                ),
                Arguments.of(
                        "Given part of user name should return user data",
                        new String[]{"", "user"},
                        " firstName lastName c1dfd651-eff7-4113-9931-90780be76075"
                ),
                Arguments.of(
                        "Given user name should return user data",
                        new String[]{"", "userName"},
                        " firstName lastName c1dfd651-eff7-4113-9931-90780be76075"
                ),
                Arguments.of(
                        "Given part of user first name should return user data",
                        new String[]{"", "f"},
                        " firstName lastName c1dfd651-eff7-4113-9931-90780be76075"
                ),
                Arguments.of(
                        "Given part of user first name should return user data",
                        new String[]{"", "firs"},
                        " firstName lastName c1dfd651-eff7-4113-9931-90780be76075"
                ),
                Arguments.of(
                        "Given part of user first name should return user data",
                        new String[]{"", "firstName"},
                        " firstName lastName c1dfd651-eff7-4113-9931-90780be76075"
                ),
                Arguments.of(
                        "Given part of user last name should return user data",
                        new String[]{"", "l"},
                        " firstName lastName c1dfd651-eff7-4113-9931-90780be76075"
                ),
                Arguments.of(
                        "Given part of user last name should return user data",
                        new String[]{"", "last"},
                        " firstName lastName c1dfd651-eff7-4113-9931-90780be76075"
                ),
                Arguments.of(
                        "Given part of user last name should return user data",
                        new String[]{"", "lastName"},
                        " firstName lastName c1dfd651-eff7-4113-9931-90780be76075"
                )
        );
    }

    private static Stream<Arguments> updateFailCases() {
        return Stream.of(
                Arguments.of(
                        "Given empty data array should return error",
                        new String[]{},
                        Events.ERROR + "Fill the form properly"
                ),
                Arguments.of(
                        "Given incorrect user first name should return error",
                        new String[]{"", "userID", null, "newLastName", "newPassword"},
                        Events.ERROR + "Fill the form properly"
                ),
                Arguments.of(
                        "Given incorrect user last name name should return error",
                        new String[]{"", "userID", "newFirstName", null, "newPassword"},
                        Events.ERROR + "Fill the form properly"
                ),
                Arguments.of(
                        "Given incorrect user password should return error",
                        new String[]{"", "userID", "newFirstName", "newLastName", null},
                        Events.ERROR + "Fill the form properly"
                ),
                Arguments.of(
                        "Given weak user password should return error",
                        new String[]{"", "userID", "newFirstName", "newLastName", "123"},
                        Events.ERROR + "Password doesnt contain 6 chars"
                )
        );
    }

    @Test
    public void singleToneServiceShouldIntialize() {
        assertNotNull(AppService.getAppService());
    }

    private static DataSource getDataSource() {
        JdbcDataSource dataSource = new JdbcDataSource();
        dataSource.setURL(
                "jdbc:h2:mem:default;MODE=MYSQL;DB_CLOSE_DELAY=-1;init=runscript from 'classpath:schema.sql'"
        );
        dataSource.setUser("sa");
        dataSource.setPassword("sa");
        return dataSource;
    }

    @ParameterizedTest(name = "{0}")
    @MethodSource("getLoginCases")
    public void testLogin(String annotation, String[] data, String expectedResult) {
        assertEquals(expectedResult, appService.login(data));
    }

    @ParameterizedTest(name = "{0}")
    @MethodSource("getRegisterFailCases")
    public void testRegisterFail(String annotation, String[] data, String expectedResult) {
        assertEquals(expectedResult, appService.register(data));
    }

    @Test
    public void testRegisterSuccess() {
        String[] data = new String[]{"", "superNick", "John", "Johnson", "strongPassword"};
        assertTrue(appService.register(data).startsWith(Events.SUCCESSREGISTER));
    }

    @ParameterizedTest(name = "{0}")
    @MethodSource("findUsersCases")
    public void testFindUserByPattern(String annotation, String[] data, String expectedResult) {
        assertEquals(expectedResult, appService.findUsers(data));
    }

    @Test
    public void givenMessageDataShouldSaveMessage() {
        appService.sendMessage(new String[]{"", testUserID, testUser2ID, "Ahoj", "jak", "se", "mas?"});
        String expectedMessage = "Ahoj jak se mas?";
        List<Object[]> messages = messageRepository.getMessagesByUserID(testUserID, testUser2ID);
        boolean containMessage = false;
        for (Object[] rawMessage : messages) {
            containMessage = containMessage || (rawMessage[2].equals(expectedMessage));
        }
        assertTrue(containMessage);
    }

    @Test
    public void givenFailMessageDataShouldNotSave() {
        appService.sendMessage(new String[]{"myMesage"});
        List<Object[]> messages = messageRepository.getMessagesByUserID(testUserID, testUser2ID);
        boolean containMessage = false;
        for (Object[] rawMessage : messages) {
            containMessage = containMessage || (rawMessage[2].equals("myMesage"));
        }
        assertFalse(containMessage);
    }

    @Test
    public void givenUserIDsAndMessageShouldFindThisMessage() {
        Message message = new Message();
        message.setSender(testUser1);
        message.setReceiver(testUser2);
        message.setMessage("Mam se dobre".replaceAll(" ", "/+"));
        messageRepository.save(message);
        String[] data = new String[]{"", testUserID, testUser2ID};
        String response = appService.getMessages(data);
        String expectedMessage = " " + testUserID + " " + message.getMessage();
        assertTrue(response.contains(expectedMessage));
    }

    @Test
    public void givenWrongMessageDataShouldReturnNull() {
        String[] data = new String[]{""};
        String response = appService.getMessages(data);
        assertNull(response);
    }

    @Test
    public void givenUserIDShouldReturnHisDialogues() {
        Relation relation = new Relation();
        relation.setUser1(testUser2);
        relation.setUser2(testUser1);
        relationsRepository.save(relation);

        Message message = new Message();
        message.setSender(testUser1);
        message.setReceiver(testUser2);
        message.setMessage("test");
        messageRepository.save(message);

        String[] data = new String[]{"", testUserID};
        String response = appService.getDialogues(data);
        StringBuilder sb = new StringBuilder();
        sb.append(" ").append(testUser2ID).append(" ").append(testUser2.getFirstName())
                .append(" ").append(testUser2.getLastName()).append(" ")
                .append(testUser2.getColor().getHexcode()).append(" ");
        assertTrue(response.contains(sb.toString()));
    }

    @Test
    public void givenWrongDialogueDataShouldReturnNull() {
        String[] data = new String[]{""};
        String response = appService.getDialogues(data);
        assertNull(response);
    }

    @Test
    public void givenUserIDsShouldSetMessagesAsReadForFirstOne() {
        Message message = new Message();
        message.setSender(testUser1);
        message.setReceiver(testUser2);
        message.setMessage("messageToBeRead");
        messageRepository.save(message);

        String[] data = new String[]{"", testUser2ID, testUserID};
        appService.readMessages(data);

        List<BigInteger> messages = messageRepository.getAmountUnreadMessages(testUser2ID, testUserID);
        assertEquals(BigInteger.ZERO, messages.get(0));
    }

    @Test
    public void givenWrongReadMessageDataShouldReturnNull() {
        Message message = new Message();
        message.setSender(testUser1);
        message.setReceiver(testUser2);
        message.setMessage("messageNotToBeRead");
        messageRepository.save(message);

        String[] data = new String[]{""};
        appService.readMessages(data);
        List<BigInteger> messages = messageRepository.getAmountUnreadMessages(testUser2ID, testUserID);
        assertNotEquals(BigInteger.ZERO, messages.get(0));
    }

    @Test
    public void givenUserIDsShouldBlockRelation() {
        Relation relation = new Relation();
        relation.setUser1(testUser1);
        relation.setUser2(testUser2);

        String[] data = new String[]{"", testUserID, testUser2ID};
        appService.block(data);

        List<Relation> relations = relationsRepository.getByUsers(testUserID, testUser2ID);
        assertTrue(!relations.isEmpty() && relations.get(0).getIsBlocked());
    }

    @Test
    public void givenIncorrectDataForBlockShouldNotBlock() {
        Relation relation = new Relation();
        relation.setUser1(testUser1);
        relation.setUser2(testUser2);
        relationsRepository.save(relation);

        String[] data = new String[]{""};
        appService.block(data);

        List<Relation> relations = relationsRepository.getByUsers(testUserID, testUser2ID);
        Optional<Relation> actualRelation = relations
                .stream()
                .filter(r -> r.getId().equals(relation.getId()))
                .findFirst();
        assertTrue(actualRelation.isPresent() && !actualRelation.get().getIsBlocked());
    }

    @Test
    public void givenUserIDsShouldFirstPinSecond() {
        Relation relation = new Relation();
        relation.setUser1(testUser1);
        relation.setUser2(testUser2);
        relationsRepository.save(relation);

        String[] data = new String[]{"", testUserID, testUser2ID};
        appService.pin(data);

        List<Relation> relations = relationsRepository.getByUsers(testUserID, testUser2ID);
        assertTrue(relations.get(0).getIsPinned());
    }

    @Test
    public void givenIncorrectUserIDsShouldNotPin() {
        Relation relation = new Relation();
        relation.setUser1(testUser1);
        relation.setUser2(testUser2);
        relationsRepository.save(relation);

        String[] data = new String[]{""};
        appService.pin(data);

        List<Relation> relations = relationsRepository.getByUsers(testUserID, testUser2ID);
        assertFalse(relations.get(0).getIsPinned());
    }

    @Test
    public void givenUserIDsAndPinnedRelationShouldFirstUnpinSecond() {
        Relation relation = new Relation();
        relation.setUser1(testUser1);
        relation.setUser2(testUser2);
        relation.setIsPinned(true);
        relationsRepository.save(relation);

        String[] data = new String[]{"", testUserID, testUser2ID};
        appService.unpin(data);

        List<Relation> relations = relationsRepository.getByUsers(testUserID, testUser2ID);
        assertFalse(relations.get(0).getIsPinned());
    }

    @Test
    public void givenIncorrectUserIDsShouldNotUnpin() {
        Relation relation = new Relation();
        relation.setUser1(testUser1);
        relation.setUser2(testUser2);
        relation.setIsPinned(true);
        relationsRepository.save(relation);

        String[] data = new String[]{""};
        appService.unpin(data);

        List<Relation> relations = relationsRepository.getByUsers(testUserID, testUser2ID);
        Optional<Relation> actualRelation = relations
                .stream()
                .filter(r -> r.getId().equals(relation.getId()))
                .findFirst();
        assertTrue(relation.getIsPinned());
    }

    @ParameterizedTest
    @ValueSource(booleans = {true, false})
    public void givenUserIDsAndPinStatusShouldSetPinStatus(boolean isPinned) {
        Relation relation = new Relation();
        relation.setUser1(testUser1);
        relation.setUser2(testUser2);
        relationsRepository.save(relation);

        String[] data = new String[]{"", testUserID, testUser2ID};
        appService.pin(testUserID, testUser2ID, isPinned);

        List<Relation> relations = relationsRepository.getByUsers(testUserID, testUser2ID);
        assertEquals(relations.get(0).getIsPinned(), isPinned);
    }

    @ParameterizedTest(name = "{0}")
    @MethodSource("updateFailCases")
    public void testUpdateUserFail(String annotation, String[] data, String expectedError) {
         assertEquals(expectedError, appService.update(data));
    }

    @Test
    public void giveDataToUpdateShouldUpdateUser() {
        String[] data = new String[]{testUserForUpdateID, "newFirstName", "newLastName", "newStrongPassword"};

        User expectedUser = new User();
        expectedUser.setID(UUID.fromString(testUserForUpdateID));
        expectedUser.setUserName(testUserForUpdate.getUserName());
        expectedUser.setFirstName("newFirstName");
        expectedUser.setLastName("newLastName");
        expectedUser.setPassword(BCrypt.withDefaults().hashToString(12, "newStrongPassword".toCharArray()));
        expectedUser.setColor(testUserForUpdate.getColor());

        appService.update(data);

        User actualUser = userRepository.getById(testUserForUpdateID);
        assertTrue(expectedUser.equals(actualUser));
    }

    @Test
    public void givenIDShouldReturnFullName() {
        String[] data = new String[]{"", testUserID};
        String expectedFullName = testUser1.getFirstName() + " " + testUser1.getLastName();
        String actualFullName = appService.getUserFullName(data);
        assertEquals(expectedFullName, actualFullName);
    }

    @Test
    public void givenEmptyDataReturnUnknown() {
        String[] data = new String[]{};
        String expectedFullName = "Unknown Unknown";
        String actualFullName = appService.getUserFullName(data);
        assertEquals(expectedFullName, actualFullName);
    }

    @Test
    public void givenIDShouldReturnUserColor() {
        String[] data = new String[]{"", testUserID};
        Color expectedColor = testUser1.getColor();
        Color actualColor = appService.getUserColor(data);
        assertTrue(actualColor.equals(expectedColor));
    }

    @Test
    public void givenEmptyDataReturnDefaultColour() {
        String[] data = new String[]{};
        Color expectedColor = new Color("F2C4DE");
        Color actualColor = appService.getUserColor(data);
        assertEquals(expectedColor.getHexcode(), actualColor.getHexcode());
    }

    @Test
    public void givenIDShouldReturnUser() {
        String[] data = new String[]{"", testUserID};
        User expectedUser = testUser1;
        User actualUser = appService.getUser(data);
        assertTrue(actualUser.equals(expectedUser));
    }

    @Test
    public void givenEmptyDataReturnNull() {
        String[] data = new String[]{};
        User actualUser = appService.getUser(data);
        assertNull(actualUser);
    }

    @Test
    public void givenIDShouldReturnUserDataInPartnerFormat() {
        String[] data = new String[]{"", testUserID};
        String expectedData = testUserID + " " + testUser1.getFirstName() +
                " " + testUser1.getLastName() + " " + testUser1.getColor().getHexcode();
        String actualData = appService.getPartnerData(data);
        assertEquals(expectedData, actualData);
    }

    @Test
    public void givenEmptyDataForPartnerReturnNull() {
        String[] data = new String[]{};
        String actualData = appService.getPartnerData(data);
        assertNull(actualData);
    }

}
