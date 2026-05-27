package Controller;

import Model.Club;
import CallBackInterface.ClubPlayerInterface;
import Utils.ViewHandler;
import java.util.ArrayList;
import java.util.Collection;
import java.util.List;

/**
 *
 * @author admin
 */
public class ClubManager extends Manager<Club> implements ClubPlayerInterface{

    public final static String TABLE_HEADER =
            ViewHandler.lineBreak(ViewHandler.CLUB_TABLE_LENGTH)+
            ViewHandler.attributeOfClubList("Club ID", "Club name","Sponsor Brand","Budget","Numbers of players")+
            ViewHandler.lineBreak(ViewHandler.CLUB_TABLE_LENGTH); 
    @Override
    public void show() {
       this.show(super.getReadOnlyManagerList());
    }

    public Collection<Club> filterByBudgetValue(double value){
        List<Club> returnData = new ArrayList<>();
        for(Club club: super.getReadOnlyManagerList()){
            if(club.getBudget()<=value)
                returnData.add(club);
        }
        
        return returnData;
    }
   
    @Override
    public String getClubName(String clubId) {
        return super.search(clubId).getClubName();
    }

    @Override
    public boolean addShirtNumber(String clubId,String playerId, int nums) {
       Club club = super.search(clubId);
       if(club.addShirtNumber(nums,playerId))
            return super.update(clubId, club);
       else 
           return false;
    }

    @Override
    public boolean updateShirtNumber(String clubId, int oldNums, int newNums) {
        Club club = super.search(clubId);
        if(club == null)
            return false;
        
        return club.updateShirtNumber(oldNums, newNums);
    }

    @Override
    public boolean deleteShirtNumber(String clubId, int nums) {
        Club club = super.search(clubId); 
        return club.deleteShirtNumber(nums);
    }

    @Override
    public boolean saveData() {
        throw new UnsupportedOperationException("Not supported yet."); // Generated from nbfs://nbhost/SystemFileSystem/Templates/Classes/Code/GeneratedMethodBody
    }

    @Override
    public boolean loadData() {
        throw new UnsupportedOperationException("Not supported yet."); // Generated from nbfs://nbhost/SystemFileSystem/Templates/Classes/Code/GeneratedMethodBody
    }
    
    @Override
    public String getPathFile() {
        return "data/clubs.txt";
    }
    
    public void show(Collection<Club> filterData){  
        ViewHandler.print(ClubManager.TABLE_HEADER);
        for(Club club: filterData){   
            ViewHandler.print(
                    ViewHandler.attributeOfClubList(
                            club.getClubId(), 
                            club.getClubName(), 
                            club.getSponsorBrand(),
                            club.getBudget()+"",
                            club.getNumberOfPlayer()+""
                    )+
                    ViewHandler.lineBreak(ViewHandler.CLUB_TABLE_LENGTH)
            );
        }
    }
    
    
}
