package fr.WarzouMc.PvPSwap.gameLoop;

import fr.WarzouMc.PvPSwap.Main;
import fr.WarzouMc.PvPSwap.configSetup.GameSetup;
import org.bukkit.scheduler.BukkitRunnable;

public class GDuel extends BukkitRunnable {

    private int time = 300;
    private int start = 1;
    private int tick = 20;

    private Main main;

    public GDuel(Main main) {this.main = main;}

    @Override
    public void run(){

        if(start == 1){
            start--;
            time = GameSetup.getDuelTime();
        }

        if(tick == 20){

            time--;
            tick = 0;
            if(time == -1){
                GameSetup.sendTitleGamer("§4Game §1Swap §6", "");
                GSwap gSwap = new GSwap(main);
                gSwap.runTaskTimer(main, 0, 1);
                cancel();
            }
        }

    }

}
