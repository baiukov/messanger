package me.chatserver.database;

import lombok.extern.slf4j.Slf4j;
import me.chatserver.database.templates.*;
import me.chatserver.entities.Relation;
import me.chatserver.services.SQLTemplateService;
import me.chatserver.utils.HibernateUtil;
import org.hibernate.Session;
import org.hibernate.Transaction;
import org.hibernate.query.Query;

import java.util.ArrayList;
import java.util.List;

@Slf4j
public class RelationsRepository {

    private final SQLTemplateService sqlTemplateService = SQLTemplateService.getSqlTemplateService();

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
