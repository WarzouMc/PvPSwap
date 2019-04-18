package fr.WarzouMc.PvPSwap.plugin;

import fr.WarzouMc.PvPSwap.Main;
import fr.WarzouMc.PvPSwap.configSetup.GameSetup;
import fr.WarzouMc.PvPSwap.configSetup.State;
import fr.WarzouMc.PvPSwap.gameLoop.GAutoStart;
import org.bukkit.GameMode;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.block.BlockBreakEvent;
import org.bukkit.event.block.BlockPlaceEvent;
import org.bukkit.event.entity.EntityDamageEvent;
import org.bukkit.event.player.PlayerChatEvent;
import org.bukkit.event.player.PlayerJoinEvent;
import org.bukkit.event.player.PlayerMoveEvent;
import org.bukkit.event.player.PlayerQuitEvent;

import java.util.UUID;

public class PluginManager extends GameSetup implements Listener {

    private Main main;

    public PluginManager(Main main) {this.main = main;}

    /*************
     **JoinEvent**
     *************/

    @EventHandler
    public void onJoin(PlayerJoinEvent event){

        Player player = event.getPlayer();
        String playerName = player.getName();
        UUID playerUUID = player.getUniqueId();

        //Set Player
        setPlayer(player);

        if(main.isState() == State.WAITING){
            player.setGameMode(GameMode.ADVENTURE);
            addInPlayerList(playerUUID);
            setXP();
        }else{
            player.setGameMode(GameMode.SPECTATOR);
            player.setPlayerListName("");
            player.sendTitle("§4The game has already starting", "§6You are spectator !");
        }

        if(getPlayerList().size() >= getMinPlayer() && main.isState() == State.WAITING){
            main.setState(State.STARTING);
            GAutoStart autoStart = new GAutoStart(main);
            autoStart.runTaskTimer(main, 0, 1);
        }
    }

    /*************
     **QuitEvent**
     *************/

    @EventHandler
    public void onQuit(PlayerQuitEvent event){

        Player player = event.getPlayer();
        String playerName = player.getName();
        UUID playerUUID = player.getUniqueId();

        removeInPlayerList(playerUUID);
        setXP();

    }

    /*************
     **ChatEvent**
     *************/

    public void onChat(PlayerChatEvent event){

        Player player = event.getPlayer();
        String playerName = player.getName();
        UUID playerUUID = player.getUniqueId();

        if(main.isState() != State.WINING && main.isState() != State.FINISH){
            if(!containPlayerInList(playerUUID)){
                event.setCancelled(true);
            }
        }

    }

    /***************
     **DamageEvent**
     ***************/

    @EventHandler
    public void onDamage(EntityDamageEvent event){

        Player player = (Player)event.getEntity();
        String playerName = player.getName();
        UUID playerUUID = player.getUniqueId();

        if(main.isState() == State.WAITING || main.isState() == State.STARTING || main.isState() == State.WINING || main.isState() == State.FINISH){
            event.setCancelled(true);
        }

    }

    /**************
     **BlockBreak**
     **************/

    @EventHandler
    public void onBlockBreak(BlockBreakEvent event){

        if(main.isState() == State.WAITING || main.isState() == State.STARTING || main.isState() == State.WINING || main.isState() == State.FINISH){
            event.setCancelled(true);
        }

    }

    /**************
     **BlockPlace**
     **************/

    @EventHandler
    public void onBlockPlace(BlockPlaceEvent event){

        if(main.isState() == State.WAITING || main.isState() == State.STARTING || main.isState() == State.WINING || main.isState() == State.FINISH){
            event.setCancelled(true);
        }

    }

    /*************
     **MoveEvent**
     *************/

    @EventHandler
    public void onMove(PlayerMoveEvent event){

        Player player = event.getPlayer();
        String playerName = player.getName();
        UUID playerUUID = player.getUniqueId();

        if(main.isState() == State.WAITING || main.isState() == State.STARTING || main.isState() == State.WINING || main.isState() == State.FINISH){
            player.setFoodLevel(20);
            player.setHealth(20);
        }

    }

}
