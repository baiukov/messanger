package me.chatserver.database;

import lombok.extern.slf4j.Slf4j;
import me.chatserver.database.templates.SetBlocked;
import me.chatserver.database.templates.UpdateUser;
import me.chatserver.entities.User;
import me.chatserver.database.templates.FindUserByPattern;
import me.chatserver.database.templates.FindUserByUserName;
import me.chatserver.services.SQLTemplateService;
import me.chatserver.utils.HibernateUtil;
import org.hibernate.Session;
import org.hibernate.Transaction;
import org.hibernate.query.Query;

import java.util.List;

@Slf4j
public class UserRepository {

    private final SQLTemplateService sqlTemplateService = SQLTemplateService.getSqlTemplateService();

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

    public List<User> getByUserName(String userName) {
        try (Session session = HibernateUtil.getSessionFactory().openSession()) {
            String sql = sqlTemplateService.getSQL(FindUserByUserName.class);
            Query<User> query = session.createQuery(sql, User.class);
            query.setParameter("userName", userName);
            return query.list();
        } catch (Exception e) {
            log.error("Unable to fetch user by username. Transaction error happened: " + e);
        }
        return null;
    }

    public User getById(String id) {
        // vytvoří novou relaci pro provedení transakce
        Session session = HibernateUtil.getSessionFactory().openSession();
        try {
            // pokusí se najít a vygenerovat entitu uživatele, pokud taková je, vrátí ji
            return session.get(User.class, id);
        } catch (Exception e) {
            log.error("Unable to fetch user by id. Transaction error happened: " + e);
        } finally {
            // každopadně nakonec relaci ukončí
            session.close();
        }
        return null;
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
            log.error("Unable to fetch user by pattern. Transaction error happened: " + e);
        }
        return null;
    }

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
