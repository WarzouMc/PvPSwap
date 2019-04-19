package fr.WarzouMc.PvPSwap.configSetup;

import fr.WarzouMc.PvPSwap.Main;
import org.bukkit.Bukkit;
import org.bukkit.Location;

import java.util.ArrayList;
import java.util.List;

public class ConfigSetup {

    private static Main main;

    public ConfigSetup(Main main){this.main = main;}

    /***************
     **PlayerLimit**
     ***************/

    public static int getMaxPlayer(){
        return main.getConfig().getInt("MaxPlayer");
    }

    public static int getMinPlayer(){
        return main.getConfig().getInt("MinPlayer");
    }

    /**********
     **Timers**
     **********/

    public static int getAutoStartTimer(){
        return main.getConfig().getInt("startTimer");
    }

    public static int getDuelTimer(){
        return main.getConfig().getInt("duelTime");
    }

    /************
     **Location**
     ************/

    public static String getSpawnLocName(){
        return main.getConfig().getString("spawnLocation");
    }

    public static Location getSpawnLocation(){
        String world = main.getConfig().getString(getSpawnLocName() + ".world");
        double x = main.getConfig().getInt(getSpawnLocName() + ".x");
        double y = main.getConfig().getInt(getSpawnLocName() + ".y");
        double z = main.getConfig().getInt(getSpawnLocName() + ".z");
        float yaw = main.getConfig().getInt(getSpawnLocName() + ".yaw");
        float pitch = main.getConfig().getInt(getSpawnLocName() + ".pitch");
        Location spawn = new Location(Bukkit.getWorld(world), x, y, z, yaw, pitch);
        return spawn;
    }

    public static List<String> getDuelLocationsName(){
        List<String> l = new ArrayList<>();
        List<String> locName = main.getConfig().getStringList("pvpLocation");
        for(int i = 0; i < locName.size(); i++){
            l.add(locName.get(i));
        }
        return l;
    }

    public static List<Location> getDuelLocation(){
        List<Location> l = new ArrayList<>();
        List<String> loc = getDuelLocationsName();
        for(int i = 0; i < loc.size(); i++){
            String world = main.getConfig().getString(loc.get(i) + ".world");
            double x = main.getConfig().getInt(loc.get(i) + ".x");
            double y = main.getConfig().getInt(loc.get(i) + ".y");
            double z = main.getConfig().getInt(loc.get(i) + ".z");
            float yaw = main.getConfig().getInt(loc.get(i) + ".yaw");
            float pitch = main.getConfig().getInt(loc.get(i) + ".pitch");
            Location location = new Location(Bukkit.getWorld(world), x, y, z, yaw, pitch);
            l.add(location);
        }
        return l;
    }

    /**********
     **Blocks**
     **********/

    public static List<Integer> getBlock(){
        return main.getConfig().getIntegerList("unbreackable");
    }

    public static List<Integer> getBlockDrop(int id){
        if(main.getConfig().contains("blockDrop." + id)){
            return main.getConfig().getIntegerList("blockDrop." + id);
        }else{
            return null;
        }
    }

}
