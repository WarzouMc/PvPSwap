package fr.WarzouMc.PvPSwap.scorboard;

import fr.WarzouMc.PvPSwap.Main;
import fr.WarzouMc.PvPSwap.configSetup.GameSetup;
import fr.WarzouMc.PvPSwap.configSetup.State;
import org.bukkit.entity.Player;

import java.util.HashMap;
import java.util.Map;

public class ScoreboardCreater extends GameSetup {

    private Main main;

    public ScoreboardCreater(Main main){this.main = main;}

    private static Map<Player, ScoreboardSign> board = new HashMap<>();

    public void Creater(Player player){

        String playerName = player.getName();

        ScoreboardSign sb = new ScoreboardSign(player, "§6PvPSwap");

        sb.create();

        sb.setLine(1, "§6Game status : §4" + main.getState());
        sb.setLine(2, "§7");
        if(main.getState() == State.WAITING || main.getState() == State.STARTING){
            if(getPlayerList().size()/getMinPlayer() >= 1){
                sb.setLine(3, "§1Player : §2" + getPlayerList().size() + "/" + getMinPlayer());
            }else{
                sb.setLine(3, "§1Player : §4" + getPlayerList().size() + "/" + getMinPlayer());
            }
        }else{

        }

        board.put(player, sb);

    }


    public static Map<Player, ScoreboardSign> getBoard() {
        return board;
    }

    public static void setBoard(Map<Player, ScoreboardSign> board) {
        ScoreboardCreater.board = board;
    }

}
