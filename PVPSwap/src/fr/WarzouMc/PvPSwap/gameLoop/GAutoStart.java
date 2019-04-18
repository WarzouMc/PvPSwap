package fr.WarzouMc.PvPSwap.gameLoop;

import fr.WarzouMc.PvPSwap.Main;
import fr.WarzouMc.PvPSwap.configSetup.ConfigSetup;
import fr.WarzouMc.PvPSwap.configSetup.GameSetup;
import fr.WarzouMc.PvPSwap.configSetup.State;
import org.bukkit.Bukkit;
import org.bukkit.Sound;
import org.bukkit.scheduler.BukkitRunnable;

public class GAutoStart extends BukkitRunnable {

    private int timer = 30;
    private int start = 1;
    private int tick = 20;

    private Main main;

    public GAutoStart(Main main) {this.main = main;}

    @Override
    public void run() {

        if(start == 1){
            start--;
            timer = ConfigSetup.getAutoStartTimer();
        }

        if(GameSetup.getPlayerList().size() < GameSetup.getMinPlayer()){
            main.setState(State.WAITING);
            GameSetup.sendTitleGamer("§4Need player !", "§6Restart soon !");
            cancel();
        }

        if(tick == 20){
            if(timer == ConfigSetup.getAutoStartTimer()){
                GameSetup.playSoundGamer(Sound.SUCCESSFUL_HIT);
                GameSetup.sendTitleGamer("§1Game start in : §f§o" + timer + "s", "§2Good luck");
            }else if(timer == 30 || timer == 20){
                GameSetup.playSoundGamer(Sound.SUCCESSFUL_HIT);
                GameSetup.sendTitleGamer("§1Game start in : §2§o" + timer + "s", "§2Good luck");
            }else if(timer == 15 || timer == 10){
                GameSetup.playSoundGamer(Sound.SUCCESSFUL_HIT);
                GameSetup.sendTitleGamer("§1Game start in : §e§o" + timer + "s", "§2Good luck");
            }else if(timer == 5 || timer == 4){
                GameSetup.playSoundGamer(Sound.SUCCESSFUL_HIT);
                GameSetup.sendTitleGamer("§1Game start in : §6§o" + timer + "s", "§2Good luck");
            }else if(timer == 3){
                GameSetup.playSoundGamer(Sound.SUCCESSFUL_HIT);
                GameSetup.sendTitleGamer("§1Game start in : §c§o" + timer + "s", "§2Good luck");
            }else if(timer == 2){
                GameSetup.sendTitleGamer("§1Game start in : §c§o" + timer + "s", "§2Good luck");
                GameSetup.playSoundGamer(Sound.SUCCESSFUL_HIT);
            }else if(timer == 1){
                GameSetup.sendTitleGamer("§1Game start in : §4§o" + timer + "s", "§2Good luck");
                GameSetup.playSoundGamer(Sound.SUCCESSFUL_HIT);
            }else if(timer == 0){
                GameSetup.sendTitleGamer("§1Game start in : §4§o" + timer + "s", "§2Good luck");
                GameSetup.playSoundGamer(Sound.ENDERMAN_DEATH);
                GameSetup.tpGamer();
            }
            Bukkit.broadcastMessage(timer + "");
            tick = 0;
            timer--;
            if(timer == -1){
                main.setState(State.DUEL);
                cancel();
            }
        }
        tick++;
    }
}
