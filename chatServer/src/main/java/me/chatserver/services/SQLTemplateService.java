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

/**
 * Třída služby sql příkazů - je služba hledající a uchovavající řádky SQL
 * příkazů. Podle názvů tříd těch řádků pokusí se je výhledat v resourcech, a
 * pokud najde uloží do mapy, pak zpracuje jejich vyzvednutí.
 * Pro fungování třídy vždy se vytvoří textový SQL příkaz, uloží se do resource,
 * a ještě k tomu třída se stejným názvem a dědící po třídě SQLTemplate.
 *
 * @author Aleksei Baiukov
 * @version 02.05.2024
 */
@Slf4j
public class SQLTemplateService {

    /**
     * Pole obsahující složky, ve kterých se budou hledat třídy příkazů
     */
    private final String[] packagePaths = {"me.chatserver.database.templates"};


    /**
     * Mapa obsahující nalezené příkazy s jejich názvy
     */
    private final Map<Class<SQLTemplate>, String> sqlMap = new HashMap<>();


    /**
     * Pro realizaci patternu SINGLETON instance třídy se ukladá
     */
    private static SQLTemplateService sqlTemplateService;

    /**
     * Privatní konstruktor spuštějící vyhledání příkazů
     */
    private SQLTemplateService() {
        loadSqlTemplates();
    }


    /**
     * Metoda pro získání instance třídy. Pokud nebyla incializovaná, nejdřív vyvolá
     * konstruktor
     */
    public static SQLTemplateService getSqlTemplateService() {
        if (sqlTemplateService == null) {
            sqlTemplateService = new SQLTemplateService();
        }
        return sqlTemplateService;
    }

    /**
     * Metoda pro načítání příkazů. Nejdřív získá všechny třídy ze složek dědicí
     * po třídě SQLTemplate, pak pro každou pokusí se vyhledat text příkazu.
     */
    private void loadSqlTemplates() {
        final Set<Class<SQLTemplate>> sqlTemplateClasses = findAllSqlTemplates();
        for (Class<SQLTemplate> sqlTemplateClass : sqlTemplateClasses) {
            sqlMap.put(sqlTemplateClass, loadSqlTemplate(sqlTemplateClass));
        }
        log.info("SQLTemplateService has been initialized with " + sqlMap.keySet().size() + " query(-es)");
    }

    /**
     * Metoda pro vyhledání textu SQL příkazů podle šablony. Pokusí se ji získat ve složce
     * sql/templates obshující v resourcech, pokud najde soubor se shodným jménem jako šablona,
     * vrátí její obsah
     *
     * @param sqlTemplateClass - třída šablony pro vyhledání
     * @return textový řádek obsahující příkaz
     * @throws RuntimeException chyba vyhledání
     */
    private String loadSqlTemplate(final Class<? extends SQLTemplate> sqlTemplateClass) {
        try {
            return IOUtils.resourceToString(
                    String.format("/sql_templates/%s.sql", sqlTemplateClass.getSimpleName()),
                    StandardCharsets.UTF_8);
        } catch (IOException e) {
            throw new RuntimeException("Error reading from SQL Template: " + sqlTemplateClass, e);
        }
    }

    /**
     * Metoda pro vyhledání všech šablon podle odkazu, pro každou složku
     * z odkazu, provede vyhledání
     *
     * @return Set tříd dědící po třídě SLQTemplate
     */
    private Set<Class<SQLTemplate>> findAllSqlTemplates() {
        final Set<Class<SQLTemplate>> sqlTemplateClasses = new HashSet<>();

        for (String packagePath : packagePaths) {
            sqlTemplateClasses.addAll(findSqlTemplates(packagePath));
        }
        return sqlTemplateClasses;
    }

    /**
     * Metoda pro vyhledání všech šablon ve složce. Vytvoří se výhledáč s filtrem
     * na třídy dědicí po SQLTemplate. Pokusí se výhledat jména kandidatů, a všechny
     * uloží do Setu
     *
     * @param  packagePath odkaz na konkretní složku
     * @return Set tříd dědící po třídě SLQTemplate
     */
    private Set<Class<SQLTemplate>> findSqlTemplates(final String packagePath) {
        ClassPathScanningCandidateComponentProvider provider =
                new ClassPathScanningCandidateComponentProvider(false);
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

    /**
     * Metoda pro vyzvednutí SQL příkazů podle jejich názvu. Vytáhne z mapy a vrátí, pokud
     * takový existuje
     *
     * @param  sqlName název příkazu
     * @return textový SQL příkaz
     */
    public String getSQL(Class<? extends SQLTemplate> sqlName) {
        return sqlMap.get(sqlName);
    }

    // Getter
    public Map<Class<SQLTemplate>, String> getSqlMap() { return sqlMap; }
}
