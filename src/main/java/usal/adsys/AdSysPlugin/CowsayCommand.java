package usal.adsys.AdSysPlugin;

import org.bukkit.Bukkit;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;


public class CowsayCommand implements CommandExecutor {

    @Override
    public boolean onCommand(CommandSender sender, Command command, String label, String[] args) {
        String txt = String.join(" ", args); // El mensaje que haya enviado el usuario viene como argumentos separados, asi que lo juntamos.
        int len = txt.length();
        String cowsay = Utils.repeat("_", len + 2) +
                                "\n< " + txt + " >\n"
                        + Utils.repeat("-", len + 2) +
                "\n        \\   ^__^\n" +
                "         \\  (oo)\\_______\n" +
                "            (__)\\          )\\/\\\n" +
                "                ||------w |\n" +
                "                ||           ||\n";
        Bukkit.broadcastMessage(cowsay);
        return false;
    }
}
