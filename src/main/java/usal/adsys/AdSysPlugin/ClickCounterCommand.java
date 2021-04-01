package usal.adsys.AdSysPlugin;

import org.bukkit.Bukkit;
import org.bukkit.ChatColor;
import org.bukkit.Sound;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;
import org.bukkit.scheduler.BukkitRunnable;
import org.bukkit.scheduler.BukkitScheduler;
import org.bukkit.scheduler.BukkitTask;
import org.bukkit.scoreboard.Score;


public class ClickCounterCommand implements CommandExecutor {
    AdSysPlugin plugin;
    public ClickCounterCommand(AdSysPlugin p) {
        this.plugin = p;
    }
    @Override
    public boolean onCommand(CommandSender sender, Command command, String label, String[] args) {
        if (command.getName().equals("start")) {
            if (this.plugin.isClickCountingEnabled) return false;
            int duracion = 0;
            if (args.length != 0) duracion = Integer.parseInt(args[0]);
            if (duracion == 0) duracion = 30;
            int finalDuracion = duracion;
            // Iniciamos la cuenta de clicks, ponemos un mensaje y un sonido para que todo el mundo se entere.
            Bukkit.broadcastMessage(ChatColor.GREEN + "" + ChatColor.BOLD + "EMPIEZA EL CONCURSO DE CLICKS." + ChatColor.RESET + " Duración: " + duracion + "s");
            this.plugin.isClickCountingEnabled = true;
            for (Player p : Bukkit.getServer().getOnlinePlayers()) {
                p.playSound(p.getLocation(), Sound.ENTITY_PLAYER_LEVELUP, 1, 0);
            }
            BukkitScheduler scheduler = Bukkit.getServer().getScheduler();
            // Creamos un BukkitTask encargado de hacer una cuenta atras cada 10 segundos.
            BukkitTask countdown = new BukkitRunnable() {
                int timeLeft = finalDuracion;
                @Override
                public void run() {
                    timeLeft -= 10;
                    if (timeLeft <= 0) this.cancel();
                    Bukkit.broadcastMessage(String.format("Quedan %s%d%s segundos.", ChatColor.BOLD.toString(), timeLeft, ChatColor.RESET.toString()));
                }
            }.runTaskTimer(this.plugin, 200L, 200L); // Hacemos que tras 10 segundos, se empiece a ejecutar periodicamente.
            scheduler.scheduleSyncDelayedTask(this.plugin, new Runnable() {
                @Override
                public void run() {
                    plugin.isClickCountingEnabled = false;
                    Bukkit.broadcastMessage(ChatColor.GREEN + "" + ChatColor.BOLD + "FIN DEL CONCURSO DE CLICKS");
                    int scores[] = {0, 0};
                    int maxScore = 0;
                    String mvp = new String();
                    // Obtenemos la puntuacion de cada jugador
                    for (String entry : plugin.board.getEntries()) {
                        int scoreActual = plugin.obj.getScore(entry).getScore();
                        // Sumamos la puntuacion de cada jugador a la de su equipo correspondiente.
                        if (plugin.board.getEntryTeam(entry).equals(plugin.equipos[0])) {
                            scores[0] += scoreActual;
                        } else if (plugin.board.getEntryTeam(entry).equals(plugin.equipos[1])) {
                            scores[1] += scoreActual;
                        }
                        // Evaluamos la puntuacion maxima, para el MVP.
                        if (maxScore < scoreActual) {
                            maxScore = scoreActual;
                            mvp = entry;
                        }
                        plugin.obj.getScore(entry).setScore(0); // Reseteamos el score para la proxima vez que juguemos.
                    }
                    // Compensamos para el caso de que haya equipos desiguales.
                    if (plugin.equipos[1].getSize() > 0) {
                        float factorCorreccion = plugin.equipos[0].getSize() / plugin.equipos[1].getSize();
                        if (factorCorreccion < 1) scores[0] /= factorCorreccion;
                        else if (factorCorreccion > 1) scores[1] *= factorCorreccion;
                    }
                    // Anunciamos el ganador y el MVP
                    if (scores[0] > scores[1]) {
                        Bukkit.broadcastMessage("El ganador es.... " + ChatColor.BOLD + "" + ChatColor.RED + "EL EQUIPO ROJO");
                    } else if (scores[0] < scores[1]) {
                        Bukkit.broadcastMessage("El ganador es.... " + ChatColor.BOLD + "" + ChatColor.AQUA + "EL EQUIPO AZUL");
                    } else {
                        Bukkit.broadcastMessage("El resultado final es: " + ChatColor.BOLD + "" + ChatColor.GRAY + "EMPATE");
                    }
                    Bukkit.broadcastMessage(String.format("El MVP ha sido %s%s con %d (%f clicks/s).", ChatColor.BOLD.toString(), mvp, maxScore, (float)maxScore / finalDuracion));
                }
            }, 20 * duracion); // 20 * duracion porque hay 20 ticks por segundo, aproximadamente.
        } else if (command.getName().equals("join")) {
            if (sender instanceof Player) {
                Player p = (Player) sender;
                // Si el usuario ya esta en un equipo, el comando no tiene efecto.
                if (p.getScoreboard().getEntryTeam(p.getName()) != null) {
                    p.sendMessage("Ya estás dentro de un equipo.");
                    return true;
                }
                // Si no, le metemos en el equipo con menos miembros.
                p.setScoreboard(plugin.board);
                int teamIndex = (this.plugin.equipos[0].getSize() > this.plugin.equipos[1].getSize())? 1 : 0;
                this.plugin.equipos[teamIndex].addEntry(sender.getName());
                if (teamIndex == 0) {
                    Bukkit.broadcastMessage(sender.getName() + " se ha unido al equipo: " + ChatColor.RED + "ROJO");
                } else {
                    Bukkit.broadcastMessage(sender.getName() + " se ha unido al equipo: " + ChatColor.AQUA + "AZUL");
                }
            }
        }
        return true;
    }
}
