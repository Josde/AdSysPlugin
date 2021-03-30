package usal.adsys.AdSysPlugin;

import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;

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

        } else if (command.getName().equals("join")) {
            if (sender instanceof Player) {
                int teamIndex = (this.plugin.equipos[0].getSize() > this.plugin.equipos[1].getSize())? 1 : 0;
                this.plugin.equipos[teamIndex].addEntry(sender.getName());
            }
        }
        return false;
    }
}
