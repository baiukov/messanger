package me.chatserver.database;

import me.chatserver.database.templates.FindMessagesByUser;
import me.chatserver.database.templates.FindUserByUserName;
import me.chatserver.entities.Message;
import me.chatserver.entities.User;
import me.chatserver.services.SQLTemplateService;
import me.chatserver.utils.HibernateUtil;
import org.hibernate.Session;
import org.hibernate.Transaction;
import org.hibernate.query.Query;

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

    public List<Object[]> getMessagesByUserID(String id) {
        try (Session session = HibernateUtil.getSessionFactory().openSession()) {
            String sql = sqlTemplateService.getSQL(FindMessagesByUser.class);
            return (List<Object[]>) session.createSQLQuery(sql).setParameter("id", id).list();
        } catch (Exception e) {
            e.printStackTrace();
        }
        return null;
    }
}
