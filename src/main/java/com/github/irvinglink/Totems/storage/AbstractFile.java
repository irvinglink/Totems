package com.github.irvinglink.Totems.storage;

import com.github.irvinglink.Totems.MClass;
import org.bukkit.ChatColor;
import org.bukkit.configuration.file.FileConfiguration;
import org.bukkit.configuration.file.YamlConfiguration;

import java.io.File;
import java.io.IOException;

public class AbstractFile {

    private final String fileName;

    private File f;

    private FileConfiguration file;

    private final MClass plugin = MClass.getPlugin(MClass.class);

    public AbstractFile(String fileName) {
        this.fileName = ChatColor.stripColor(fileName);
    }

    public void create(String path) {

        this.f = new File(path);

        if (!this.f.exists())
            try {
                this.f.createNewFile();
            } catch (IOException e) {
                e.printStackTrace();
            }

        //setDefaults();

    }

    public void save() {
        if (this.file != null)
            try {
                this.file.save(this.f);
            } catch (IOException e) {
                e.printStackTrace();
            }
    }

    public void delete() {
        this.f = new File(this.plugin.getDataFolder() + File.separator + "Clans" + File.separator + this.fileName + ".yml");
        if (this.f.exists())
            this.f.delete();
    }

    public boolean exists() {
        this.f = new File(this.plugin.getDataFolder() + File.separator + "Clans" + File.separator + this.fileName + ".yml");
        return this.f.exists();
    }

    public File getF() {
        if (this.f != null)
            return this.f;
        return null;
    }

    public FileConfiguration getFile() {
        if (this.file != null)
            return this.file;
        this.f = new File(this.plugin.getDataFolder() + File.separator + "Clans" + File.separator + this.fileName + ".yml");
        this.file = new YamlConfiguration();
        try {
            this.file.load(this.f);
        } catch (IOException |org.bukkit.configuration.InvalidConfigurationException e) {
            e.printStackTrace();
        }
        return this.file;
    }

}