package fr.WarzouMc.PvPSwap.scorboard;

import fr.WarzouMc.PvPSwap.Main;
import fr.WarzouMc.PvPSwap.configSetup.GameSetup;
import fr.WarzouMc.PvPSwap.configSetup.State;
import org.bukkit.entity.Player;
import org.bukkit.scheduler.BukkitRunnable;

import java.util.HashMap;
import java.util.Map;

public class Updater extends BukkitRunnable {

    private Main main;

    public Updater(Main main){this.main = main;}

    private Map<Player, ScoreboardSign> board = new HashMap<>();

    @Override
    public void run() {

        board = ScoreboardCreater.getBoard();

        for(Map.Entry<Player, ScoreboardSign> sign : board.entrySet()){
            sign.getValue().setLine(1, "§6Game status : §4" + main.getState());
            sign.getValue().setLine(2, "§7");
            if(main.getState() == State.WAITING || main.getState() == State.STARTING){
                if(GameSetup.getPlayerList().size()/GameSetup.getMinPlayer() >= 1){
                    sign.getValue().setLine(3, "§1Player : §2" + GameSetup.getPlayerList().size() + "/" + GameSetup.getMinPlayer());
                }else{
                    sign.getValue().setLine(3, "§1Player : §4" + GameSetup.getPlayerList().size() + "/" + GameSetup.getMinPlayer());
                }
            }else{

            }
        }

    }
}
