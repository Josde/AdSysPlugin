package usal.adsys.AdSysPlugin;

import org.bukkit.Bukkit;
import org.bukkit.ChatColor;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.player.PlayerInteractEvent;
import org.bukkit.event.player.PlayerJoinEvent;
import org.bukkit.event.player.PlayerQuitEvent;

public class ConnectionListener implements Listener {
    @EventHandler
    public void onPlayerJoin(PlayerJoinEvent e) {
        Bukkit.broadcastMessage("¡Bienvenido a nuestro servidor para AdSys, " + ChatColor.BOLD + "" + ChatColor.GOLD + e.getPlayer().getName() + ChatColor.RESET + "!");
    }
    @EventHandler
    public void onPlayerDisconnect(PlayerQuitEvent e) {
        Bukkit.broadcastMessage("Adios, " + ChatColor.BOLD + "" + ChatColor.BLUE + e.getPlayer().getName() + ChatColor.RESET + ", te echaremos de menos :(");
    }
}
