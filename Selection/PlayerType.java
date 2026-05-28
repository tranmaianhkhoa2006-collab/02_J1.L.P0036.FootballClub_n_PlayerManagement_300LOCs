package Selection;

import Model.Defender;
import Model.Defender;
import Model.Forward;
import Model.Forward;
import Model.GoalKeeper;
import Model.GoalKeeper;
import Model.MidFielder;
import Model.MidFielder;
import Model.Player;
import Model.Player;
import Model.Winger;
import Model.Winger;

/**
 *
 * @author admin
 */
public enum PlayerType {
        WINGER("Winger") {
            @Override
            public Player createPlayer() {
               return new Winger();
            }
        },
        DEFENDER("Defender") {
            @Override
            public Player createPlayer() {
               return new Defender();
            }
        },
        GOALKEEPER("GoalKeeper") {
            @Override
            public Player createPlayer() {
                   return new GoalKeeper();
            }
          },
        MIDFIELDER("MidFielder") {
            @Override
            public Player createPlayer() {
              return new MidFielder();
            }
        },
        FORWARD("Forward") {
            @Override
            public Player createPlayer() {
             return new Forward();
            }
        };
       
        private String position;
        
        private PlayerType(String position){
            this.position = position;
        }
        
        public String getPosition(){
            return position;
        }
        public abstract Player createPlayer();
        
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
