package com.github.irvinglink.Totems.storage.database;

import java.sql.DriverManager;
import java.sql.SQLException;

public class MySQL extends SQLManager {

    @Override
    public void setup(String host, String database, String username, String password) {

        try {

            if (conn != null && !(conn.isClosed())) return;


            synchronized (this) {

                Class.forName("com.mysql.jdbc.Driver");

                conn = DriverManager.getConnection("jdbc:mysql://" + host + ":" + 3306 + "/" + database, username, password);

                createTables();

                System.out.println("[ClashDonations] Successfully connected to the database!");

            }

        } catch (SQLException | ClassNotFoundException e) {
            e.printStackTrace();
        }
    }

}
