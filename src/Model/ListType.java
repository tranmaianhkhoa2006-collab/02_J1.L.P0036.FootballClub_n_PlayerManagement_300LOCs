package Model;

import Controller.ClubManager;
import Controller.Manager;
import Controller.PlayerManager;

/**
 *
 * @author admin
 */
public enum ListType {
    PLAYER_MANAGER{
        @Override
        public Manager createNewManager() {
            return new PlayerManager();
        }
    },
    CLUB_MANAGER{
        @Override
        public Manager createNewManager() {
           return new ClubManager();
        }
        
    };
    
    public abstract Manager createNewManager();
}
