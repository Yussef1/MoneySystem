package de.yussef.money;

import de.yussef.money.asyncmysql.AsyncMySQL;
import de.yussef.money.commands.MoneyCommand;
import org.bukkit.Bukkit;
import org.bukkit.plugin.java.JavaPlugin;

public class Money extends JavaPlugin {

    public static Money money;
    public static AsyncMySQL asyncMySQL;
    public static final String MONEY_PREFIX = "§8[§3Money§8] §7";

    @Override
    public void onLoad() {
        money = this;
    }

    @Override
    public void onEnable() {
        printOutWatermark();
        loadConfig();
        registerEvents();
        connectMySQL();

        Bukkit.getConsoleSender().sendMessage("§aDas MoneySystem wurde vollständig geladen.");
    }

    @Override
    public void onDisable() {
        Bukkit.getConsoleSender().sendMessage("§4Das MoneySystem ist nun Deaktiviert.");
    }

    private void loadConfig() {
        getConfig().options().copyDefaults(true);
        saveConfig();

        Bukkit.getConsoleSender().sendMessage("§eDie Config wurde geladen.");
    }

    private void registerEvents() {
        getCommand("money").setExecutor(new MoneyCommand());

        Bukkit.getConsoleSender().sendMessage("§eDie Events wurden registriert.");
    }

    private void connectMySQL() {
        String host = getConfig().getString("MySQL.Host"),
               database = getConfig().getString("MySQL.Database"),
               username = getConfig().getString("MySQL.Username"),
               password = getConfig().getString("MySQL.Password");
        int port = getConfig().getInt("MySQL.Port");

        asyncMySQL = new AsyncMySQL(money, host, port, username, password, database);
        asyncMySQL.update("CREATE TABLE IF NOT EXISTS MoneySystem (PlayerUUID VARCHAR(64), Money INT(100))");
    }

    private void printOutWatermark() {
        Bukkit.getConsoleSender().sendMessage("""
                 __  __                         _____           _                \s
                |  \\/  |                       / ____|         | |               \s
                | \\  / | ___  _ __   ___ _   _| (___  _   _ ___| |_ ___ _ __ ___ \s
                | |\\/| |/ _ \\| '_ \\ / _ \\ | | |\\___ \\| | | / __| __/ _ \\ '_ ` _ \\\s
                | |  | | (_) | | | |  __/ |_| |____) | |_| \\__ \\ ||  __/ | | | | |
                |_|  |_|\\___/|_| |_|\\___|\\__, |_____/ \\__, |___/\\__\\___|_| |_| |_|
                                          __/ |        __/ |                     \s
                                         |___/        |___/                      \s""".indent(1));
    }

    public static Money getMoney() {
        return money;
    }

    public static AsyncMySQL getAsyncMySQL() {
        return asyncMySQL;
    }
}