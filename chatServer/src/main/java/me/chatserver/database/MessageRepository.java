package me.chatserver.database;

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

public class MessageRepository {

    SQLTemplateService sqlTemplateService = SQLTemplateService.getSqlTemplateService();

    public void save(Message message) {
        Session session = HibernateUtil.getSessionFactory().openSession();
        Transaction transaction = null;
        try {
            transaction = session.beginTransaction();
            session.save(message);
            transaction.commit();
        } catch (Exception e) {
            if (transaction != null) {
                transaction.rollback();
            }
            e.printStackTrace();
        } finally {
            session.close();
        }
    }

    public List<Object[]> getMessagesByUserID(String id, String partnerID) {
        try (Session session = HibernateUtil.getSessionFactory().openSession()) {
            String sql = sqlTemplateService.getSQL(FindMessagesByUser.class);
            return (List<Object[]>) session.createSQLQuery(sql)
                    .setParameter("id", id)
                    .setParameter("partnerID", partnerID)
                    .list();
        } catch (Exception e) {
            e.printStackTrace();
        }
        return null;
    }

    public List<String> getLastMessagesByUsers(String id, String partnerID) {
        try (Session session = HibernateUtil.getSessionFactory().openSession()) {
            String sql = sqlTemplateService.getSQL(FindLastMessageByUsers.class);
            return session.createSQLQuery(sql)
                    .setParameter("id", id)
                    .setParameter("partnerID", partnerID)
                    .list();
        } catch (Exception e) {
            e.printStackTrace();
        }
        return null;
    }

    public List<BigInteger> getAmountUnreadMessages(String id, String partnerID) {
        try (Session session = HibernateUtil.getSessionFactory().openSession()) {
            String sql = sqlTemplateService.getSQL(GetAmountUnreadMessages.class);
            return (List<BigInteger>) session.createSQLQuery(sql)
                    .setParameter("userID", id)
                    .setParameter("partnerID", partnerID)
                    .list();
        } catch (Exception e) {
            e.printStackTrace();
        }
        return null;
    }

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
        } catch (RuntimeException e) {
            if (transaction != null && transaction.isActive()) {
                transaction.rollback();
            }
            throw e; // or display error message
        } finally {
            session.close();
        }
    }
}
