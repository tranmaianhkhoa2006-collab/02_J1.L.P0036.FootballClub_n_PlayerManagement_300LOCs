package Model;

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

    public Club(String clubId, String clubName, String sponsorBrand) {
        this.clubId = clubId;
        this.clubName = clubName;
        this.sponsorBrand = sponsorBrand;
    }

    
    public String getClubId() {
        return clubId;
    }

    public Club setClubId(String clubId) {
        this.clubId = clubId;
        return this;
    }

    public String getClubName() {
        return clubName;
    }

    public Club setClubName(String clubName) {
        this.clubName = clubName;
        return this;
    }

    public String getSponsorBrand() {
        return sponsorBrand;
    }

    public Club setSponsorBrand(String sponsorBrand) {
        this.sponsorBrand = sponsorBrand;
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
        return dataOfShirtNumber.put(number, id)!=null;
    }
    
     public boolean deleteShirtNumber(int shirtNumber){
        return this.dataOfShirtNumber.remove(shirtNumber)!=null;
    }
      
    public boolean updateShirtNumber(int oldNums, int newNums){
       boolean isContainOldNumsAndIsNewNumsEmptyInClub = 
             this.isContainShirtNumber(oldNums) && 
               !this.isContainShirtNumber(newNums);
       
       if(isContainOldNumsAndIsNewNumsEmptyInClub){
                String thisPlayerID = this.dataOfShirtNumber.remove(oldNums);
                return this.addShirtNumber(newNums, thisPlayerID);
       }
       else 
           return false;
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
   
    public static Club createNewClub(String clubId,String clubName,String sponsorBrand){
        return new Club(clubId, clubName, sponsorBrand);
    }
    
}
