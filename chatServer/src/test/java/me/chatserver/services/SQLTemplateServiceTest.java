package me.chatserver.services;

import me.chatserver.database.templates.FindUserByUserName;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;

public class SQLTemplateServiceTest {

    private final SQLTemplateService sqlTemplateService = SQLTemplateService.getSqlTemplateService();

    @Test
    public void shouldInitSQLTemplateService() {
        Assertions.assertNotNull(SQLTemplateService.getSqlTemplateService());
    }

    @Test
    public void shouldFindAllSQLTemplates() {
        Assertions.assertEquals(sqlTemplateService.getSqlMap().size(), 11);
    }

    @Test
    public void givenQueryTemplateShouldReturnSQLQuery() {
        String expected = "FROM User WHERE USER_NAME = :userName";
        Assertions.assertEquals(expected, sqlTemplateService.getSQL(FindUserByUserName.class));
    }
}
