package Controller;

import Business.ClubManager;
import Business.Manager;
import Business.PlayerManager;
import Model.Club;
import Model.Player;
import Selection.ManagerType;
import Selection.MenuHeaderType;
import Selection.OptionProcessor;
import Utils.Inputter;
import Utils.MenuContainer;
import Utils.ViewHandler;

/**
 *
 * @author admin
 */

// pathFile for local git: d:\\javaBaiTap\\Lab211_Lab2\\src

public class Controller {

    private Manager<Club> clubManager = Manager.getNewManagerList(ManagerType.CLUB_MANAGER);
    private Manager<Player> playerManager = Manager.getNewManagerList(ManagerType.PLAYER_MANAGER);
    
    public Controller(){
        ((PlayerManager)playerManager).setApiClubManager((ClubManager) clubManager);
    }
    
    public void startProgram(){
        while(true){
            ViewHandler.fakeClearScreen();
            ViewHandler.displayMenu(
                    MenuContainer.getInstance().createMainMenu().getMenu(), 
                    MenuContainer.getHeader(MenuHeaderType.MAIN_MENU_HEADER)
            );
            int option = Inputter.inputInteger("Input your option: ","Invalid choice, please enter again!" ,0, MenuContainer.getInstance().getNumberOfOptions()-1);
            processOption(option);
            boolean isClientWantToLeave = option == 0;
            Inputter.inputString("\nPlease enter to continue\n");
            ViewHandler.fakeClearScreen();
            
            if(isClientWantToLeave){
                ViewHandler.print("See you again!");
                 return; 
            }
            
        }
        
    }
    
    public void processOption(int option){
        int optionOfLoadData = MenuContainer.getInstance().getNumberOfOptions()-1;
        
        //InputInteger(min->max) can return min - 1 
        if(option==-1){
            ViewHandler.printError("Invalid choice!\n");
            return;
        }
        else if(option == optionOfLoadData){
                clubManager = Manager.getNewManagerList(ManagerType.CLUB_MANAGER);
                playerManager = Manager.getNewManagerList(ManagerType.PLAYER_MANAGER);
                ((PlayerManager)playerManager).setApiClubManager((ClubManager) clubManager);
        }
        OptionProcessor.get(option).processOption(playerManager, clubManager);
    } 
}
