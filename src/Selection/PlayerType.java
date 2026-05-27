package Selection;

import Model.Defender;
import Model.Forward;
import Model.GoalKeeper;
import Model.MidFielder;
import Model.Player;
import Model.Winger;

/**
 *
 * @author admin
 */
public enum PlayerType {
        WINGER("Winger") {
            @Override
            public Player createPlayer(String playerId, String playerName) {
               return new Winger(playerId, playerName);
            }
        },
        DEFENDER("Defender") {
            @Override
            public Player createPlayer(String playerId, String playerName) {
               return new Defender(playerId, playerName);
            }
        },
        GOALKEEPER("GoalKeeper") {
            @Override
            public Player createPlayer(String playerId, String playerName) {
                   return new GoalKeeper(playerId, playerName);
            }
          },
        MIDFIELDER("MidFielder") {
            @Override
            public Player createPlayer(String playerId, String playerName) {
              return new MidFielder(playerId, playerName);
            }
        },
        FORWARD("Forward") {
            @Override
            public Player createPlayer(String playerId, String playerName) {
             return new Forward(playerId, playerName);
            }
        };
       
        private String position;
        
        private PlayerType(String position){
            this.position = position;
        }
        
        public String getPosition(){
            return position;
        }
        public abstract Player createPlayer(String playerId, String playerName);
        
        public static PlayerType searchPlayerType(String playerType){
            if(playerType.isEmpty())
                    return null;
            
            for(PlayerType player : PlayerType.values()){
                if(player.getPosition().equalsIgnoreCase(playerType))
                    return player;
            }
             
            return null;
        }
}
