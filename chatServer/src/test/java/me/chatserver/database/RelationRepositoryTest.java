package me.chatserver.database;

import me.chatserver.DatabaseTestBase;
import me.chatserver.entities.Message;
import me.chatserver.entities.Relation;
import me.chatserver.entities.User;
import org.dbunit.database.IDatabaseConnection;
import org.dbunit.dataset.IDataSet;
import org.dbunit.dataset.ITable;
import org.dbunit.dataset.xml.FlatXmlDataSetBuilder;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.params.ParameterizedTest;

import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.util.List;

public class RelationRepositoryTest extends DatabaseTestBase {

    private final RelationsRepository relationsRepository = new RelationsRepository();

    @Test
    public void givenRelationShouldSaveIt() throws Exception {
        Relation relation = new Relation();
        List<User> users = insertUsers();
        System.out.println(users);
        relation.setUser1(users.get(0));
        relation.setUser2(users.get(1));

        relationsRepository.save(relation);

        IDataSet dataSet = new FlatXmlDataSetBuilder().build(getClass().getClassLoader()
                .getResourceAsStream("dataSets/RelationInsertSet.xml"));
        ITable expectedTable = dataSet.getTable("user_relation");
        IDatabaseConnection iDatabaseConnection = getConnection();
        IDataSet databaseDataSet = getConnection().createDataSet();
        ITable actualTable = databaseDataSet.getTable("user_relation");

        assertion.assertEqualsIgnoreCols(expectedTable, actualTable, new String[]{"ID", "CREATED_AT"});
    }

    @Test
    public void givenUsersShouldFindTheirRelation() throws Exception {
        List<User> users = insertUsers();
        User user1 = users.get(0);
        User user2 = users.get(1);
        Relation expectedRelation = new Relation();
        expectedRelation.setUser1(user1);
        expectedRelation.setUser2(user2);

        relationsRepository.save(expectedRelation);
        List<Relation> relations = relationsRepository.getByUsers(user1.getID(), user2.getID());

        Relation actualRelation = relations.get(0);
        assertTrue(expectedRelation.equals(actualRelation));
    }

    @Test
    public void givenUserShouldFindAllHisRelation() throws Exception {
        List<User> users = insertUsers();
        User user1 = users.get(0);
        User user2 = users.get(1);
        Relation expectedRelation1 = new Relation();
        expectedRelation1.setUser1(user1);
        expectedRelation1.setUser2(user2);
        Relation expectedRelation2 = new Relation();
        expectedRelation2.setUser1(user2);
        expectedRelation2.setUser2(user1);

        relationsRepository.save(expectedRelation1);
        relationsRepository.save(expectedRelation2);
        List<Relation> actualRelations = relationsRepository.getByUser(user1.getID());

        assertEquals(List.of(expectedRelation1, expectedRelation2).size(), actualRelations.size());
    }

    @Test
    public void givenRelationShouldPin() throws Exception {
        List<User> users = insertUsers();
        User user1 = users.get(0);
        User user2 = users.get(1);
        Relation relation = new Relation();
        relation.setUser1(user1);
        relation.setUser2(user2);

        relationsRepository.save(relation);
        relationsRepository.pin(user1.getID(), user2.getID(), true);

        String sql = "SELECT IS_PINNED FROM USER_RELATION WHERE USER_1 = ? AND USER_2 = ?";
        PreparedStatement preparedStatement = getConnection().getConnection().prepareStatement(sql);
        preparedStatement.setString(1, user1.getID());
        preparedStatement.setString(2, user2.getID());
        ResultSet resultSet = preparedStatement.executeQuery();
        resultSet.next();
        boolean isPinned = resultSet.getBoolean("is_pinned");

        assertTrue(isPinned);
    }

    @Test
    public void givenRelationShouldBlock() throws Exception {
        List<User> users = insertUsers();
        User user1 = users.get(0);
        User user2 = users.get(1);
        Relation relation = new Relation();
        relation.setUser1(user1);
        relation.setUser2(user2);

        relationsRepository.save(relation);
        relationsRepository.block(user1.getID(), user2.getID(), true);

        String sql = "SELECT IS_BLOCKED FROM USER_RELATION WHERE USER_1 = ? AND USER_2 = ?";
        PreparedStatement preparedStatement = getConnection().getConnection().prepareStatement(sql);
        preparedStatement.setString(1, user1.getID());
        preparedStatement.setString(2, user2.getID());
        ResultSet resultSet = preparedStatement.executeQuery();
        resultSet.next();
        boolean isPinned = resultSet.getBoolean("is_blocked");

        assertTrue(isPinned);
    }

}
