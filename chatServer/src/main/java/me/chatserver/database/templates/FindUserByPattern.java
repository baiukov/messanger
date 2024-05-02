package me.chatserver.database.templates;

import me.chatserver.database.SQLTemplate;

/**
 * Třída dědicí po třídě SQLTemplate pro vyhledání šablony textového SQL příkazů.
 * Příkaz pro nachazení uživatele podle části jména, příjmení nebo uživatelského jména.
 * Bude se vyhledavat podle názvu této třídy, musí shodovat s souborem *.sql ve
 * složce sql_templates v resourcech
 *
 * @author Aleksei Baiukov
 * @version 02.05.2024
 */
public class FindUserByPattern extends SQLTemplate { }
