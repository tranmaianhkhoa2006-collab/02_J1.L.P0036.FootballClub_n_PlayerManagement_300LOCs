package Model;

/**
 *
 * @author admin
 */
public class Defender extends Player{

    public Defender(String playerId,String playerName) {
        super(playerId, playerName);
    }

    @Override
    public String getPosition() {
       return "Defender";
    }
    
}
