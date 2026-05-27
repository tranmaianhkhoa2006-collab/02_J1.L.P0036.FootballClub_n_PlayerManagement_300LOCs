package Model;

/**
 *
 * @author admin
 */
public class MidFielder extends Player{

      public MidFielder(String playerId,String playerName) {
        super(playerId, playerName);
    }
   
    @Override
    public String getPosition() {
       return "Mid Fielder";
    }
    
}
