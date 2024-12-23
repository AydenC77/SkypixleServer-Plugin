package net.skypixle.skypixleServer;

import org.bukkit.command.Command;
import org.bukkit.command.CommandSender;
import org.bukkit.plugin.java.JavaPlugin;
import java.util.concurrent.Executors;
import java.util.concurrent.ScheduledExecutorService;
import java.util.concurrent.TimeUnit;

public final class SkypixleServer extends JavaPlugin {

    private long startTime;
    private boolean isServerShuttingDown;

    @Override
    public void onEnable() {
        // Plugin startup logic
        startTime = System.currentTimeMillis();
        isServerShuttingDown = false;

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
        if (isServerShuttingDown == false) {
            getServer().getPluginManager().enablePlugin(this);
            throw new UnsupportedOperationException("This plugin cannot be disabled!");
        }
    }

    @Override
    public boolean onCommand(CommandSender sender, Command command, String label, String[] args) {
        if (command.getName().equals("stopserver")) {
            isServerShuttingDown = true;
            getServer().shutdown();
        }
        return super.onCommand(sender, command, label, args);
    }
}
