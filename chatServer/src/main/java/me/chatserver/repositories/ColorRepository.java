package me.chatserver.repositories;

import me.chatserver.entities.Color;
import me.chatserver.entities.User;
import me.chatserver.repositories.templates.CountAvailableColors;
import me.chatserver.repositories.templates.FindUserByUserName;
import me.chatserver.services.SQLTemplateService;
import me.chatserver.utils.HibernateUtil;
import org.hibernate.Session;
import org.hibernate.query.Query;

public class ColorRepository {

    private final SQLTemplateService sqlTemplateService = SQLTemplateService.getSqlTemplateService();

    public int getAvailableAmount() {
        try (Session session = HibernateUtil.getSessionFactory().openSession()) {
            String sql = sqlTemplateService.getSQL(CountAvailableColors.class);
            Query<Color> query = session.createQuery(sql, Color.class);
            return query.getFetchSize();
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
