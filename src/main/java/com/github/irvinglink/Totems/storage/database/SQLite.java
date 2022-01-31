package com.github.irvinglink.Totems.storage.database;

import java.sql.DriverManager;
import java.sql.SQLException;

public class SQLite extends SQLManager {

    @Override
    public void setup(String host, String database, String username, String password) {

        try {

            synchronized (this) {

                Class.forName("org.sqlite.JDBC");

                conn = DriverManager.getConnection("jdbc:sqlite:" + plugin.getDataFolder().toPath() + "/database.db");

                createTables();

                System.out.println("[ClashDonations] Successfully connected to the database!");

            }

        } catch (SQLException | ClassNotFoundException e) {
            e.printStackTrace();
        }

    }

}
