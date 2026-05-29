package Controller;

import Selection.ListType;
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
    private Manager playerManager = Manager.getNewManagerList(ListType.PLAYER_MANAGER);
    private Manager clubManager = Manager.getNewManagerList(ListType.CLUB_MANAGER);
    
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
            Inputter.inputString("Please enter to continue");
            ViewHandler.fakeClearScreen();
            
            if(isClientWantToLeave){
                ViewHandler.print("See you again!");
                 return; 
            }
            
        }
        
    }
    
    public void processOption(int option){
        OptionProcessor.get(option).processOption(playerManager, clubManager);
    } 
}
