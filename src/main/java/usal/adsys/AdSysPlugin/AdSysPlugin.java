package usal.adsys.AdSysPlugin;

import org.bukkit.Bukkit;
import org.bukkit.ChatColor;
import org.bukkit.plugin.java.JavaPlugin;
import org.bukkit.scoreboard.Objective;
import org.bukkit.scoreboard.Scoreboard;
import org.bukkit.scoreboard.ScoreboardManager;
import org.bukkit.scoreboard.Team;

public class AdSysPlugin extends JavaPlugin {

    public Team[] equipos = new Team[2];
    public boolean isClickCountingEnabled = false;
    public Objective obj;
    @Override
    public void onEnable() {
        // TODO: Permisos para los plugins, para que no todos puedan hacer start.
        // Inicializamos los comandos con su ejecutor
        getCommand("cowsay").setExecutor(new CowsayCommand());
        getCommand("clickstart").setExecutor(new ClickCounterCommand(this));
        getCommand("clickjoin").setExecutor(new ClickCounterCommand(this));
        // Inicializamos un Scoreboard y dos equipos para el concurso de clicks.
        ScoreboardManager manager = Bukkit.getScoreboardManager();
        Scoreboard board = manager.getNewScoreboard();
        board.registerNewObjective("Clicks", "dummy");
        this.obj = board.getObjective("Clicks");
        this.equipos[0] = board.registerNewTeam(ChatColor.RED + "Equipo Rojo");
        this.equipos[1] = board.registerNewTeam(ChatColor.BLUE + "Equipo Azul");
        getLogger().info(String.format("-----------------------%n| ADSYS PLUGIN LOADED |%n-----------------------"));

    }
    // Fired when plugin is disabled
    @Override
    public void onDisable() {

    }

}
