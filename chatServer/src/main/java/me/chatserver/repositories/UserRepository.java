package me.chatserver.repositories;

import me.chatserver.entities.User;
import me.chatserver.repositories.templates.FindUserByPattern;
import me.chatserver.repositories.templates.FindUserByUserName;
import me.chatserver.services.SQLTemplateService;
import me.chatserver.utils.HibernateUtil;
import org.hibernate.Session;
import org.hibernate.Transaction;
import org.hibernate.query.Query;

import java.util.List;

public class UserRepository {

    private final SQLTemplateService sqlTemplateService = SQLTemplateService.getSqlTemplateService();

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

    public User getById(String id) {
        // vytvoří novou relaci pro provedení transakce
        Session session = HibernateUtil.getSessionFactory().openSession();
        try {
            // pokusí se najít a vygenerovat entitu uživatele, pokud taková je, vrátí ji
            return session.get(User.class, id);
        } finally {
            // každopadně nakonec relaci ukončí
            session.close();
        }
    }

    public List<User> getUsersByStartsWith(String startsWith) {
        try (Session session = HibernateUtil.getSessionFactory().openSession()) {
            String sql = sqlTemplateService.getSQL(FindUserByPattern.class);
            Query<User> query = session.createQuery(sql, User.class);
            String pattern = startsWith + "%";
            query.setParameter("pattern", pattern);
            query.setMaxResults(5);
            return query.list();
        } catch (Exception e) {
            e.printStackTrace();
        }
        return null;
    }
}
