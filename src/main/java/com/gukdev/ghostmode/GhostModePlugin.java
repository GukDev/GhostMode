package com.gukdev.ghostmode;

import com.gukdev.ghostmode.listeners.PlayerMovementListener;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.command.PluginCommand;
import org.bukkit.entity.Player;
import org.bukkit.event.Listener;
import org.bukkit.plugin.java.JavaPlugin;

public class GhostModePlugin extends JavaPlugin implements Listener, CommandExecutor {
    private static GhostModePlugin instance;
    private GameManager gameManager;
    private ConfigManager configManager;

    @Override
    public void onEnable() {
        instance = this;
        configManager = new ConfigManager(this);
        getLogger().info("GhostMode Plugin Enabled");
        getServer().getPluginManager().registerEvents(new PlayerMovementListener(), this);
        gameManager = new GameManager(configManager);

        PluginCommand command = getCommand("ghostmode");
        if (command != null) {
            command.setExecutor(this);
        } else {
            getLogger().severe("Failed to register command 'ghostmode'.");
        }
    }

    @Override
    public void onDisable() {
        getLogger().info("GhostMode Plugin Disabled");
    }

    public static GhostModePlugin getInstance() {
        return instance;
    }

    public ConfigManager getConfigManager() {
        return configManager;
    }

    @Override
    public boolean onCommand(CommandSender sender, Command command, String label, String[] args) {
        if (command.getName().equalsIgnoreCase("ghostmode")) {
            if (sender instanceof Player) {
                Player player = (Player) sender;
                if (args.length > 0) {
                    if (args[0].equalsIgnoreCase("start")) {
                        gameManager.startGame();
                    } else if (args[0].equalsIgnoreCase("stop")) {
                        gameManager.stopGame();
                    } else if (args[0].equalsIgnoreCase("plantbomb")) {
                        gameManager.plantBomb(player);
                    } else if (args[0].equalsIgnoreCase("defusebomb")) {
                        gameManager.defuseBomb(player);
                    } else {
                        sender.sendMessage("Usage: /ghostmode <start|stop|plantbomb|defusebomb>");
                    }
                } else {
                    sender.sendMessage("Usage: /ghostmode <start|stop|plantbomb|defusebomb>");
                }
            } else {
                sender.sendMessage("This command can only be run by a player.");
            }
            return true;
        }
        return false;
    }
}
