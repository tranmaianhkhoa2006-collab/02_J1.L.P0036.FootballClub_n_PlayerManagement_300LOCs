package Controller;

import Model.Club;
import Model.ClubPlayerInterface;
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
public class ClubManager extends Manager<Club> implements ClubPlayerInterface{

    public final static String TABLE_HEADER =
            ViewHandler.lineBreak(ViewHandler.CLUB_TABLE_LENGTH)+
            ViewHandler.attributeOfClubList("Club ID", "Club name","Sponsor Brand","Budget","Numbers of players")+
            ViewHandler.lineBreak(ViewHandler.CLUB_TABLE_LENGTH); 
    @Override
    public void show() {
        
       this.show(this.sortByComparator());
    }

    public Collection<Club> filterByBudgetValue(double value){
        List<Club> returnData = new ArrayList<>();
        for(Club club: this.sortByComparator()){
            if(club.getBudget()<=value)
                returnData.add(club);
        }
        
        return returnData;
    }
    
      public Collection<Club> sortByComparator (){
           List<Club> filterData = new ArrayList<>(super.getReadOnlyManagerList());
        filterData.sort(ComparatorContainer.sortAscendingByIdForClubs);
        return filterData;
      }
      public Collection<Club> sortByComparator(Comparator<Club> sortingRule){
        List<Club> filterData = new ArrayList<>(this.sortByComparator());
            filterData.sort(sortingRule);
        return filterData;
      }
   
    @Override
    public String getClubName(String clubId) {
        return super.search(clubId.toUpperCase()).getClubName();
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
        Collection<Club> filterData = sortByComparator(ComparatorContainer.sortAscendingByIdForClubs);
       List<String> listOfStringToSave = new ArrayList();
       for(Club club : filterData){
           listOfStringToSave.add(club.toSaveString());
       }
       
       return FileIOHandler.writeStringFile(this.getPathFile(), listOfStringToSave);
       
    }

    @Override
    public boolean loadData() {
      List<String> rawStringOfClubs = FileIOHandler.readStringFile(this.getPathFile());
        if(rawStringOfClubs.isEmpty())
            return true;
        
        for(String rawStringClub : rawStringOfClubs){
             String[] pieceOfClubInfo = rawStringClub.split(",");
             
             for(String pieceInfo : pieceOfClubInfo){
                 if(pieceInfo.isEmpty())
                     return false;
             }
              
             String clubId = pieceOfClubInfo[0].trim();
             String clubName = pieceOfClubInfo[1].trim();
             String sponsorBrand = pieceOfClubInfo[2].trim();
             double budget = Double.parseDouble(pieceOfClubInfo[3]);
             
             Club club = Club.createNewClub()
                                           .setClubId(clubId)
                                           .setClubName(clubName)
                                           .setSponsorBrand(sponsorBrand)
                                           .setBudget(budget);
             
             super.add(clubId.toUpperCase(), club);
        }
        
        return true;
    }
    
    @Override
    public String getPathFile() {
        return "data/clubs.txt";
    }
    
    public void show(Collection<Club> filterData){  
        if(filterData.isEmpty()){
            ViewHandler.print("There is no data or there is no club matches conditions to view!\n");
            return;
        }
        
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

    @Override
    public boolean isContainShirtNumber(String clubId, int nums) {
        Club club = this.search(clubId);
        if(club ==null)
            return false;
        
        return club.isContainShirtNumber(nums);
        
    }
    
    
}
