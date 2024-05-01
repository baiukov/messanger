package me.chatserver.database;

import me.chatserver.DatabaseTestBase;
import me.chatserver.entities.Color;
import me.chatserver.entities.User;
import org.dbunit.dataset.IDataSet;
import org.dbunit.dataset.ITable;
import org.dbunit.dataset.xml.FlatXmlDataSetBuilder;
import org.junit.Assert;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.ValueSource;

import java.sql.PreparedStatement;
import java.util.List;

public class UserRepositoryTest extends DatabaseTestBase {

    private final UserRepository userRepository = new UserRepository();
    private final ColorRepository colorRepository = new ColorRepository();

    private User getUser() {
        Color color = colorRepository.getColor(1);
        User user = new User();
        user.setUserName("testUser");
        user.setFirstName("test");
        user.setLastName("test");
        user.setPassword("password");
        user.setColor(color);
        return user;
    }

    @Test
    public void givenUserShouldSaveHim() throws Exception {
        User user = getUser();
        userRepository.save(user);

        IDataSet dataSet = new FlatXmlDataSetBuilder().build(getClass().getClassLoader()
                .getResourceAsStream("dataSets/userInsertSet.xml"));
        ITable expectedTable = dataSet.getTable("actual_users");
        IDataSet databaseDataSet = getConnection().createDataSet();
        ITable actualTable = databaseDataSet.getTable("actual_users");

        assertion.assertEqualsIgnoreCols(expectedTable, actualTable, new String[]{"ID"});
    }

    @Test
    public void givenUserNameShouldFetchUser() throws Exception {
        String sql = "INSERT INTO ACTUAL_USERS(`id`, `user_name`, `first_name`, `last_name`, `password`, `color`) " +
                "VALUES ('randomID', 'testUser', 'test', 'test', 'password', '1')";
        PreparedStatement preparedStatement2 = getConnection().getConnection().prepareStatement(sql);
        preparedStatement2.execute();
        String userName = "testUser";
        List<User> users = userRepository.getByUserName(userName);
        Assert.assertTrue(!users.isEmpty() && users.get(0).getUserName().equals(userName));
    }

    @Test
    public void givenUserIDShouldFetchUser() throws Exception {
        User expectedUser = getUser();
        String sql = "INSERT INTO ACTUAL_USERS(`id`, `user_name`, `first_name`, `last_name`, `password`, `color`) " +
                "VALUES (?, 'testUser', 'test', 'test', 'password', '1')";

        PreparedStatement preparedStatement = getConnection().getConnection().prepareStatement(sql);
        String id = expectedUser.getID();
        preparedStatement.setString(1, id);
        preparedStatement.execute();

        User actualUser = userRepository.getById(id);
        assertTrue(expectedUser.equals(actualUser));
    }


    @ParameterizedTest
    @ValueSource(strings = {"t", "te", "test", "testUser", "n", "na", "name", "s", "su", "surname"})
    public void givenPartOfNameShouldFindUser(String startsWith) throws Exception {
        User expectedUser = getUser();
        String sql = "INSERT INTO ACTUAL_USERS(`id`, `user_name`, `first_name`, `last_name`, `password`, `color`) " +
                "VALUES (?, 'testUser', 'Name', 'Surname', 'password', '1')";

        PreparedStatement preparedStatement = getConnection().getConnection().prepareStatement(sql);
        String id = expectedUser.getID();
        preparedStatement.setString(1, id);
        preparedStatement.execute();

        List<User> actualUsers = userRepository.getUsersByStartsWith(startsWith);
        assertTrue(!actualUsers.isEmpty() && actualUsers.get(0).equals(expectedUser));
    }

    @Test
    public void givenUpdatedUserShouldUpdate() throws Exception {
        User expectedUser = getUser();
        String sql = "INSERT INTO ACTUAL_USERS(`id`, `user_name`, `first_name`, `last_name`, `password`, `color`) " +
                "VALUES (?, 'testUser', 'Name', 'Surname', 'password', '1')";

        PreparedStatement preparedStatement = getConnection().getConnection().prepareStatement(sql);
        String id = expectedUser.getID();
        preparedStatement.setString(1, id);
        preparedStatement.execute();

        String firstName = "newFirstName";
        String lastName = "newLastName";
        String password = "newPassword";

        expectedUser.setFirstName(firstName);
        expectedUser.setLastName(lastName);
        expectedUser.setPassword(password);

        userRepository.update(id, firstName, lastName, password);

        IDataSet dataSet = new FlatXmlDataSetBuilder().build(getClass().getClassLoader()
                .getResourceAsStream("dataSets/userUpdatedSet.xml"));
        ITable expectedTable = dataSet.getTable("actual_users");
        IDataSet databaseDataSet = getConnection().createDataSet();
        ITable actualTable = databaseDataSet.getTable("actual_users");

        assertion.assertEqualsIgnoreCols(expectedTable, actualTable, new String[]{"ID"});
    }

}
