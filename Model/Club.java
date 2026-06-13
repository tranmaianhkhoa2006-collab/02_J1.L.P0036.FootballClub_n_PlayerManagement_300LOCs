package Model;

import Utils.ViewHandler;

/**
 *
 * @author admin
 */
public class Club {
    private String clubId;
    private String clubName;
    private String sponsorBrand;
    private double budget;
   
    
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
        this.clubName =clubName;
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
    
    //Only 9 bucket from (0->8) in hashTable
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
    
    @Override
    public String toString(){
        return "Club ID: "+this.clubId+"\n"+
                   "Club name: "+this.clubName+"\n"+
                   "Sponsor Brand: "+this.sponsorBrand+"\n"+
                   "Budget: "+this.budget+"\n";     
    }
    
    public String toSaveString(){
        return clubId+", "+clubName+", "+sponsorBrand+", "+String.format("%.0f", budget);
    }

    public static Club createNewClub(){
        return new Club();
    }
    
}
