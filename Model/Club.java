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

    @Override
    public int hashCode() {
        int hash = 7;
        return hash;
    }

    @Override
    public boolean equals(Object obj) {
        if (this == obj) {
            return true;
        }
        if (obj == null) {
            return false;
        }
        if (getClass() != obj.getClass()) {
            return false;
        }
        final Club other = (Club) obj;
        return Objects.equals(this.clubId, other.clubId);
    }
   
    
    
    public static Club createNewClub(){
        return new Club();
    }
    
}
