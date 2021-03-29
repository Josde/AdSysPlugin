package usal.adsys.AdSysPlugin;

import org.bukkit.Bukkit;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;


public class CowsayCommand implements CommandExecutor {

    @Override
    public boolean onCommand(CommandSender sender, Command command, String label, String[] args) {
        String txt = String.join(" ", args);
        int len = txt.length();
        String cowsay = Utils.repeat("_", len) +
                                "\n< " + txt + " >\n"
                        + Utils.repeat("-", len) +
                "\n        \\   ^__^\n" +
                "         \\  (oo)\\_______\n" +
                "            (__)\\       )\\/\\\n" +
                "                ||----w |\n" +
                "                ||     ||\n";
        Bukkit.broadcastMessage(cowsay);
        return false;
    }
}