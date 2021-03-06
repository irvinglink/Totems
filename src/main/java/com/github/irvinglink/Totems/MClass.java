package com.github.irvinglink.Totems;

import com.github.irvinglink.Totems.commands.admin.TotemAdminCommand;
import com.github.irvinglink.Totems.editor.chat.EditorHandler;
import com.github.irvinglink.Totems.editor.inventory.EditorManager;
import com.github.irvinglink.Totems.gui.manager.IMenu;
import com.github.irvinglink.Totems.listeners.GuiEvents;
import com.github.irvinglink.Totems.storage.Data;
import com.github.irvinglink.Totems.storage.DatabaseType;
import com.github.irvinglink.Totems.storage.YAML;
import com.github.irvinglink.Totems.storage.database.MySQL;
import com.github.irvinglink.Totems.storage.database.SQLite;
import com.github.irvinglink.Totems.utils.chat.Chat;
import lombok.Getter;
import org.bukkit.Bukkit;
import org.bukkit.configuration.InvalidConfigurationException;
import org.bukkit.configuration.file.FileConfiguration;
import org.bukkit.configuration.file.YamlConfiguration;
import org.bukkit.plugin.java.JavaPlugin;

import java.io.File;
import java.io.IOException;
import java.io.InputStreamReader;
import java.nio.charset.StandardCharsets;
import java.nio.file.Files;
import java.util.*;

public class MClass extends JavaPlugin {

    /*
        DEFAULT LIBRARY: 1.16
        HexManager needs to be more or equal to 1.16
     */
    private Chat chat;
    private Data database;


    private final String version = this.getDescription().getVersion();
    private String serverVersion = "";


    private File configFile;
    private File langFile;

    private FileConfiguration config;
    private FileConfiguration lang;

    private final Map<UUID, IMenu> playerMenus = Collections.synchronizedMap(new HashMap<>());

    @Getter private EditorManager editorManager;

    @Override
    public void onLoad() {

        createFiles();

        setServerVersion();

        // setupDatabase(getConfig().getString("Database.host"), getConfig().getString("Database.database"), getConfig().getString("Database.username"), getConfig().getString("Database.password"));
        this.chat = new Chat();
        this.editorManager = new EditorManager(this, chat);

        chat.registerHooks();

    }

    @Override
    public void onEnable() {

        new TotemAdminCommand("totemadmin", "totems.admin", false);

        getServer().getPluginManager().registerEvents(new GuiEvents(), this);
        new EditorHandler();

    }

    @Override
    public void onDisable() {

    }

    public Chat getChat() {
        return chat;
    }

    public void setServerVersion() {
        String v = Bukkit.getServer().getClass().getPackage().getName();
        this.serverVersion = v.substring(v.lastIndexOf(".") + 1);
    }

    public String getServerVersion() {
        return this.serverVersion;
    }


    public String getVersion() {
        return this.version;
    }

    /**
     * Amethystlib supports MySQL, MariaDB, SQLite and YAML.
     * This method will define the database that the plugin will use.
     */
    void setupDatabase(String host, String database, String username, String password) {

        DatabaseType databaseType = DatabaseType.valueOf(getConfig().getString("Database.type").toUpperCase());

        switch (databaseType) {

            case MYSQL:
            case MARIADB:
                MySQL mySQL = new MySQL();
                mySQL.setup(host, database, username, password);

                this.database = mySQL;
                break;

            case SQLITE:
                SQLite sqLite = new SQLite();
                sqLite.setup(host, database, username, password);

                this.database = sqLite;
                break;

            case YAML:
                this.database = new YAML();
                break;

        }

    }

    /**
     * Creates the common files such as config.yml and lang.yml
     */
    void createFiles() {

        File mainFolder = getDataFolder();

        if (!mainFolder.exists()) mainFolder.mkdirs();


        this.configFile = new File(mainFolder, "config.yml");
        this.langFile = new File(mainFolder, "lang.yml");

        if (!this.configFile.exists())
            try {
                Files.copy(Objects.requireNonNull(getResource(this.configFile.getName())), this.configFile.toPath());
            } catch (IOException e) {
                e.printStackTrace();
            }

        if (!this.langFile.exists())
            try {
                Files.copy(Objects.requireNonNull(getResource(this.langFile.getName())), this.langFile.toPath());
            } catch (IOException e) {
                e.printStackTrace();
            }

        updateFiles(configFile, langFile);

        getConfig();
        getLang();

    }

    /**
     * Updates the old data of the older version of the plugin to the newer data from the plugin.
     */
    void updateFiles(File... files) {

        getServer().getLogger().info("[Totems] Updating files...");

        for (File file : files) {

            YamlConfiguration externalYamlConfig = YamlConfiguration.loadConfiguration(file);
            externalYamlConfig.options().copyDefaults(true);

            InputStreamReader internalConfig = new InputStreamReader(Objects.requireNonNull(getResource(file.getName())), StandardCharsets.UTF_8);
            YamlConfiguration internalYamlConfig = YamlConfiguration.loadConfiguration(internalConfig);

            for (String line : internalYamlConfig.getKeys(true))
                if (!externalYamlConfig.contains(line)) externalYamlConfig.set(line, internalYamlConfig.get(line));

            try {
                externalYamlConfig.save(file);
            } catch (IOException e) {
                e.printStackTrace();
            }

        }

        getServer().getLogger().info("[Totems] Files are now updated!");

    }

    /**
     * Reload the config of your plugin. This is commonly used in reload commands.
     * To add more files to reload copy the code section.
     */
    public void reloadConfig() {
        if (this.config != null)
            this.config = YamlConfiguration.loadConfiguration(this.configFile);

        if (this.lang != null)
            this.lang = YamlConfiguration.loadConfiguration(this.langFile);

    }

    /**
     * This save the config of your plugins this is used when you update data.
     */
    public void saveConfig() {
        if (this.config != null)
            try {
                this.config.save(this.configFile);
            } catch (IOException e) {
                e.printStackTrace();
            }
        if (this.lang != null)
            try {
                this.lang.save(this.langFile);
            } catch (IOException e) {
                e.printStackTrace();
            }

    }

    @Override
    public FileConfiguration getConfig() {

        if (this.config != null)
            return this.config;

        this.config = new YamlConfiguration();

        try {
            this.config.load(this.configFile);
        } catch (IOException | InvalidConfigurationException e) {
            e.printStackTrace();
        }

        return this.config;
    }

    public FileConfiguration getLang() {
        if (this.lang != null)
            return this.lang;

        this.lang = new YamlConfiguration();

        try {
            this.lang.load(this.langFile);
        } catch (IOException | InvalidConfigurationException e) {
            e.printStackTrace();
        }

        return this.lang;

    }

    /**
     * This method returns the menus/gui opened by a player.
     */
    public Map<UUID, IMenu> getPlayerMenus() {
        return playerMenus;
    }

    /**
     * Returns the database used by the plugin (MySQL, MariaDB, SQLite, YAML)
     * Take a look to {@link Data}
     */
    public Data getDatabase() {
        return database;
    }

}
