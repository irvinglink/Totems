package com.github.irvinglink.AmethystLib.utils.tasks;

import com.github.irvinglink.AmethystLib.MClass;
import org.bukkit.scheduler.BukkitRunnable;
import org.bukkit.scheduler.BukkitTask;

public abstract class CacheTask extends BukkitRunnable {

    private final MClass plugin;
    private final long minutes;

    private final BukkitTask bukkitTask;

    public CacheTask(MClass plugin, long minutes) {
        this.plugin = plugin;
        this.minutes = minutes;
        this.bukkitTask = runTaskTimer(plugin, minutes * 1200, minutes * 1200);
    }

    public void stop() {
        this.bukkitTask.cancel();
    }

    @Override
    public void run() {
        wipeCache();
    }

    public abstract void wipeCache();


}