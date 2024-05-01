package me.chatserver.utils;

import lombok.extern.slf4j.Slf4j;
import me.chatserver.entities.Color;
import me.chatserver.entities.Message;
import me.chatserver.entities.Relation;
import me.chatserver.entities.User;
import org.hibernate.SessionFactory;
import org.hibernate.boot.MetadataSources;
import org.hibernate.boot.registry.StandardServiceRegistry;
import org.hibernate.boot.registry.StandardServiceRegistryBuilder;
import org.hibernate.cfg.Configuration;

import javax.sql.DataSource;

@Slf4j
public class HibernateUtil {
    private static SessionFactory sessionFactory = buildSessionFactory();

    private static SessionFactory buildSessionFactory() {
        try {
            return new Configuration().configure().buildSessionFactory();
        } catch (Throwable ex) {
            log.error("Initial SessionFactory creation failed." + ex.getMessage());
            throw new ExceptionInInitializerError(ex);
        }
    }

    public static void buildSessionFactory(DataSource dataSource) {
        try {
            StandardServiceRegistry standardRegistry = new StandardServiceRegistryBuilder()
                    .applySetting("hibernate.connection.datasource", dataSource)
                    .build();

            MetadataSources metadataSources = new MetadataSources(standardRegistry);
            metadataSources.addAnnotatedClass(User.class);
            metadataSources.addAnnotatedClass(Color.class);
            metadataSources.addAnnotatedClass(Relation.class);
            metadataSources.addAnnotatedClass(Message.class);

            sessionFactory = metadataSources.buildMetadata().buildSessionFactory();

        } catch (Throwable ex) {
            log.error("Initial SessionFactory creation failed." + ex.getMessage());
            throw new ExceptionInInitializerError(ex);
        }
    }

    public static SessionFactory getSessionFactory() {
        return sessionFactory;
    }
}
