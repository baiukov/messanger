package me.chatserver.services;

import lombok.extern.slf4j.Slf4j;
import me.chatserver.database.SQLTemplate;
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

@Slf4j
public class SQLTemplateService {

    private final String[] packagePaths = {"me.chatserver.database.templates"};

    private Map<Class<SQLTemplate>, String> sqlMap = new HashMap<>();

    private static SQLTemplateService sqlTemplateService;

    private SQLTemplateService() {
        loadSqlTemplates();
    }

    public static SQLTemplateService getSqlTemplateService() {
        if (sqlTemplateService == null) {
            sqlTemplateService = new SQLTemplateService();
        }
        return sqlTemplateService;
    }

    private void loadSqlTemplates() {
        final Set<Class<SQLTemplate>> sqlTemplateClasses = findAllSqlTemplates();
        for (Class<SQLTemplate> sqlTemplateClass : sqlTemplateClasses) {
            sqlMap.put(sqlTemplateClass, loadSqlTemplate(sqlTemplateClass));
        }
        log.info("SQLTemplateService has been initialized with " + sqlMap.keySet().size() + " query(-es)");
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

    public Map<Class<SQLTemplate>, String> getSqlMap() { return sqlMap; }
}
