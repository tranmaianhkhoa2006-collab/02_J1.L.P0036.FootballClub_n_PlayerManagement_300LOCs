package Business;

import Model.Player;
import Selection.PlayerType;
import Utils.Acceptable;
import Utils.ComparatorContainer;
import Utils.FileIOHandler;
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
     private ClubAndPlayerConnection apiClubManager;
     private final String PATH_FILE = "data/players.txt";
     
    public static String TABLE_HEADER = 
            ViewHandler.lineBreak(ViewHandler.PLAYER_TABLE_LENGTH)+
            ViewHandler.attributeOfPlayerList("Player ID","Player Name","Club Name","Shirt Number","Position")+
            ViewHandler.lineBreak(ViewHandler.PLAYER_TABLE_LENGTH);
    
    public void setApiClubManager(ClubAndPlayerConnection apiClubManager){
        this.apiClubManager = apiClubManager;
    }
    
   
    @Override
    public void show() {
        this.show(this.sortByComparator());
    }
    
    public void show(Collection<Player> filterData){
        if(filterData.isEmpty()){
            ViewHandler.print("There is no data or there is no player matches conditions to view!\n");
            return;
        }
        
        ViewHandler.print(TABLE_HEADER);
        for(Player player: filterData){
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
    
    public Collection<Player> searchByName(String name){
        List<Player> filterData = new ArrayList<>();
        for(Player player: this.sortByComparator()){
            if(Acceptable.isPartialEqual(player.getPlayerName(), name)){
                filterData.add(player);
            }
        }
        return filterData;
    }
    
    public Collection<Player> filterBySpecificPosition(PlayerType position){
        
        List<Player> filterData = new ArrayList<>();
        for(Player player: this.sortByComparator()){
            if(player.getPosition().equalsIgnoreCase(position.getPosition())){
                filterData.add(player);
            }
        }
       
        
        return filterData;
    }
    
    //default is sort by id
    public Collection<Player> sortByComparator(){
            List<Player> filterData = new ArrayList<>(super.getReadOnlyManagerList());
            filterData.sort(ComparatorContainer.sortAscendingByIdForPlayers);
            return filterData;
        }
        
    public Collection<Player> sortByComparator(Comparator<Player> sortingRule){
        List<Player> filterData = new ArrayList<>(this.sortByComparator());
            filterData.sort(sortingRule);
        
        return filterData;
    }
    
    @Override
    public String getPathFile() {
        return PATH_FILE;
    }
     
    @Override
    public boolean saveData(){
       Collection<Player> filterData = sortByComparator(ComparatorContainer.sortAscendingByIdForPlayers);
       List<String> listOfStringToSave = new ArrayList();
       for(Player player : filterData){
           listOfStringToSave.add(player.toSaveString());
       }
       
     
       boolean isSaveSuccess = FileIOHandler.writeStringFile(this.getPathFile(), listOfStringToSave);
       super.setSaveStatus(isSaveSuccess);
       return isSaveSuccess;
    }

    @Override
    public boolean loadData(){
        List<String> rawStringOfPlayers = FileIOHandler.readStringFile(this.getPathFile());
        List<Player> tempLoadData = new ArrayList<>();
        if(rawStringOfPlayers.isEmpty())
            return false;
        
        for(String rawStringlayer : rawStringOfPlayers){
             String pieceOfPlayerInfo[] = rawStringlayer.split(",");
             
             if (pieceOfPlayerInfo.length < 5) return false;
             
             for(String pieceInfo : pieceOfPlayerInfo){
                 if(pieceInfo.isEmpty()){
                     return false;
                 }
             }
             
             String playerId = pieceOfPlayerInfo[0].trim();
             String playerClubId = pieceOfPlayerInfo[1].trim();
             String playerName = pieceOfPlayerInfo[2].trim();
             PlayerType type = PlayerType.searchPlayerType(pieceOfPlayerInfo[3].trim());
             int shirtNumber = Integer.parseInt(pieceOfPlayerInfo[4].trim());
             
             
             Player player = Player.getNewPlayer(type).setApiClubManager(apiClubManager)
                                                                              .setPlayerId(playerId)
                                                                              .setPlayerName(playerName)
                                                                              .setClubId(playerClubId)
                                                                              .setShirtNumber(shirtNumber);
          
             tempLoadData.add(player);
        }
        
        super.clear();
        for(Player player: tempLoadData){
            super.add(player.getPlayerId(), player);
        }
        
        super.setSaveStatus(true);
        return true;
    }
}
