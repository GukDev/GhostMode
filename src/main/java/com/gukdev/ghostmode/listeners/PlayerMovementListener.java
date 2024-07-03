package com.gukdev.ghostmode.listeners;

import com.gukdev.ghostmode.GhostModePlugin;
import org.bukkit.Bukkit;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.entity.EntityDamageByEntityEvent;
import org.bukkit.event.player.PlayerMoveEvent;
import org.bukkit.potion.PotionEffectType;

public class PlayerMovementListener implements Listener {
    @EventHandler
    public void onPlayerMove(PlayerMoveEvent event) {
        Player player = event.getPlayer();
        if (player.hasPotionEffect(PotionEffectType.INVISIBILITY)) {
            double speed = event.getFrom().distance(event.getTo());
            if (speed > 0.1) {
                player.setGlowing(true); // Briefly show ghost
                Bukkit.getScheduler().runTaskLater(GhostModePlugin.getInstance(), () -> player.setGlowing(false), 20L); // Hide after 1 second
            }
        }
    }

    @EventHandler
    public void onEntityDamageByEntity(EntityDamageByEntityEvent event) {
        if (event.getDamager() instanceof Player) {
            Player player = (Player) event.getDamager();
            if (player.hasPotionEffect(PotionEffectType.INVISIBILITY)) {
                player.setGlowing(true); // Briefly show ghost
                Bukkit.getScheduler().runTaskLater(GhostModePlugin.getInstance(), () -> player.setGlowing(false), 20L); // Hide after 1 second
            }
        }
    }
}
