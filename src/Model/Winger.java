package Model;

/**
 *
 * @author admin
 */
public class Winger extends Player{
     public Winger(String playerId,String playerName) {
        super(playerId, playerName);
    }
    @Override
    public String getPosition() {
        return "Winger";
    }
    
}
