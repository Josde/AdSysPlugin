package usal.adsys.AdSysPlugin;

import org.bukkit.Bukkit;
import org.bukkit.ChatColor;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;
import org.bukkit.scheduler.BukkitScheduler;
import org.bukkit.scoreboard.Score;


public class ClickCounterCommand implements CommandExecutor {
    AdSysPlugin plugin;
    public ClickCounterCommand(AdSysPlugin p) {
        this.plugin = p;
    }
    @Override
    public boolean onCommand(CommandSender sender, Command command, String label, String[] args) {
        if (command.getName().equals("start")) {
            /*
            TODO: Poner un mensaje de inicio, empezar a contar clicks y esperar 30/60s antes de anunciar el ganador. Limpieza despues de los 60s.
             */
            if (this.plugin.isClickCountingEnabled) return false;
            this.plugin.isClickCountingEnabled = true;
            Bukkit.broadcastMessage(ChatColor.GREEN + "" + ChatColor.BOLD + "EMPIEZA EL CONCURSO DE CLICKS");
            BukkitScheduler scheduler = Bukkit.getServer().getScheduler();
            scheduler.scheduleSyncDelayedTask(this.plugin, new Runnable() {

                @Override
                public void run() {
                    plugin.isClickCountingEnabled = false;
                    Bukkit.broadcastMessage(ChatColor.GREEN + "" + ChatColor.BOLD + "FIN DEL CONCURSO DE CLICKS");
                    Score[] scores = new Score[2];
                    for (int i = 0; i < scores.length; i++) {
                        for (Score s : plugin.equipos[i].getScoreboard().getScores("Clicks")) {
                            scores[i].setScore(scores[i].getScore() + s.getScore());
                        }
                    }
                    if (scores[0].getScore() > scores[1].getScore()) {
                        Bukkit.broadcastMessage("El ganador es.... " + ChatColor.BOLD + "" + ChatColor.RED + "EL EQUIPO ROJO");
                    } else if (scores[0].getScore() < scores[1].getScore()) {
                        Bukkit.broadcastMessage("El ganador es.... " + ChatColor.BOLD + "" + ChatColor.AQUA + "EL EQUIPO AZUL");
                    } else {
                        Bukkit.broadcastMessage("El resultado final es: " + ChatColor.BOLD + "" + ChatColor.GRAY + "EMPATE");
                    }
                }
            }, 100L); //Despues de 1200 ticks (60 segundos) contamos los clicks.
        } else if (command.getName().equals("join")) {
            if (sender instanceof Player) {
                int teamIndex = (this.plugin.equipos[0].getSize() > this.plugin.equipos[1].getSize())? 1 : 0;
                this.plugin.equipos[teamIndex].addEntry(sender.getName());
                if (teamIndex == 0) {
                    Bukkit.broadcastMessage(sender.getName() + " se ha unido al equipo: " + ChatColor.RED + "ROJO");
                } else {
                    Bukkit.broadcastMessage(sender.getName() + " se ha unido al equipo: " + ChatColor.AQUA + "AZUL");
                }
            }
        }
        return false;
    }
}
