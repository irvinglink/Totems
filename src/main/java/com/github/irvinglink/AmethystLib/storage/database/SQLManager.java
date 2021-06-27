package com.github.irvinglink.AmethystLib.storage.database;

import com.github.irvinglink.AmethystLib.storage.Data;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.SQLException;

public abstract class SQLManager implements Data {


    protected Connection conn;

    public abstract void setup(String host, String database, String username, String password);

    public Connection getConnection() {
        return this.conn;
    }

    public synchronized void createTables() {

        try {

            PreparedStatement stmt2 = getConnection().prepareStatement("CREATE TABLE IF NOT EXISTS playerData(UUID VARCHAR(100), NAME VARCHAR(40), PACKAGE VARCHAR(100), DATE LONG, BANNED BOOLEAN)");
            stmt2.executeUpdate();

        } catch (SQLException e) {
            e.printStackTrace();
        }

    }

}
