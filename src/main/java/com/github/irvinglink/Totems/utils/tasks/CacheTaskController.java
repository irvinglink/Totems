package com.github.irvinglink.Totems.utils.tasks;

import org.bukkit.Bukkit;

import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;

public class CacheTaskController {

    public static final Map<String, CacheTask> cacheTaskMap = new ConcurrentHashMap<>();
    private static boolean isInitialize = false;

    public static void register(String id, CacheTask cacheTask) {
        cacheTaskMap.put(id, cacheTask);
    }

    public static void runTasks() {

        if (isInitialize) {
            Bukkit.getLogger().warning("CacheTaskController is already initialized.");
            return;
        }

        isInitialize = true;
        cacheTaskMap.values().forEach(CacheTask::run);
    }

    public static void stop(String id) {
        cacheTaskMap.get(id).stop();
    }

}
