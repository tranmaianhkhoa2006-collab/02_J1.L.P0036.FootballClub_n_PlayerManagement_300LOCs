package Controller;

import Model.Player;
import Selection.PlayerType;
import Utils.Acceptable;
import Utils.ViewHandler;
import java.util.ArrayList;
import java.util.Collection;
import java.util.Comparator;
import java.util.List;

/**
 *
 * @author admin
 */
public class PlayerManager extends Manager<Player>{
    public static String TABLE_HEADER = 
            ViewHandler.lineBreak(ViewHandler.PLAYER_TABLE_LENGTH)+
            ViewHandler.attributeOfPlayerList("Player ID","Player Name","Club Name","Shirt Number","Position")+
            ViewHandler.lineBreak(ViewHandler.PLAYER_TABLE_LENGTH);
    
    @Override
    public void show() {
        this.show(super.getReadOnlyManagerList());
    }
    
    public Collection<Player> searchByName(String name){
        List<Player> filterData = new ArrayList<>();
        for(Player player: super.getReadOnlyManagerList()){
            if(Acceptable.isPartialEqual(player.getPlayerName(), name)){
                filterData.add(player);
            }
        }
        return filterData;
    }
    
    public Collection<Player> filterBySpecificPosition(PlayerType position){
        List<Player> filterData = new ArrayList<>();
        for(Player player: super.getReadOnlyManagerList()){
            if(player.getPosition().equals(position.getPosition())){
                filterData.add(player);
            }
        }
        return filterData;
    }
    
    public Collection<Player> sortByComparator(Comparator<Player> sortingRule){
        List<Player> filterData = new ArrayList<>(super.getReadOnlyManagerList());
        filterData.sort(sortingRule);
        return filterData;
    }
    
    @Override
    public String getPathFile() {
        return "data/Players.txt";
    }
    
    @Override
    public boolean saveData() {
        throw new UnsupportedOperationException("Not supported yet."); // Generated from nbfs://nbhost/SystemFileSystem/Templates/Classes/Code/GeneratedMethodBody
    }

    @Override
    public boolean loadData() {
        throw new UnsupportedOperationException("Not supported yet."); // Generated from nbfs://nbhost/SystemFileSystem/Templates/Classes/Code/GeneratedMethodBody
    }

    public void show(Collection<Player> data){
        ViewHandler.print(TABLE_HEADER);
        for(Player player: data){
            ViewHandler.print(
                    ViewHandler.attributeOfPlayerList(
                            player.getPlayerId(),
                            player.getPlayerName(),
                            player.getApiClubManager().getClubName(player.getClubId()),
                            player.getShirtNumber()+"",
                            player.getPosition()
                    )+
                 ViewHandler.lineBreak(ViewHandler.PLAYER_TABLE_LENGTH)
            );
        }
    } 
    
    
}
