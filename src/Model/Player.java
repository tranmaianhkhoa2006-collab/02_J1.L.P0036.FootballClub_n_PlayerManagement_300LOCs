package Model;

import CallBackInterface.ClubPlayerInterface;

/**
 *
 * @author admin
 */
public abstract class Player {
    private String playerId;
    private String playerName;
    private String clubId;
    private int shirtNumber;
    private ClubPlayerInterface apiClubManager;


    public Player(){
    }
    
    public String getPlayerId() {
        return playerId;
    }

    public String getPlayerName() {
        return playerName;
    }

    public Player setPlayerName(String playerName) {
        this.playerName = playerName;
         return this;
    }

    public String getClubId() {
        return clubId;
    }

    public Player setClubId(String clubId) {
        this.clubId = clubId;
         return this;
    }

    public int getShirtNumber() {
        return shirtNumber;
    }
   
    public Player setShirtNumber(int shirtNumber) {
        //Player need to link with Club Manager through interface
        boolean isClubApiLink = (apiClubManager !=null);
     
        
       if(isClubApiLink){
            if(this.shirtNumber == 0 && !this.clubId.isEmpty()){
                this.apiClubManager.addShirtNumber(clubId,playerId, shirtNumber);
                this.shirtNumber = shirtNumber;
                return this;
            }//set for first time case 

            //if club update success? set shirtNumber
            else{
                 if(apiClubManager.updateShirtNumber(this.clubId, this.shirtNumber, shirtNumber))
                     this.shirtNumber = shirtNumber;
             }
            //set for remaininng time
       }
        return this;
    }

    public ClubPlayerInterface getApiClubManager() {
        return apiClubManager;
    }
    
    public Player setApiClubManager(ClubPlayerInterface apiClubManager){
            this.apiClubManager = apiClubManager;
        return this;
    }
    
    public abstract String getPosition();
    public String toString(){
        return "Player ID: "+ this.playerId +"\n"+
                   "Player Name: "+ this.playerName +"\n"+
                   "Player's club id: "+ this.clubId+"\n"+
                   "Shirt Number: "+this.shirtNumber+"\n"+
                   "Position: "+this.getPosition();
    }

    public String toSaveFile(){
         return playerId+", "+playerName+", "+getPosition()+", "+ shirtNumber;
    }
    
    public static Player getNewPlayer(PlayerType playerType){
        return playerType.createPlayer();
        //null = invalid position
    }
}
