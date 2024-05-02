package me.chatserver;

import me.chatserver.database.ColorRepository;
import me.chatserver.entities.Color;
import me.chatserver.entities.User;
import me.chatserver.utils.HibernateUtil;
import org.dbunit.DataSourceBasedDBTestCase;
import org.dbunit.assertion.DbUnitAssert;
import org.dbunit.dataset.IDataSet;
import org.dbunit.dataset.xml.FlatXmlDataSetBuilder;
import org.dbunit.operation.DatabaseOperation;
import org.h2.jdbcx.JdbcDataSource;
import org.junit.After;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import javax.sql.DataSource;
import java.sql.PreparedStatement;
import java.util.ArrayList;
import java.util.List;
import java.util.UUID;

public class DatabaseTestBase extends DataSourceBasedDBTestCase {
    @Override
    protected DataSource getDataSource() {
        JdbcDataSource dataSource = new JdbcDataSource();
        dataSource.setURL(
                "jdbc:h2:mem:default;MODE=MYSQL;DB_CLOSE_DELAY=-1;init=runscript from 'classpath:schema.sql'"
        );
        dataSource.setUser("sa");
        dataSource.setPassword("sa");
        return dataSource;
    }

    @Override
    protected IDataSet getDataSet() throws Exception {
        return null;
    }

    protected final DbUnitAssert assertion = new DbUnitAssert();

    @BeforeEach
    public void setUP() throws Exception {
        HibernateUtil.buildSessionFactory(getDataSource());
        String sql = "DELETE FROM ACTUAL_USERS; DELETE FROM TEXT_MESSAGES; DELETE FROM USER_RELATION";
        PreparedStatement preparedStatement2 = getConnection().getConnection().prepareStatement(sql);
        preparedStatement2.execute();
    }

    public void insertUser(User user) throws Exception {
        String sql = "INSERT INTO ACTUAL_USERS(`id`, `user_name`, `first_name`, `last_name`, `password`, `color`)" +
                " VALUES(?, ?, ?, ?, ?, ?)";
        PreparedStatement preparedStatement = getConnection().getConnection().prepareStatement(sql);
        preparedStatement.setString(1, user.getID());
        preparedStatement.setString(2, user.getUserName());
        preparedStatement.setString(3, user.getFirstName());
        preparedStatement.setString(4, user.getLastName());
        preparedStatement.setString(5, user.getPassword());
        preparedStatement.setInt(6, user.getColor().getID());

        preparedStatement.execute();
    }

    protected List<User> insertUsers() throws Exception {
        List<User> users = new ArrayList<>();
        ColorRepository colorRepository = new ColorRepository();
        Color color = colorRepository.getColor(1);
        User user = new User();
        user.setID(UUID.fromString("c1dfd651-eff7-4113-9931-90780be76075"));
        user.setUserName("testUser");
        user.setFirstName("test");
        user.setLastName("test");
        user.setPassword("password");
        user.setColor(color);
        insertUser(user);
        users.add(user);

        User user2 = new User();
        user2.setID(UUID.fromString("714b24c7-e001-4710-aff9-faef3cceb911"));
        user2.setUserName("testUser1");
        user2.setFirstName("test");
        user2.setLastName("test");
        user2.setPassword("password");
        user2.setColor(color);
        insertUser(user2);
        users.add(user2);

        return users;
    }
}
