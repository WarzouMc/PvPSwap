package fr.WarzouMc.PvPSwap.plugin;

import fr.WarzouMc.PvPSwap.Main;
import fr.WarzouMc.PvPSwap.configSetup.GameSetup;
import fr.WarzouMc.PvPSwap.configSetup.State;
import fr.WarzouMc.PvPSwap.gameLoop.GAutoStart;
import org.bukkit.*;
import org.bukkit.block.Block;
import org.bukkit.entity.Entity;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.block.BlockBreakEvent;
import org.bukkit.event.block.BlockPlaceEvent;
import org.bukkit.event.entity.EntityDamageByEntityEvent;
import org.bukkit.event.entity.EntityDamageEvent;
import org.bukkit.event.player.PlayerChatEvent;
import org.bukkit.event.player.PlayerJoinEvent;
import org.bukkit.event.player.PlayerMoveEvent;
import org.bukkit.event.player.PlayerQuitEvent;
import org.bukkit.inventory.ItemStack;

import java.util.List;
import java.util.UUID;

public class PluginManager extends GameSetup implements Listener {

    private Main main;

    public PluginManager(Main main) {this.main = main;}

    private String msgPrefixError = "§4[§cPvPSwap§4] §2>> §r";
    private String msgPrefixSimple = "§1[§9PvPSwap§1] §2>> §r";

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
    public void onDamage(EntityDamageByEntityEvent event){

        Entity entity = event.getEntity();

        if(entity instanceof Player){

            String playerName = entity.getName();
            UUID playerUUID = entity.getUniqueId();

            if(main.isState() == State.WAITING || main.isState() == State.STARTING || main.isState() == State.WINING || main.isState() == State.FINISH){
                event.setCancelled(true);
            }

        }

    }

    /**************
     **BlockBreak**
     **************/

    @EventHandler
    public void onBlockBreak(BlockBreakEvent event){

        Block b = event.getBlock();

        Player player = event.getPlayer();
        String playerName = player.getName();
        UUID playerUUID = player.getUniqueId();

        if(main.isState() == State.WAITING || main.isState() == State.STARTING || main.isState() == State.WINING || main.isState() == State.FINISH){
            event.setCancelled(true);
        }

        List<Integer> block = getBlock();
        for(int i = 0; i < block.size(); i++){
            if(b.getTypeId() == block.get(i)){
                event.setCancelled(true);
                player.sendMessage(msgPrefixError+"§4You can't break this block !");
                playSoundGamer(Sound.ITEM_PICKUP);
                Location loc = new Location(b.getWorld(), b.getX() + 0.5, b.getY() + 0.5, b.getZ() + 0.5);
                player.playEffect(loc, Effect.SMOKE, 1);
            }
        }

        if(getBlockDrop(b.getTypeId()) != null){
            int id = getBlockDrop(b.getTypeId()).get(0);
            int amount = getBlockDrop(b.getTypeId()).get(2);
            int data = getBlockDrop(b.getTypeId()).get(1);
            b.getLocation().getWorld().dropItemNaturally(b.getLocation(), new ItemStack(id, amount, (byte)data));
        }

    }

    /**************
     **BlockPlace**
     **************/

    @EventHandler
    public void onBlockPlace(BlockPlaceEvent event){

        Block b = event.getBlock();

        Player player = event.getPlayer();
        String playerName = player.getName();
        UUID playerUUID = player.getUniqueId();

        if(main.isState() == State.WAITING || main.isState() == State.STARTING || main.isState() == State.WINING || main.isState() == State.FINISH){
            event.setCancelled(true);
        }

        List<Integer> block = getBlock();
        for(int i = 0; i < block.size(); i++){
            if(b.getTypeId() == block.get(i)){
                event.setCancelled(true);
                player.sendMessage(msgPrefixError+"§4You can't place this block !");
                playSoundGamer(Sound.ITEM_PICKUP);
                Location loc = new Location(b.getWorld(), b.getX() + 0.5, b.getY() + 0.5, b.getZ() + 0.5);
                player.playEffect(loc, Effect.SMOKE, 1);
            }
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
