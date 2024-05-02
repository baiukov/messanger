package me.chatserver.database;

import lombok.extern.slf4j.Slf4j;
import me.chatserver.entities.Color;
import me.chatserver.services.SQLTemplateService;
import me.chatserver.utils.HibernateUtil;
import org.hibernate.Session;

import javax.persistence.criteria.CriteriaBuilder;
import javax.persistence.criteria.CriteriaQuery;
import java.util.List;

/**
 * Třída ColorRepository - je třída repositáře barev, která se zabývá operováním s jejích uložištěm.
 * V této třídě jsou definováné metody komunikace s uložištěm, které budou zajištěny transakcemi
 * knihovny Hibernate
 *
 * @author Aleksei Baiukov
 * @version 02.05.2024
 */
@Slf4j
public class ColorRepository {

    public int getAvailableAmount() {
        try (Session session = HibernateUtil.getSessionFactory().openSession()) {
            CriteriaBuilder builder = session.getCriteriaBuilder();
            CriteriaQuery<Color> criteria = builder.createQuery(Color.class);
            criteria.from(Color.class);
            List<Color> data = session.createQuery(criteria).getResultList();
            return data.size();
        } catch (Exception e) {
            log.error("Unable to get available amount of colours. Exception occurred: " + e);
        }
        return 0;
    }

    public Color getColor(int id) {
        Session session = HibernateUtil.getSessionFactory().openSession();
        try {
            // pokusí se najít a vygenerovat entitu uživatele, pokud taková je, vrátí ji
            return session.get(Color.class, id);
        } catch (Exception e) {
          log.error("Unabel to get colour. Exception occurred: " + e);
        } finally {
            session.close();
        }
        return null;
    }
}
