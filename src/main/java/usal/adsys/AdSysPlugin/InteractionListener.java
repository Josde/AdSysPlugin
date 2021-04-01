package usal.adsys.AdSysPlugin;

import org.bukkit.Bukkit;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.player.PlayerInteractEvent;
import org.bukkit.scoreboard.Score;

public class InteractionListener implements Listener {
    AdSysPlugin plugin;
    public InteractionListener(AdSysPlugin plugin) {
        this.plugin = plugin;
    }

    @EventHandler
    public void onPlayerInteract(PlayerInteractEvent e) {
        if (this.plugin.isClickCountingEnabled) {
            Player sender = e.getPlayer();
            Score s = this.plugin.obj.getScore(sender.getName());
            s.setScore(s.getScore() + 1);
            this.plugin.getLogger().info("Score de " + sender.getName() + " = " + s.getScore());
        }
    }
}
