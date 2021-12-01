package com.github.irvinglink.AmethystLib.storage;


import com.github.irvinglink.AmethystLib.MClass;
import com.github.irvinglink.AmethystLib.storage.database.SQLManager;

/**
 * This will be the data that all your types of database will accept
 * for SQL you only need to work in {@link SQLManager} for not to repeat code
 * in each type of SQL database.
 * For YAML you need to work in {@link YAML}.
 *
 * All the methods that you put in this class will inherit in {@link YAML} and {@link SQLManager}.
 */
public interface Data {

    MClass plugin = MClass.getPlugin(MClass.class);


}
