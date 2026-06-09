package Model;

import Selection.PlayerType;
import Utils.ViewHandler;
import Business.ClubAndPlayerConnection;
import Utils.FileIOHandler;

/**
 *
 * @author admin
 */
public abstract class Player {
    private String playerId;
    private String playerName;
    private String clubId;
    private int shirtNumber;
    private ClubAndPlayerConnection apiClubManager;


    public Player(){
        playerId = "";
        playerName="";
        clubId="";
        shirtNumber=0;
    }
    
    
    
    public String getPlayerId() {
        return playerId;
    }
    
    public Player setPlayerId(String id){
        this.playerId = id.toUpperCase();
        return this;
    }
    
    public String getPlayerName() {
        return playerName;
    }

    public Player setPlayerName(String playerName) {
        this.playerName = ViewHandler.nameFormatter(playerName);
         return this;
    }

    public String getClubId() {
        return clubId;
    }

    public Player setClubId(String clubId) {
        this.clubId = clubId.toUpperCase();
         return this;
    }

    public int getShirtNumber() {
        return shirtNumber;
    }
   
    public Player setShirtNumber(int shirtNumber) {
        //Player need to link with Club Manager through interface
        boolean isClubApiLink = (apiClubManager !=null);
        if(!isClubApiLink){
            FileIOHandler.logWriter("API MISSING!");
            ViewHandler.printError("Something occur during set shirt number!\nPlease contact dev for support\n");
            return this;
        }
        
        boolean isFirstTimeSetShirtNumber = this.shirtNumber == 0 && !this.clubId.isEmpty();
       
        if(isFirstTimeSetShirtNumber){
                this.apiClubManager.addShirtNumber(clubId,playerId, shirtNumber);
                this.shirtNumber = shirtNumber;
                return this;
       }//set for first time case 

            //if club update success? set shirtNumber
        else{
            boolean isUpdatedShirtNumberInClubSuccess = apiClubManager.updateShirtNumber(this.clubId, this.shirtNumber, shirtNumber);
                 
            if(isUpdatedShirtNumberInClubSuccess)
                     this.shirtNumber = shirtNumber;
             }
            //set for remaininng time
       
        return this;
    }

    public ClubAndPlayerConnection getApiClubManager() {
        return apiClubManager;
    }
    
    public Player setApiClubManager(ClubAndPlayerConnection apiClubManager){
            this.apiClubManager = apiClubManager;
        return this;
    }
    
    @Override
    public int hashCode() {
       int hash = (this.playerId.charAt(playerId.length()-1)-'0') % 9 ;
        return hash;
    }

    @Override
    public boolean equals(Object obj) {
       if(obj instanceof Player)
           return this.playerId.equals(((Player) obj).playerId);
       
       return false;
    }
    
    public abstract String getPosition();
    
    public String toString(){
        return "Player ID: "+ this.playerId +"\n"+
                   "Player Name: "+ this.playerName +"\n"+
                   "Player's club id: "+ this.clubId+"\n"+
                   "Shirt Number: "+this.shirtNumber+"\n"+
                   "Position: "+this.getPosition();
    }

    public String toSaveString(){
         return playerId+", "+clubId+", "+playerName+", "+getPosition()+ ", "+shirtNumber;
    }
    
    public static Player getNewPlayer(PlayerType playerType){
        return playerType.createPlayer();
        //null = invalid position
    }
}
