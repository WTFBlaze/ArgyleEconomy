package me.wtfblaze;

import org.bukkit.OfflinePlayer;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.logging.Level;

public abstract class Database {
    ArgyleEconomy plugin;
    String table = "argyle_econ";
    Connection connection;

    public Database(ArgyleEconomy instance) {
        plugin = instance;
    }

    public abstract Connection getSQLConnection();

    public abstract void load();

    public void initialize() {
        connection = getSQLConnection();
        try{
            PreparedStatement ps = connection.prepareStatement("SELECT * FROM " + table + " WHERE player = ?");
            ResultSet rs = ps.executeQuery();
            close(ps,rs);

        } catch (SQLException ex) {
            plugin.getLogger().log(Level.SEVERE, "Unable to retrieve connection", ex);
        }
    }

    public Double getBalance(OfflinePlayer offlinePlayer) {
        Connection conn = null;
        PreparedStatement ps = null;
        ResultSet rs = null;
        try {
            conn = getSQLConnection();
            ps = conn.prepareStatement(String.format("SELECT * FROM %s WHERE player = '%s';", table, offlinePlayer.getUniqueId()));
            rs = ps.executeQuery();
            while(rs.next()){
                // Tell the database to search for the player's UUID in the db.
                if(rs.getString("player").equalsIgnoreCase(offlinePlayer.getUniqueId().toString())){
                    return rs.getDouble("balance"); // Return the players balance
                }
            }
        } catch (SQLException ex) {
            plugin.getLogger().log(Level.SEVERE, sqlConnectionExecute(), ex);
        } finally {
            try {
                if (ps != null)
                    ps.close();
                if (conn != null)
                    conn.close();
            } catch (SQLException ex) {
                plugin.getLogger().log(Level.SEVERE, sqlConnectionClose(), ex);
            }
        }
        return 0d;
    }

    public void setBalance(OfflinePlayer offlinePlayer, double newBalance) {
        Connection conn = null;
        PreparedStatement ps = null;
        try {
            conn = getSQLConnection();
            ps = conn.prepareStatement(String.format("REPLACE INTO %s (player,balance) VALUES(?,?)", table));
            ps.setString(1, offlinePlayer.getUniqueId().toString());
            ps.setDouble(2, newBalance);
            ps.executeUpdate();
        } catch (SQLException ex) {
            plugin.getLogger().log(Level.SEVERE, sqlConnectionExecute(), ex);
        } finally {
            try {
                if (ps != null)
                    ps.close();
                if (conn != null)
                    conn.close();
            } catch (SQLException ex) {
                plugin.getLogger().log(Level.SEVERE, sqlConnectionClose(), ex);
            }
        }
    }

    public void close(PreparedStatement ps,ResultSet rs){
        try {
            if (ps != null)
                ps.close();
            if (rs != null)
                rs.close();
        } catch (SQLException ex) {
            close(plugin, ex);
        }
    }

    // LOG METHODS
    public void execute(ArgyleEconomy plugin, Exception ex){
        plugin.getLogger().log(Level.SEVERE, "Couldn't execute MySQL statement: ", ex);
    }
    public void close(ArgyleEconomy plugin, Exception ex){
        plugin.getLogger().log(Level.SEVERE, "Failed to close MySQL connection: ", ex);
    }

    public String sqlConnectionExecute(){
        return "Couldn't execute MySQL statement: ";
    }
    public String sqlConnectionClose(){
        return "Failed to close MySQL connection: ";
    }
    public String noSQLConnection(){
        return "Unable to retrieve MYSQL connection: ";
    }
    public String noTableFound(){
        return "Database Error: No Table Found";
    }
}
