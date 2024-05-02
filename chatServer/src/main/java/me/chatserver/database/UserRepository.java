package me.chatserver.database;

import lombok.extern.slf4j.Slf4j;
import me.chatserver.database.templates.FindUserByPattern;
import me.chatserver.database.templates.FindUserByUserName;
import me.chatserver.database.templates.UpdateUser;
import me.chatserver.entities.User;
import me.chatserver.services.SQLTemplateService;
import me.chatserver.utils.HibernateUtil;
import org.hibernate.Session;
import org.hibernate.Transaction;
import org.hibernate.query.Query;

import java.util.ArrayList;
import java.util.List;

/**
 * Třída RelationsRepository - je třída repositáře uživatelů, která se zabývá operováním s jejích uložištěm.
 * V této třídě jsou definováné metody komunikace s uložištěm, které budou zajištěny transakcemi
 * knihovny Hibernate
 *
 * @author Aleksei Baiukov
 * @version 02.05.2024
 */
@Slf4j
public class UserRepository {

    /**
     * Uložení služby pro vyhledání šablon SQL příkazů
     */
    private final SQLTemplateService sqlTemplateService = SQLTemplateService.getSqlTemplateService();

    /**
     * Metoda uložení nové entity uživatele. Otevří novou sessinu, vytvoří v ní transakci, pokusí se uložit
     * předanou instanci uživatele, pokud se ji to podaří, provede transakci, jinak smaže změny a zavře
     * sessionu.
     *
     * @param user instance uživatele k ukladání
     */
    public void save(User user) {
        Session session = HibernateUtil.getSessionFactory().openSession();
        Transaction transaction = null;
        try {
            transaction = session.beginTransaction();
            session.save(user);
            transaction.commit();
            log.info("New user has been created " + user);
        } catch (Exception e) {
            if (transaction != null) {
                transaction.rollback();
            }
            log.error("Unable to save user. Transaction error happened: " + e);
        } finally {
            session.close();
        }
    }

    /**
     * Metoda pro získání seznamu uživatelů podle uživatelského jména.
     * Otevří novou sessinu, pokusí se vyhledat uživatele, pokud se ji to podaří, vrátí seznam,
     * jinak zavře sessionu.
     *
     * @param userName uživatelské jméno
     * @return seznam uživatelů
     */
    public List<User> getByUserName(String userName) {
        try (Session session = HibernateUtil.getSessionFactory().openSession()) {
            String sql = sqlTemplateService.getSQL(FindUserByUserName.class);
            Query<User> query = session.createQuery(sql, User.class);
            query.setParameter("userName", userName);
            return query.list();
        } catch (Exception e) {
            log.error("Unable to fetch user by username. Transaction error happened: " + e);
        }
        return new ArrayList<>();
    }

    /**
     * Metoda pro získání seznamu uživatelů podle identifikačního čísla.
     * Otevří novou sessinu, pokusí se vyhledat uživatele, pokud se ji to podaří, vrátí instanci uživatele,
     * jinak zavře sessionu.
     *
     * @param id identifikační číslo
     * @return uživatel nebo null
     */
    public User getById(String id) {
        Session session = HibernateUtil.getSessionFactory().openSession();
        try {
            return session.get(User.class, id);
        } catch (Exception e) {
            log.error("Unable to fetch user by id. Transaction error happened: " + e);
        } finally {
            session.close();
        }
        return null;
    }

    /**
     * Metoda pro získání seznamu uživatelů podle části jména, příjmení nebo uživatelského jména.
     * Otevří novou sessinu, pokusí se vyhledat uživatele, pokud se ji to podaří, vrátí instanci uživatele,
     * jinak zavře sessionu.
     *
     * @param startsWith pattern pro vyhledání
     * @return seznam uživatelů podle patternu
     */
    public List<User> getUsersByStartsWith(String startsWith) {
        try (Session session = HibernateUtil.getSessionFactory().openSession()) {
            String sql = sqlTemplateService.getSQL(FindUserByPattern.class);
            Query<User> query = session.createQuery(sql, User.class);
            String pattern = startsWith + "%";
            query.setParameter("pattern", pattern);
            query.setMaxResults(5);
            return query.list();
        } catch (Exception e) {
            log.error("Unable to fetch user by pattern. Transaction error happened: " + e);
        }
        return new ArrayList<>();
    }

    /**
     * Metoda obnovení dat v entitě uživatele. Otevří novou sessinu, vytvoří v ní transakci, pokusí se obnovit
     * uživatele podle předaných dat, pokud se ji to podaří, provede transakci, jinak smaže změny a zavře
     * sessionu.
     *
     * @param id identifikační číslo
     * @param name nové jméno
     * @param surname nové přijmení
     * @param password nové heslo
     */
    public void update(String id, String name, String surname, String password) {
        Session session = HibernateUtil.getSessionFactory().openSession();
        Transaction transaction = null;
        try {
            transaction = session.beginTransaction();
            String hsql = sqlTemplateService.getSQL(UpdateUser.class);
            session.createQuery( hsql )
                    .setParameter("id", id)
                    .setParameter("name", name)
                    .setParameter("surname", surname)
                    .setParameter("password", password)
                    .executeUpdate();
            transaction.commit();
            log.info("User " + id + " has been successfully updated. " +
                    "New first name: " + name + "New last name " + surname);
        } catch (RuntimeException e) {
            if (transaction != null && transaction.isActive()) {
                transaction.rollback();
            }
            log.error("Unable to update user. Transaction error happened: " + e);
        } finally {
            session.close();
        }
    }
}
