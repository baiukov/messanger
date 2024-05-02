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

/**
 * Třída HibernateUtil - je pomocní třída pro knihovnu Hibernate, nastaví konfiguraci práce s databází
 *
 * @author Aleksei Baiukov
 * @version 02.05.2024
 */
@Slf4j
public class HibernateUtil {

    /**
     *  Uložení továrny pro produkci sessinů
     */
    private static SessionFactory sessionFactory = buildSessionFactory();

    /**
     *  Metoda pro ytvoření továrny podle konfiguráčního souboru
     *
     */
    private static SessionFactory buildSessionFactory() {
        try {
            return new Configuration().configure().buildSessionFactory();
        } catch (Throwable ex) {
            log.error("Initial SessionFactory creation failed." + ex.getMessage());
            throw new ExceptionInInitializerError(ex);
        }
    }

    /**
     *  Vytvoření továrny podle datové instance
     */
    public static void buildSessionFactory(DataSource dataSource) {
        try {
            StandardServiceRegistry standardRegistry = new StandardServiceRegistryBuilder()
                    .applySetting("hibernate.connection.datasource", dataSource)
                    .build();

            // přidání entit ke scanování
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

    /**
     *  Metoda pro získání připravené továrny
     *
     * @return továrna pro generaci sessinů
     */
    public static SessionFactory getSessionFactory() {
        return sessionFactory;
    }
}
