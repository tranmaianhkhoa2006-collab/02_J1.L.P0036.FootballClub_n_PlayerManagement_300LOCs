package Utils;

import Selection.MenuHeaderType;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

/**
 *
 * @author admin
 */
public class MenuContainer {
     private static MenuContainer menuContainerInstance = new MenuContainer();
    
     private ArrayList<String> menuOption = new ArrayList<>();
     
     //menu width
     public final static int HEADER_WIDTH = 100;
     
     //menu pattern
     public final static String PATTERN_OF_MENU = "| %-"+(MenuContainer.HEADER_WIDTH-4)+"s |";
     
     
     private MenuContainer(){
     }
     
     //using singleton for avoiding trash menu in the program , menu do not need to
     //create multiple object
     public static MenuContainer getInstance(){
         return menuContainerInstance;
     }
     
     public static String getHeader(MenuHeaderType headerType){
         String header = 
             ViewHandler.lineBreak(MenuContainer.HEADER_WIDTH) +
             String.format(MenuContainer.PATTERN_OF_MENU,headerType.getTitle())+
             ViewHandler.lineBreak(MenuContainer.HEADER_WIDTH);
         return header;
     }
     
     //return instance - idea based on builder pattern
    public MenuContainer createMainMenu() {
        menuOption.clear();
        menuOption.add("Exit the Program");
        menuOption.add("List of all clubs");
        menuOption.add("Add a new club");
        menuOption.add("Search for a club by ID");
        menuOption.add("Update a club by ID");
        menuOption.add("List of all clubs with budget ≤ input value");
        menuOption.add("List all players in ascending order of club names.if same,sort by shirt number ascending.");
        menuOption.add("Search players by partial player name");
        menuOption.add("Add a new player");
        menuOption.add("Remove a player with ID");
        menuOption.add("Update a player with an ID");
        menuOption.add("List all players by a specific position ");
        menuOption.add("Save data to files");
        menuOption.add("Load data from files ");
        return menuContainerInstance;
    }
     
     public MenuContainer createUpdatePlayerMenu(){
         menuOption.clear();
         menuOption.add("Return to menu");
         menuOption.add("Update player name");
         menuOption.add("Update position");
         menuOption.add("Update shirt number");
         menuOption.add("Change player id to update");
         return menuContainerInstance;
     }
     
      public MenuContainer createUpdateClubMenu(){
         menuOption.clear();
         menuOption.add("Confirm information and return to menu");
         menuOption.add("Update club name");
         menuOption.add("Update sponsor brand");
         menuOption.add("Update budget");
         menuOption.add("Refuse changing information and exit");
         menuOption.add("Confirm information and change club id to update");
         return menuContainerInstance;
     }
     
     public MenuContainer createYesNoMenu(){
          menuOption.clear();
          menuOption.add("Yes");
          menuOption.add("No");
          return menuContainerInstance;
     }
     
     public int getNumberOfOptions(){
         return menuOption.size();
     }
     
     public List<String> getMenu(){
         return Collections.unmodifiableList(menuOption);
     }
     
     
}
