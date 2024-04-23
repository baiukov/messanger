package me.chatserver.repositories;

import me.chatserver.entities.User;
import me.chatserver.utils.HibernateUtil;
import org.hibernate.Session;
import org.hibernate.Transaction;

public class UserRepository {

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
}
