package Model;

/**
 *
 * @author admin
 */
public class GoalKeeper extends Player{
      public GoalKeeper(String playerId,String playerName) {
        super(playerId, playerName);
    }
      
    @Override
    public String getPosition() {
        return "Goal Keeper";
    }
    
}
