package me.chatserver.database;

import lombok.extern.slf4j.Slf4j;
import me.chatserver.database.templates.FindRelationByUsers;
import me.chatserver.database.templates.FindRelationForUserID;
import me.chatserver.database.templates.SetBlocked;
import me.chatserver.database.templates.SetPinned;
import me.chatserver.entities.Relation;
import me.chatserver.services.SQLTemplateService;
import me.chatserver.utils.HibernateUtil;
import org.hibernate.Session;
import org.hibernate.Transaction;
import org.hibernate.query.Query;

import java.util.ArrayList;
import java.util.List;

/**
 * Třída RelationsRepository - je třída repositáře uživatelských vzrahů, která se zabývá operováním s jejích uložištěm.
 * V této třídě jsou definováné metody komunikace s uložištěm, které budou zajištěny transakcemi
 * knihovny Hibernate
 *
 * @author Aleksei Baiukov
 * @version 02.05.2024
 */
@Slf4j
public class RelationsRepository {

    /**
     * Uložení služby pro vyhledání šablon SQL příkazů
     */
    private final SQLTemplateService sqlTemplateService = SQLTemplateService.getSqlTemplateService();

    /**
     * Metoda uložení nové entity vztahu. Otevří novou sessinu, vytvoří v ní transakci, pokusí se uložit
     * předanou instanci vztahu, pokud se ji to podaří, provede transakci, jinak smaže změny a zavře
     * sessionu.
     *
     * @param relation instance uživatelského vztahu
     */
    public void save(Relation relation) {
        Session session = HibernateUtil.getSessionFactory().openSession();
        Transaction transaction = null;
        try {
            transaction = session.beginTransaction();
            System.out.println(session.save(relation));
            transaction.commit();
            log.info("New relation has been created " + relation);
        } catch (Exception e) {
            if (transaction != null) {
                transaction.rollback();
            }
            log.error("Exception occurred on relation saving " + e);
        } finally {
            session.close();
        }
    }

    /**
     * Metoda pro získání vztahu mezi uživateli podle jejich identifikačních čísel.
     * Otevří novou sessinu, pokusí se vyhledat vztah, pokud se ji to podaří, vrátí seznam obsahující tento vztah,
     * jinak zavře sessionu.
     *
     * @param user1ID iniciátor vztahu
     * @param user2ID druhý uživatel ve vztahu
     * @return seznam vztahů
     */
    public List<Relation> getByUsers(String user1ID, String user2ID) {
        try (Session session = HibernateUtil.getSessionFactory().openSession()) {
            String sql = sqlTemplateService.getSQL(FindRelationByUsers.class);
            Query<Relation> query = session.createQuery(sql, Relation.class);
            query.setParameter("user1", user1ID);
            query.setParameter("user2", user2ID);
            return query.list();
        } catch (Exception e) {
            log.error("Unable to fetch relation by 2 users. Exception occurred " + e);
        }
        return new ArrayList<>();
    }

    /**
     * Metoda pro získání všech vztahů spojených s uživatelem podle jeho identifikačního čísla.
     * Otevří novou sessinu, pokusí se vyhledat vztahy, pokud se ji to podaří, vrátí seznam vztahů,
     * jinak zavře sessionu.
     *
     * @param id identifikační číslo uživatele
     * @return seznam vztahů
     */
    public List<Relation> getByUser(String id) {
        try (Session session = HibernateUtil.getSessionFactory().openSession()) {
            String sql = sqlTemplateService.getSQL(FindRelationForUserID.class);
            Query<Relation> query = session.createQuery(sql, Relation.class);
            query.setParameter("id", id);
            return query.list();
        } catch (Exception e) {
            log.error("Unable to fetch relation for 1 user. Exception occurred " + e);
        }
        return new ArrayList<>();
    }

    /**
     * Metoda pro připnutí vztahu (chatu) mezi uživateli.
     * Otevří novou sessinu, vytvoří v ní transakci, pokusí se obnovit instanci vztahu,
     * pokud se ji to podaří, provede transakci, jinak smaže změny a zavře sessionu.
     *
     * @param userID identifikační číslo uživatele
     * @param partnerID identifikační číslo partnera
     * @param isPinned jestli se vztah má připnout nebo odepnout
     */
    public void pin(String userID, String partnerID, boolean isPinned) {
        Session session = HibernateUtil.getSessionFactory().openSession();
        Transaction transaction = null;
        try {
            transaction = session.beginTransaction();
            String hsql = sqlTemplateService.getSQL(SetPinned.class);
            session.createQuery( hsql )
                    .setParameter("userID", userID)
                    .setParameter("partnerID", partnerID)
                    .setParameter("isPinned", isPinned)
                    .executeUpdate();
            transaction.commit();
            log.info("User " + userID + " successfully " + (isPinned ? "pinned" : "unpinned") + partnerID);
        } catch (RuntimeException e) {
            if (transaction != null && transaction.isActive()) {
                transaction.rollback();
            }
            log.error("Unable to " + (isPinned ? "pin" : "unpin") + " user. Exception occurred: " + e );
        } finally {
            session.close();
        }
    }

    /**
     * Metoda pro zablokování vztahu (chatu) mezi uživateli.
     * Otevří novou sessinu, vytvoří v ní transakci, pokusí se obnovit instanci vztahu, pokud se ji to podaří,
     * provede transakci, jinak smaže změny a zavře sessionu.
     *
     * @param userID identifikační číslo uživatele
     * @param partnerID identifikační číslo partnera
     * @param isBlocked jestli se vztah má zablokovat nebo odblokovat
     */
    public void block(String userID, String partnerID, boolean isBlocked) {
        Session session = HibernateUtil.getSessionFactory().openSession();
        Transaction transaction = null;
        try {
            transaction = session.beginTransaction();
            String hsql = sqlTemplateService.getSQL(SetBlocked.class);
            session.createQuery( hsql )
                    .setParameter("userID", userID)
                    .setParameter("partnerID", partnerID)
                    .setParameter("isBlocked", isBlocked)
                    .executeUpdate();
            transaction.commit();
            log.info("User " + userID + " successfully " + (isBlocked ? "blocked" : "unblocked") + partnerID);
        } catch (RuntimeException e) {
            if (transaction != null && transaction.isActive()) {
                transaction.rollback();
            }
            log.error("Unable to " + (isBlocked ? "block" : "unblock") + " user. Exception occurred: " + e );
        } finally {
            session.close();
        }
    }
}
