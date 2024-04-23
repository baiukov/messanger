package me.chatserver.repositories;

import me.chatserver.entities.User;
import me.chatserver.repositories.templates.FindUserByUserName;
import me.chatserver.services.SQLTemplateService;
import me.chatserver.utils.HibernateUtil;
import org.hibernate.Session;
import org.hibernate.Transaction;
import org.hibernate.query.Query;

import java.util.List;

public class UserRepository {

    private final SQLTemplateService sqlTemplateService = new SQLTemplateService();

    public String save(User user) {
        Session session = HibernateUtil.getSessionFactory().openSession();
        Transaction transaction = null;
        String generatedId = null;
        try {
            transaction = session.beginTransaction();
            session.save(user);
            transaction.commit();
        } catch (Exception e) {
            if (transaction != null) {
                transaction.rollback();
            }
            e.printStackTrace();
        } finally {
            session.close();
        }
        return generatedId;
    }

    public List<User> getByUserName(String userName) {
        try (Session session = HibernateUtil.getSessionFactory().openSession()) {
            String sql = sqlTemplateService.getSQL(FindUserByUserName.class);
            Query<User> query = session.createQuery(sql, User.class);
            query.setParameter("userName", userName);
            return query.list();
        } catch (Exception e) {
            e.printStackTrace();
        }
        return null;
    }
}
