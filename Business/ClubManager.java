package Business;

import Model.Club;
import Utils.ComparatorContainer;
import Utils.FileIOHandler;
import Utils.ViewHandler;
import java.util.ArrayList;
import java.util.Collection;
import java.util.Comparator;
import java.util.HashMap;
import java.util.List;

/**
 *
 * @author admin
 */
public class ClubManager extends Manager<Club> implements ClubAndPlayerConnection{
    private final String PATH_FILE = "data/clubs.txt";
    private HashMap<String, HashMap<Integer,String>> dataOfShirtNumber = new HashMap<>();
    
    public final static String TABLE_HEADER =
            ViewHandler.lineBreak(ViewHandler.CLUB_TABLE_LENGTH)+
            ViewHandler.attributeOfClubList("Club ID", "Club name","Sponsor Brand","Budget","Numbers of players")+
            ViewHandler.lineBreak(ViewHandler.CLUB_TABLE_LENGTH); 
    @Override
    public void show() {
        
       this.show(this.sortByComparator());
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
                            dataOfShirtNumber.get(club.getClubId()).size()+""
                    )+
                    ViewHandler.lineBreak(ViewHandler.CLUB_TABLE_LENGTH)
            );
        }
    }
      
    public Collection<Club> filterByBudgetValue(double maxBudget){
        List<Club> returnData = new ArrayList<>();
        for(Club club: this.sortByComparator()){
            if(club.getBudget()<=maxBudget)
                returnData.add(club);
        }
        
        return returnData;
    }
    
    //block from delete club by controller
    @Override
    public boolean remove(String id){
       try{ 
         throw new UnsupportedOperationException("Clubs cannot be deleted");
       }
       catch(UnsupportedOperationException e){
           FileIOHandler.logWriter(e+"-"+e.getMessage());
       }
       return false;
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
    public boolean saveData() {
        Collection<Club> filterData = sortByComparator(ComparatorContainer.sortAscendingByIdForClubs);
       List<String> listOfStringToSave = new ArrayList();
       for(Club club : filterData){
           listOfStringToSave.add(club.toSaveString());
       }
       
       return FileIOHandler.writeStringFile(this.getPathFile(), listOfStringToSave);
       
    }

    @Override
    public boolean loadData(){
      List<String> rawStringOfClubs = FileIOHandler.readStringFile(this.getPathFile());
      List<Club> tempLoadData = new ArrayList<>();
        if(rawStringOfClubs.isEmpty())
            return false;
        
        for(String rawStringClub : rawStringOfClubs){
             String[] pieceOfClubInfo = rawStringClub.split(",");
             
             if (pieceOfClubInfo.length < 4) return false;
             
             for(String pieceInfo : pieceOfClubInfo){
                 if(pieceInfo.isEmpty()){
                     return false;
                 }
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
             
             tempLoadData.add(club);
        }
        
        super.clear();
        for(Club club: tempLoadData){
            super.add(club.getClubId(), club);
        }
        
        super.setSaveStatus(true);
        return true;
    }
    
    @Override
    public String getPathFile() {
        return PATH_FILE;
    }
   
    @Override
    public String getClubName(String clubId) {
        Club club = super.search(clubId.toUpperCase());
        if(club == null)
            return "Not exist club";
        return club.getClubName();
    }

    @Override
    public boolean addShirtNumber(String clubId,String playerId, int nums) {
        HashMap<Integer, String> currentShirtNumberData = dataOfShirtNumber.get(clubId);
        if(currentShirtNumberData==null)
            return false;
        
        if(currentShirtNumberData.containsKey(nums))
            return false;
        
        currentShirtNumberData.put(nums, playerId);
        return true;
    }

    @Override
    public boolean updateShirtNumber(String clubId, int oldNums, int newNums) {
         HashMap<Integer, String> currentShirtNumberData = dataOfShirtNumber.get(clubId);
        if(currentShirtNumberData==null)
            return false;
        
        if(!currentShirtNumberData.containsKey(oldNums))
            return false;
        
        if(currentShirtNumberData.containsKey(newNums))
            return false;
         
         currentShirtNumberData.put(newNums, currentShirtNumberData.get(oldNums));
         currentShirtNumberData.remove(oldNums);
       
         return true;
    }
    
    @Override
    public boolean deleteShirtNumber(String clubId, int nums) {
        HashMap<Integer, String> currentShirtNumberData = dataOfShirtNumber.get(clubId);
        if(currentShirtNumberData==null)
            return false;
        
        if(!currentShirtNumberData.containsKey(nums))
            return false;
        
        return dataOfShirtNumber.remove(nums)!=null;
    }
    
    @Override
    public boolean isContainShirtNumber(String clubId, int nums) {
          HashMap<Integer, String> currentShirtNumberData = dataOfShirtNumber.get(clubId);
        if(currentShirtNumberData==null)
            return false;
        
        return currentShirtNumberData.containsKey(nums);
    }
    
    
}
