package me.chatserver.database;

import me.chatserver.DatabaseTestBase;
import me.chatserver.entities.Message;
import me.chatserver.entities.User;
import org.dbunit.database.IDatabaseConnection;
import org.dbunit.dataset.IDataSet;
import org.dbunit.dataset.ITable;
import org.dbunit.dataset.xml.FlatXmlDataSetBuilder;
import org.junit.jupiter.api.Test;

import java.math.BigInteger;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.util.List;

public class MessageRepositoryTest extends DatabaseTestBase {

    private final MessageRepository messageRepository = new MessageRepository();

    private Message getMessage(List<User> users) throws Exception {
        Message message = new Message();
        message.setSender(users.get(0));
        message.setReceiver(users.get(1));
        message.setMessage("message text");
        message.setIsRead(false);

        return message;
    }

    @Test
    public void givenMessageShouldSaveIt() throws Exception {
        List<User> users = insertUsers();
        Message message = getMessage(users);
        messageRepository.save(message);

        IDataSet dataSet = new FlatXmlDataSetBuilder().build(getClass().getClassLoader()
                .getResourceAsStream("dataSets/messageInsertSet.xml"));
        ITable expectedTable = dataSet.getTable("text_messages");
        IDataSet databaseDataSet = getConnection().createDataSet();
        ITable actualTable = databaseDataSet.getTable("text_messages");

        assertion.assertEqualsIgnoreCols(expectedTable, actualTable, new String[]{"ID", "CREATED_AT"});
    }

    @Test
    public void givenUsersShouldFindTheirMessage() throws Exception {
        List<User> users = insertUsers();
        User user1 = users.get(0);
        User user2 = users.get(1);

        messageRepository.save(getMessage(users));
        List<Object[]> messages = messageRepository.getMessagesByUserID(user1.getID(), user2.getID());

        Object[] message = messages.get(0);
        String text = (String) message[2];
        assertEquals(text, "message text");
    }

    @Test
    public void givenUsersShouldFindTheirLastMessage() throws Exception {
        List<User> users = insertUsers();
        User user1 = users.get(0);
        User user2 = users.get(1);

        Message message2 = new Message();
        message2.setSender(users.get(0));
        message2.setReceiver(users.get(1));
        message2.setMessage("second message");
        message2.setIsRead(false);

        messageRepository.save(getMessage(users));
        messageRepository.save(message2);
        List<String> messages = messageRepository.getLastMessagesByUsers(user1.getID(), user2.getID());

        String text = (String) messages.get(0);
        assertEquals(text, "second message");
    }

    @Test
    public void givenUsersShouldReturnAmountOfUnreadMessages() throws Exception {
        List<User> users = insertUsers();
        User user1 = users.get(0);
        User user2 = users.get(1);

        Message message2 = new Message();
        message2.setSender(users.get(0));
        message2.setReceiver(users.get(1));
        message2.setMessage("second message");
        message2.setIsRead(false);

        messageRepository.save(getMessage(users));
        messageRepository.save(message2);
        List<BigInteger> amounts = messageRepository.getAmountUnreadMessages(user2.getID(), user1.getID());

        BigInteger amount = amounts.get(0);
        assertEquals(BigInteger.TWO, amount);
    }

    @Test
    public void givenMessageShouldSetAsRead() throws Exception {
        Message message = getMessage(insertUsers());
        messageRepository.save(message);
        messageRepository.setMessagesRead(message.getReceiver().getID(), message.getSender().getID());

        String sql = "SELECT * FROM TEXT_MESSAGES WHERE ID = ?";

        PreparedStatement preparedStatement = getConnection().getConnection().prepareStatement(sql);
        preparedStatement.setString(1, message.getId());
        ResultSet resultSet = preparedStatement.executeQuery();
        IDatabaseConnection iDatabaseConnection =  getConnection();
        resultSet.next();
        boolean isRead = resultSet.getBoolean("is_read");

        assertTrue(isRead);
    }


}
