package usal.adsys.AdSysPlugin;

import org.bukkit.plugin.java.JavaPlugin;

public class AdSysPlugin extends JavaPlugin {

    @Override
    public void onEnable() {
        getLogger().info(String.format("-----------------------%n| ADSYS LOADED |%n-----------------------"));
        getCommand("cowsay").setExecutor(new CowsayCommand());
    }
    // Fired when plugin is disabled
    @Override
    public void onDisable() {

    }

}
