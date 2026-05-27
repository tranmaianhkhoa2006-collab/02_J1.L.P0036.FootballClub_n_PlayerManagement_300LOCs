package Model;

/**
 *
 * @author admin
 */
public class Forward extends Player{

     public Forward(String playerId,String playerName) {
        super(playerId, playerName);
    }
      
    @Override
    public String getPosition() {
        return "Forward";
    }
    
}
