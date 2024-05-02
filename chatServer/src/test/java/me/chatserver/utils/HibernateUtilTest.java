package me.chatserver.utils;

import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.assertNotNull;

public class HibernateUtilTest {

    @Test
    public void hibernateUtilShouldOpenSession() {
        assertNotNull(HibernateUtil.getSessionFactory());
    }
}
