package me.chatserver.enums;

/**
 *  Přípustné názvy příkazů podle kontraktu s front aplikací
 */
public class Commands {

    /**
     * Název příkazu pro získání celého jména
     */
    public static String FETCHNAME = "FETCHNAME";

    /**
     * Název příkazu pro získání uživatelů podle části jména
     */
    public static String FETCHUSERS = "FETCHUSERS";

    /**
     * Název příkazu pro získání barvy
     */
    public static String FETCHCOLOR = "FETCHCOLOR";

    /**
     * Název příkazu pro získání dat o uživatelu ve formatu partnera
     */
    public static String FETCHPARTNERDATA = "FETCHPARTNERDATA";

    /**
     * Název příkazu pro odeslání zprávy
     */
    public static String SEND = "SEND";

    /**
     * Název příkazu pro získání všech zpráv
     */
    public static String FETCHMESSAGES = "FETCHMESSAGES";

    /**
     * Název příkazu pro získání všech chatů
     */
    public static String FETCHDIALOGUES = "FETCHDIALOGUES";

    /**
     * Název příkazu pro přečtení všech zpráv v chatu
     */
    public static String READMESSAGES = "READMESSAGES";

    /**
     * Název příkazu pro přípnutí chatu
     */
    public static String PIN = "PIN";

    /**
     * Název příkazu pro odepnutí chatu
     */
    public static String UNPIN = "UNPIN";

    /**
     * Název příkazu pro blokování uživatele
     */
    public static String BLOCK = "BLOCK";

    /**
     * Název příkazu pro obnovení uživatelských dat
     */
    public static String UPDATE = "UPDATE";

    /**
     * Název příkazu pro registraci
     */
    public static String REGISTER = "REGISTER";

    /**
     * Název příkazu pro příhlášení
     */
    public static String LOGIN = "LOGIN";

}
