package usal.adsys.AdSysPlugin;

import org.bukkit.Bukkit;
import org.bukkit.ChatColor;
import org.bukkit.entity.Player;
import org.bukkit.plugin.java.JavaPlugin;
import org.bukkit.scoreboard.*;

public class AdSysPlugin extends JavaPlugin {

    public Team[] equipos = new Team[2];
    public boolean isClickCountingEnabled = false;
    public Objective obj;
    public Scoreboard board;
    @Override
    public void onEnable() {
        // TODO: Permisos para los plugins, para que no todos puedan hacer start.
        // Inicializamos los comandos con su ejecutor
        ClickCounterCommand ccc = new ClickCounterCommand(this);
        getCommand("cowsay").setExecutor(new CowsayCommand());
        getCommand("start").setExecutor(ccc);
        getCommand("join").setExecutor(ccc);
        getCommand("shuffle").setExecutor(ccc);
        getServer().getPluginManager().registerEvents(new InteractionListener(this), this);
        getServer().getPluginManager().registerEvents(new ConnectionListener(), this);
        // Inicializamos un Scoreboard y dos equipos para el concurso de clicks.
        ScoreboardManager manager = Bukkit.getScoreboardManager();
        board = manager.getNewScoreboard();
        board.registerNewObjective("Clicks", "dummy");
        this.obj = board.getObjective("Clicks");
        this.obj.setDisplayName("Clicks");
        this.obj.setDisplaySlot(DisplaySlot.SIDEBAR);
        this.equipos[0] = board.registerNewTeam(ChatColor.RED + "Equipo Rojo");
        this.equipos[0].setColor(ChatColor.RED);
        this.equipos[1] = board.registerNewTeam(ChatColor.AQUA + "Equipo Azul");
        this.equipos[1].setColor(ChatColor.AQUA);
        getLogger().info(String.format("-----------------------| ADSYS PLUGIN LOADED |-----------------------"));

    }
    // Fired when plugin is disabled
    @Override
    public void onDisable() {

    }

}
