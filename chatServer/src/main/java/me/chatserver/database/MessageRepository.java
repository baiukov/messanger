package me.chatserver.database;

import lombok.extern.slf4j.Slf4j;
import me.chatserver.database.templates.FindLastMessageByUsers;
import me.chatserver.database.templates.FindMessagesByUser;
import me.chatserver.database.templates.GetAmountUnreadMessages;
import me.chatserver.database.templates.SetMessagesRead;
import me.chatserver.entities.Message;
import me.chatserver.services.SQLTemplateService;
import me.chatserver.utils.HibernateUtil;
import org.hibernate.Session;
import org.hibernate.Transaction;

import java.math.BigInteger;
import java.util.List;

/**
 * Třída MessageRepository - je třída repositáře uživatelských zpráv, která se zabývá operováním s jejích uložištěm.
 * V této třídě jsou definováné metody komunikace s uložištěm, které budou zajištěny transakcemi
 * knihovny Hibernate
 *
 * @author Aleksei Baiukov
 * @version 02.05.2024
 */
@Slf4j
public class MessageRepository {

    /**
     * Uložení služby pro vyhledání šablon SQL příkazů
     */
    SQLTemplateService sqlTemplateService = SQLTemplateService.getSqlTemplateService();

    /**
     * Metoda uložení nové entity zprávy. Otevří novou sessinu, vytvoří v ní transakci, pokusí se uložit
     * předanou instanci zprávy, pokud se ji to podaří, provede transakci, jinak smaže změny a zavře
     * sessionu.
     *
     * @param message instance uživatelské zprávy
     */
    public void save(Message message) {
        Session session = HibernateUtil.getSessionFactory().openSession();
        Transaction transaction = null;
        try {
            transaction = session.beginTransaction();
            session.save(message);
            transaction.commit();
            log.info("New message has been created " + message);
        } catch (Exception e) {
            if (transaction != null) {
                transaction.rollback();
            }
            log.error("Unable to create message. Exception occurred " + e);
        } finally {
            session.close();
        }
    }

    /**
     * Metoda pro získání všech zpráv mezi uživateli podle jejich identifikačních čísel.
     * Otevří novou sessinu, pokusí se vyhledat uživatele, pokud se ji to podaří, vrátí seznam objektů reprezentujících
     * zprávy, jinak zavře sessionu.
     *
     * @param id identifikační číslo
     * @param partnerID identifikační číslo partneru
     * @return seznam objektu reprezentujících zprávu
     */
    public List<Object[]> getMessagesByUserID(String id, String partnerID) {
        try (Session session = HibernateUtil.getSessionFactory().openSession()) {
            String sql = sqlTemplateService.getSQL(FindMessagesByUser.class);
            return (List<Object[]>) session.createSQLQuery(sql)
                    .setParameter("id", id)
                    .setParameter("partnerID", partnerID)
                    .list();
        } catch (Exception e) {
            log.error("Unable to get messages by user id. Exception occurred: " + e);
        }
        return null;
    }

    /**
     * Metoda pro získání poslední zprávy mezi uživateli podle jejich identifikačních čísel.
     * Otevří novou sessinu, pokusí se vyhledat uživatele, pokud se ji to podaří, vrátí obsah poslední zprávy,
     * jinak sessionu zavře
     *
     * @param id identifikační číslo
     * @param partnerID identifikační číslo partneru
     * @return obsah poslední zprávy nebo null
     */
    public List<String> getLastMessagesByUsers(String id, String partnerID) {
        try (Session session = HibernateUtil.getSessionFactory().openSession()) {
            String sql = sqlTemplateService.getSQL(FindLastMessageByUsers.class);
            return session.createSQLQuery(sql)
                    .setParameter("id", id)
                    .setParameter("partnerID", partnerID)
                    .list();
        } catch (Exception e) {
            log.error("Unable to get messages by 2 users. Exception occurred: " + e);
        }
        return null;
    }

    /**
     * Metoda pro získání počtu všech nepřečtených zpráv mezi uživateli podle jejich identifikačních čísel.
     * Otevří novou sessinu, pokusí se vyhledat uživatele, pokud se ji to podaří, vrátí seznam obsahující číslo počtu
     * nepřečtených zpráv, jinak zavře sessionu.
     *
     * @param id identifikační číslo
     * @param partnerID identifikační číslo partneru
     * @return počet zpráv nebo null
     */
    public List<BigInteger> getAmountUnreadMessages(String id, String partnerID) {
        try (Session session = HibernateUtil.getSessionFactory().openSession()) {
            String sql = sqlTemplateService.getSQL(GetAmountUnreadMessages.class);
            return (List<BigInteger>) session.createSQLQuery(sql)
                    .setParameter("userID", id)
                    .setParameter("partnerID", partnerID)
                    .list();
        } catch (Exception e) {
            log.error("Unable to get amount of unread messages. Exception occurred: " + e);
        }
        return null;
    }

    /**
     * Metoda pro nastavení všech zpráv získaných jedným uživatelem v chatu s druhým jako přečtených.
     * Otevří novou sessinu, vytvoří v ní transakci, pokusí se obnovit všechny zprávy podle
     * předaných identifikačních čísel, pokud se ji to podaří, provede transakci, jinak smaže změny a zavře
     * sessionu.
     *
     * @param id identifikační číslo
     * @param partnerID identifikační číslo partneru
     */
    public void setMessagesRead(String id, String partnerID) {
        Session session = HibernateUtil.getSessionFactory().openSession();
        Transaction transaction = null;
        try {
            transaction = session.beginTransaction();
            String hsql = sqlTemplateService.getSQL(SetMessagesRead.class);
            session.createQuery( hsql )
                    .setParameter("id", id)
                    .setParameter("partnerID", partnerID)
                    .executeUpdate();
            transaction.commit();
            log.info("Message " + id + " has been set as read");
        } catch (RuntimeException e) {
            if (transaction != null && transaction.isActive()) {
                transaction.rollback();
            }
            log.error("Unable to set message " + id + " as read. Exception occurred: " + e);
        } finally {
            session.close();
        }
    }
}
