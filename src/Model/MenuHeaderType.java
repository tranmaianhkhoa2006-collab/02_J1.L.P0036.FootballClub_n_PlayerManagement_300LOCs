package Model;

/**
 *
 * @author admin
 */
public enum MenuHeaderType {
     MAIN_MENU_HEADER("Main menu"),
     YES_NO_MENU_HEADER("Choose yes or no"),
     UPDATE_Player_MENU_HEADER("Update Player info menu"),
     UPDATE_CLUB_MENU_HEADER("Update club info menu");
     
     private String title;
     private MenuHeaderType(String title){
         this.title = title;
     }
     
     public String getTitle(){
         return this.title;
     }
}
