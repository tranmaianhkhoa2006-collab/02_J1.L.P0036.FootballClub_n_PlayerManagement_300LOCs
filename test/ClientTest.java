package Run;

import Controller.ClubManager;
import Controller.Manager;
import Model.Club;
import CallBackInterface.ClubPlayerInterface;
import Model.Player;
import Model.ListType;
import Model.MenuHeaderType;
import Model.PlayerType;
import Utils.MenuContainer;
import Utils.ViewHandler;

/**
 *
 * @author admin
 */
public class ClientTest {
    public static void main(String[] args) {
        ViewHandler.displayMenu(
                MenuContainer.getInstance().createMainMenu().getMenu(),
                MenuContainer.getHeader(MenuHeaderType.MAIN_MENU_HEADER)
        );
        
        ViewHandler.displayMenu(
                MenuContainer.getInstance().createUpdateClubMenu().getMenu(),
                MenuContainer.getHeader(MenuHeaderType.UPDATE_CLUB_MENU_HEADER)
        );
        
        ViewHandler.displayMenu(
                MenuContainer.getInstance().createUpdatePlayerMenu().getMenu(),
                MenuContainer.getHeader(MenuHeaderType.UPDATE_Player_MENU_HEADER)
        );
        
        Manager dataOfPlayers = Manager.getNewManagerList(ListType.PLAYER_MANAGER);
        Manager dataOfClubs = Manager.getNewManagerList(ListType.CLUB_MANAGER);
        dataOfClubs.add("CL-0001", Club.createNewClub("CL-0001","Man dan", "Nike"));
        
        Player player1 = Player.getNewPlayer(PlayerType.WINGER, "P-0001" ,"Khoa").
                setApiClubManager((ClubManager) dataOfClubs).setClubId("CL-0001").setShirtNumber(13);
       
         Player player2 = Player.getNewPlayer(PlayerType.WINGER, "P-0002" ,"Kiet").
                setApiClubManager((ClubManager) dataOfClubs).setClubId("CL-0001").setShirtNumber(15);
         
         
        dataOfPlayers.add(player1.getPlayerId(), player1);
        dataOfPlayers.add(player2.getPlayerId(), player2);
        
        
        dataOfPlayers.show();
        dataOfClubs.show();
       
        player1.setShirtNumber(19);
       
        dataOfPlayers.show();
        dataOfClubs.show();
        
    }
}
