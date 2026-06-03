package Model;

import Utils.ViewHandler;
import java.util.Objects;
import java.util.TreeMap;

/**
 *
 * @author admin
 */
public class Club {
    private String clubId;
    private String clubName;
    private String sponsorBrand;
    private double budget;
    private TreeMap<Integer,String> dataOfShirtNumber = new TreeMap<>();
    
    public String getClubId() {
        return clubId;
    }

    public Club setClubId(String clubId) {
        this.clubId = clubId.toUpperCase();
        return this;
    }

    public String getClubName() {
        return clubName;
    }

    public Club setClubName(String clubName) {
        this.clubName = ViewHandler.nameFormatter(clubName);
        return this;
    }

    public String getSponsorBrand() {
        return sponsorBrand;
    }

    public Club setSponsorBrand(String sponsorBrand) {
        this.sponsorBrand = ViewHandler.nameFormatter(sponsorBrand);
        return this;
    }

    public double getBudget() {
        return budget;
    }

    public Club setBudget(double budget) {
        this.budget = budget;
        return this;
    }
    
    public boolean isContainShirtNumber(int number){
        return this.dataOfShirtNumber.containsKey(number);
    }
    
    public int getNumberOfPlayer(){
        return this.dataOfShirtNumber.size();
    }
    
    public boolean addShirtNumber(int number,String id){
    
            this.dataOfShirtNumber.put(number, id);
            return true;
        
    }

    @Override
    public int hashCode() {
       int hash = (this.clubId.charAt(clubId.length()-1)-'0') % 9 ;
        return hash;
    }

    @Override
    public boolean equals(Object obj) {
       if(obj instanceof Club)
           return this.clubId.equals(((Club) obj).clubId);
       
       return false;
    }
            
    
    
    
     public boolean deleteShirtNumber(int shirtNumber){
        return this.dataOfShirtNumber.remove(shirtNumber)!=null;
    }
      
    public boolean updateShirtNumber(int oldNums, int newNums){
                String thisPlayerID = this.dataOfShirtNumber.remove(oldNums);
                return this.addShirtNumber(newNums, thisPlayerID);
    }
    
    public String toString(){
        return "Club ID: "+this.clubId+"\n"+
                   "Club name: "+this.clubName+"\n"+
                   "Sponsor Brand: "+this.sponsorBrand+"\n"+
                   "Budget: "+this.budget+"\n"+
                   "Numbers of Players: "+this.getNumberOfPlayer();
        
    }
    
    public String toSaveString(){
        return clubId+", "+clubName+", "+sponsorBrand+", "+budget;
    }

    
    
    public static Club createNewClub(){
        return new Club();
    }
    
}
