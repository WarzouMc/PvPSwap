package fr.WarzouMc.PvPSwap;

import fr.WarzouMc.PvPSwap.configSetup.ConfigSetup;
import fr.WarzouMc.PvPSwap.configSetup.State;
import fr.WarzouMc.PvPSwap.scorboard.Updater;
import org.bukkit.Bukkit;
import org.bukkit.ChatColor;
import org.bukkit.entity.Player;
import org.bukkit.plugin.java.JavaPlugin;

import fr.WarzouMc.PvPSwap.plugin.PluginManager;

public class Main extends JavaPlugin {

    private State state;

    private ConfigSetup cs = new ConfigSetup(this);

    @Override
    public void onEnable() {

        if(!getDataFolder().exists()){
            getDataFolder().mkdir();
            saveDefaultConfig();

            getConfig().options().header("This plugin has been creat by WarzouMc\nThank you for use this\nYou can comment this plugin on the spigot official web site\nIf you discover bug or dysfunction thank you for the report on the site : https://www.spigotmc.org/");
            saveConfig();
        }

        for(Player pls : Bukkit.getOnlinePlayers()){
            pls.kickPlayer("§6Server restarting !\n" +
                    "§5§oYou can reconnect now !");
        }

        Bukkit.getServer().getConsoleSender().sendMessage(ChatColor.GREEN + "###############################\n" +
                                                             "                 ##PvPSwap 0.0.4 is now Enable##\n" +
                                                             "                 ###############################");

        Bukkit.getServer().getPluginManager().registerEvents(new PluginManager(this), this);

        setState(State.WAITING);

        Updater updater = new Updater(this);
        updater.runTaskTimer(this, 0, 20);

    }

    @Override
    public void onDisable() {
        Bukkit.getServer().getConsoleSender().sendMessage(ChatColor.RED + "################################\n" +
                                                           "                 ##PvPSwap 0.0.4 is now Disable##\n" +
                                                           "                 ################################");
    }

    public State getState() {
        return state;
    }

    public void setState(State state) {
        this.state = state;
    }

}
