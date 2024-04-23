package me.chatserver.services;

import me.chatserver.repositories.SQLTemplate;
import org.apache.commons.io.IOUtils;
import org.springframework.beans.factory.config.BeanDefinition;
import org.springframework.context.annotation.ClassPathScanningCandidateComponentProvider;
import org.springframework.core.type.filter.AssignableTypeFilter;

import java.io.IOException;
import java.nio.charset.StandardCharsets;
import java.util.HashMap;
import java.util.HashSet;
import java.util.Map;
import java.util.Set;

public class SQLTemplateService {

    private final String[] packagePaths = {"me.chatserver.repositories.templates"};

    private Map<Class<SQLTemplate>, String> sqlMap = new HashMap<>();

    public SQLTemplateService() {
        loadSqlTemplates();
    }

    private void loadSqlTemplates() {
        final Set<Class<SQLTemplate>> sqlTemplateClasses = findAllSqlTemplates();
        for (Class<SQLTemplate> sqlTemplateClass : sqlTemplateClasses) {
            sqlMap.put(sqlTemplateClass, loadSqlTemplate(sqlTemplateClass));
        }
    }

    private String loadSqlTemplate(final Class<? extends SQLTemplate> sqlTemplateClass) {
        try {
            return IOUtils.resourceToString(
                    String.format("/sql_templates/%s.sql", sqlTemplateClass.getSimpleName()),
                    StandardCharsets.UTF_8);
        } catch (IOException e) {
            throw new RuntimeException("Error reading from SQL Template: " + sqlTemplateClass, e);
        }
    }

    private Set<Class<SQLTemplate>> findAllSqlTemplates() {
        final Set<Class<SQLTemplate>> sqlTemplateClasses = new HashSet<>();

        for (String packagePath : packagePaths) {
            sqlTemplateClasses.addAll(findSqlTemplates(packagePath));
        }
        return sqlTemplateClasses;
    }

    private Set<Class<SQLTemplate>> findSqlTemplates(final String packagePath) {
        ClassPathScanningCandidateComponentProvider provider = new ClassPathScanningCandidateComponentProvider(false);
        provider.addIncludeFilter(new AssignableTypeFilter(SQLTemplate.class));
        final Set<Class<SQLTemplate>> sqlTemplateClasses = new HashSet<>();
        Set<BeanDefinition> components = provider.findCandidateComponents(packagePath);
        for (BeanDefinition component : components) {
            try {
                Class cls = Class.forName(component.getBeanClassName());
                sqlTemplateClasses.add(cls);
            } catch (ClassNotFoundException ignored) { }
        }

        return sqlTemplateClasses;
    }

    public String getSQL(Class<? extends SQLTemplate> sqlName) {
        return sqlMap.get(sqlName);
    }
}
