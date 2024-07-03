package com.gukdev.ghostmode;

import org.bukkit.configuration.file.FileConfiguration;

public class ConfigManager {
    private final GhostModePlugin plugin;
    private FileConfiguration config;

    public ConfigManager(GhostModePlugin plugin) {
        this.plugin = plugin;
        loadConfig();
    }

    public void loadConfig() {
        plugin.saveDefaultConfig();
        config = plugin.getConfig();
    }

    public int getBombTimer() {
        return config.getInt("general.bomb_timer", 30);
    }

    public int getGhostTeamSize() {
        return config.getInt("general.ghost_team_size", 4);
    }

    public int getHumanTeamSize() {
        return config.getInt("general.human_team_size", 4);
    }

    public String getMessage(String path) {
        return config.getString("messages." + path, "");
    }
}
