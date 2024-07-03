package com.gukdev.ghostmode;

import com.gukdev.ghostmode.listeners.PlayerMovementListener;
import org.bukkit.Bukkit;
import org.bukkit.Location;
import org.bukkit.command.Command;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;
import org.bukkit.event.Listener;
import org.bukkit.plugin.java.JavaPlugin;

public class GhostModePlugin extends JavaPlugin implements Listener {
    private static GhostModePlugin instance;
    private GameManager gameManager;

    @Override
    public void onEnable() {
        instance = this;
        getLogger().info("GhostMode Plugin Enabled");
        getServer().getPluginManager().registerEvents(new PlayerMovementListener(), this);
        gameManager = new GameManager();
    }

    @Override
    public void onDisable() {
        getLogger().info("GhostMode Plugin Disabled");
    }

    public static GhostModePlugin getInstance() {
        return instance;
    }

    @Override
    public boolean onCommand(CommandSender sender, Command command, String label, String[] args) {
        if (command.getName().equalsIgnoreCase("ghostmode")) {
            if (sender instanceof Player) {
                Player player = (Player) sender;
                if (args.length > 0 && args[0].equalsIgnoreCase("start")) {
                    gameManager.startGame();
                } else if (args.length > 0 && args[0].equalsIgnoreCase("stop")) {
                    gameManager.stopGame();
                } else if (args.length > 0 && args[0].equalsIgnoreCase("plantbomb")) {
                    gameManager.plantBomb(player);
                } else if (args.length > 0 && args[0].equalsIgnoreCase("defusebomb")) {
                    gameManager.defuseBomb(player);
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
