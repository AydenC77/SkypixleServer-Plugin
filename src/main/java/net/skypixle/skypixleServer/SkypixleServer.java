package net.skypixle.skypixleServer;

import org.bukkit.Bukkit;
import org.bukkit.command.Command;
import org.bukkit.command.CommandSender;
import org.bukkit.plugin.java.JavaPlugin;

import java.io.*;
import java.util.concurrent.Executors;
import java.util.concurrent.ScheduledExecutorService;
import java.util.concurrent.TimeUnit;

public final class SkypixleServer extends JavaPlugin {

    private long startTime;

    @Override
    public void onEnable() {
        // Plugin startup logic
        startTime = System.currentTimeMillis();

        ScheduledExecutorService scheduler = Executors.newScheduledThreadPool(1);
        scheduler.scheduleAtFixedRate(() -> {
            long currentTime = System.currentTimeMillis();
            long uptimeMillis = currentTime - startTime;
            if (getServer().getOnlinePlayers().size() == 0 && TimeUnit.MILLISECONDS.toMinutes(uptimeMillis) >= 15) {
                getServer().shutdown();
            }
        }, 0, 15, TimeUnit.MINUTES);

    }

    @Override
    public void onDisable() {
        // Plugin shutdown logic
        //getServer().getPluginManager().enablePlugin(this);
    }
}
