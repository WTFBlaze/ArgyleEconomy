package me.wtfblaze;

import java.io.File;
import java.io.IOException;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.logging.Level;

public class SQLite extends Database {
    public SQLite(ArgyleEconomy instance) {
        super(instance);
    }

    public String SQLiteCreateTokensTable = "CREATE TABLE IF NOT EXISTS " + table +
            "(`player` varchar(32) NOT NULL," +
            "`balance` double(10, 2) NOT NULL," +
            "PRIMARY KEY (`player`)" +
            ");";

    public Connection getSQLConnection() {
        File dataFolder = new File(plugin.getDataFolder(), "data.db");
        if (!dataFolder.exists()){
            try {
                //noinspection ResultOfMethodCallIgnored
                dataFolder.createNewFile();
            } catch (IOException e) {
                plugin.getLogger().log(Level.SEVERE, "File write error: data.db");
            }
        }
        try {
            if(connection != null && !connection.isClosed()){
                return connection;
            }
            Class.forName("org.sqlite.JDBC");
            connection = DriverManager.getConnection("jdbc:sqlite:" + dataFolder);
            return connection;
        } catch (SQLException ex) {
            plugin.getLogger().log(Level.SEVERE,"SQLite exception on initialize", ex);
        } catch (ClassNotFoundException ex) {
            plugin.getLogger().log(Level.SEVERE, "You need the SQLite JBDC library. Google it. Put it in /lib folder.");
        }
        return null;
    }

    public void load() {
        connection = getSQLConnection();
        try {
            Statement s = connection.createStatement();
            s.executeUpdate(SQLiteCreateTokensTable);
            s.close();
        } catch (SQLException e) {
            e.printStackTrace();
        }
        initialize();
    }
}
