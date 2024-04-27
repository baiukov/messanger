package me.chatserver.repositories;

import me.chatserver.entities.Color;
import me.chatserver.entities.User;
import me.chatserver.repositories.templates.CountAvailableColors;
import me.chatserver.repositories.templates.FindUserByUserName;
import me.chatserver.services.SQLTemplateService;
import me.chatserver.utils.HibernateUtil;
import org.hibernate.Session;
import org.hibernate.query.Query;

import javax.persistence.criteria.CriteriaBuilder;
import javax.persistence.criteria.CriteriaQuery;
import java.util.List;

public class ColorRepository {

    private final SQLTemplateService sqlTemplateService = SQLTemplateService.getSqlTemplateService();

    public int getAvailableAmount() {
        try (Session session = HibernateUtil.getSessionFactory().openSession()) {
            CriteriaBuilder builder = session.getCriteriaBuilder();
            CriteriaQuery<Color> criteria = builder.createQuery(Color.class);
            criteria.from(Color.class);
            List<Color> data = session.createQuery(criteria).getResultList();
            return data.size();
        } catch (Exception e) {
            e.printStackTrace();
        }
        return 0;
    }

    public Color getColor(int id) {
        try (Session session = HibernateUtil.getSessionFactory().openSession()) {
            // pokusí se najít a vygenerovat entitu uživatele, pokud taková je, vrátí ji
            return session.get(Color.class, id);
        }
        // každopadně nakonec relaci ukončí
    }
}
