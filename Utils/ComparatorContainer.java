package Utils;

import Model.Club;
import Model.Player;
import java.util.Comparator;

/**
 *
 * @author admin
 */
public interface ComparatorContainer {
    public static Comparator<Player> clubNamesShirtNumberAscending = new Comparator<Player>() {
        @Override
        public int compare(Player thisPlayer, Player otherPlayer) {
            String thisPlayerClubName = thisPlayer.getApiClubManager().getClubName(thisPlayer.getClubId());
            String otherPlayerClubName = otherPlayer.getApiClubManager().getClubName(otherPlayer.getClubId());
            
            if(!thisPlayerClubName.equals(otherPlayerClubName))
                return thisPlayerClubName.compareTo(otherPlayerClubName);
            else
                return Integer.compare(thisPlayer.getShirtNumber(), otherPlayer.getShirtNumber());
        }
    };
    
    public static Comparator<Player> sortingForPlayerSaveList = new Comparator<Player>() {
        @Override
        public int compare(Player thisPlayer,Player otherPlayer) {
             return thisPlayer.getPlayerId().compareTo(otherPlayer.getPlayerId());
        }
    };
    
     public static Comparator<Club> sortingForClubSaveList = new Comparator<Club>() {
        @Override
        public int compare(Club thisClub,Club otherClub) {
             return thisClub.getClubId().compareTo(otherClub.getClubId());
        }
    };
}
