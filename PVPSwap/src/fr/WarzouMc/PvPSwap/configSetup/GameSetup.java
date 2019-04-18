package fr.WarzouMc.PvPSwap.configSetup;

import org.bukkit.Bukkit;
import org.bukkit.GameMode;
import org.bukkit.Location;
import org.bukkit.Sound;
import org.bukkit.entity.Player;

import java.util.ArrayList;
import java.util.List;
import java.util.UUID;

public class GameSetup {

    /**************
     **ListConfig**
     **************/

    private static List<UUID> playerList = new ArrayList<>();

    public static void addInPlayerList(UUID uuid){
        playerList.add(uuid);
    }

    public static void removeInPlayerList(UUID uuid){
        playerList.remove(uuid);
    }

    public static List<UUID> getPlayerList(){
        return playerList;
    }

    public static boolean containPlayerInList(UUID uuid){
        if(playerList.contains(uuid)){
            return true;
        }else{
            return false;
        }
    }

    /****************
     **Gamer Action**
     ****************/

    public void setPlayer(Player player) {
        Location spawn = ConfigSetup.getSpawnLocation();

        player.teleport(spawn);
        player.setFoodLevel(20);
        player.setHealth(20);
        player.setLevel(0);
        player.setExp(0);
    }

    public void setXP(){
        for(int i = 0; i < getPlayerList().size(); i++){
            Player player = Bukkit.getPlayer(getPlayerList().get(i));
            float xp = (float)getPlayerList().size()/getMaxPlayer();
            player.setLevel(getPlayerList().size());
            player.setExp(xp);
        }
    }

    public static void sendTitleGamer(String title, String subtitle) {
        for(int i = 0; i < getPlayerList().size(); i++){
            Player player = Bukkit.getPlayer(getPlayerList().get(i));
            player.sendTitle(title, subtitle);
        }
    }

    public static void playSoundGamer(Sound s) {
        for(int i = 0; i < getPlayerList().size(); i++){
            Player player = Bukkit.getPlayer(getPlayerList().get(i));
            player.playSound(player.getLocation(), s, 10, 1);
        }
    }

    public static void tpGamer() {
        List<Location> locTp = ConfigSetup.getDuelLocation();
        for(int i = 0; i < getPlayerList().size(); i++){
            Player player = Bukkit.getPlayer(getPlayerList().get(i));
            Location loc = new Location(locTp.get(i).getWorld(), locTp.get(i).getX(), locTp.get(i).getY(), locTp.get(i).getZ(), locTp.get(i).getYaw(), locTp.get(i).getPitch());
            player.teleport(loc);
            player.setExp(0);
            player.setLevel(0);
            player.setGameMode(GameMode.SURVIVAL);
        }
        if(getPlayerList().size()%2 == 1){
            Player player = Bukkit.getPlayer(getPlayerList().get(getPlayerList().size()-1));
            player.sendTitle("§4Not enough player", "§6Sorry !");
            player.setGameMode(GameMode.SPECTATOR);
            removeInPlayerList(getPlayerList().get(getPlayerList().size()-1));
        }
    }

    /****************
     **getterConfig**
     ****************/

    public static int getMaxPlayer(){
        return ConfigSetup.getMaxPlayer();
    }

    public static int getMinPlayer(){
        return ConfigSetup.getMinPlayer();
    }

    public static int getAutoStartTimer(){
        return getAutoStartTimer();
    }

    /**********
     **Blocks**
     **********/

    public static List<Integer> getBlock(){
        return ConfigSetup.getBlock();
    }

    public static List<Integer> getBlockDrop(int id){
        return ConfigSetup.getBlockDrop(id);
    }

}
