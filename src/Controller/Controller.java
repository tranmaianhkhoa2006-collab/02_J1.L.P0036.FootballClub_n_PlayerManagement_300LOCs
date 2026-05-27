package Controller;

import Model.Club;
import Model.ListType;
import Model.MenuHeaderType;
import Utils.Acceptable;
import Utils.Inputter;
import static Utils.Inputter.inputChoice;
import Utils.MenuContainer;
import Utils.ViewHandler;

/**
 *
 * @author admin
 */
public class Controller {
    private Manager playerManager = Manager.getNewManagerList(ListType.PLAYER_MANAGER);
    private Manager clubManager = Manager.getNewManagerList(ListType.CLUB_MANAGER);
    
    public void startProgram(){
        while(true){
            ViewHandler.displayMenu(
                    MenuContainer.getInstance().createMainMenu().getMenu(), 
                    MenuContainer.getHeader(MenuHeaderType.MAIN_MENU_HEADER)
            );
            int option = Inputter.inputChoice("Input your option: ", 0, MenuContainer.getInstance().getNumberOfOptions()-1);
            switch(option){
                
            }
        }
    }
    
    public void saveBeforeExiting(){
        
    }
    
    public void listOfAllClub(){
        clubManager.show();
    }
    
    public String inputIdAndCheckDuplicate(){
         int count = 0;
         String clubId;
        do{
           boolean isThreeAttempt = ++count > 3; 
           
           if(isThreeAttempt){
                   switch(inputChoice("Do you want of continue?: ",0, 1)){
                       case 0:
                           count = 0;
                           break;
                       case 1:
                           return null;
                   }
           }
       
              clubId = Inputter.inputStringAndLoop("Input Club ID: ", Acceptable.CLUB_ID_VALID);
              if(clubId.isEmpty())
                    return null;

              if(clubManager.containId(clubId) )
                    ViewHandler.print("This club ID already exists!");
              else 
                     break;
            
       
        }
        while(true);
        return null;
    }
    public void addNewClub(){
        String clubId = inputIdAndCheckDuplicate();
         if(clubId.isEmpty())
            return;
         
        String clubName = Inputter.inputStringAndLoop("Input Club Name: ", Acceptable.CLUB_NAME_VALID);
        if(clubName.isEmpty())
            return;
        
        String sponsorBrand = Inputter.inputStringAndLoop("Input sponsor brand: ", Acceptable.SPONSOR_BRAND_VALID);
        if(sponsorBrand.isEmpty())
            return;
        
        double budget = Inputter.inputDouble("Input club budget: ", 0.0);
        
        boolean isAddSuccess = clubManager.add(clubId,
                                                                        Club.createNewClub().
                                                                                setClubId(clubId).
                                                                                setClubName(clubName).
                                                                                setSponsorBrand(sponsorBrand).
                                                                                setBudget(budget)
                                                                    );
        if(isAddSuccess){
            ViewHandler.print("Added successfully");
        }
        
    }
    
    
    public void searchClubByID(){
        String clubId =inputIdAndCheckDuplicate();
        if(clubId.isEmpty())
            return;
        
        Club foundClub = (Club) clubManager.search(clubId);
        
        if(foundClub != null){
            String clubInfo = clubId.toString();
            ViewHandler.print(
                    ViewHandler.lineBreak(clubInfo.length())+
                     clubInfo+
                    ViewHandler.lineBreak(clubInfo.length())         
            );
        }
        else{
           ViewHandler.print("This club does not exist!");
        }
        
    }
    
    public void updateClubByID(){
        String clubId = inputIdAndCheckDuplicate();
        Club foundClub = (Club) clubManager.search(clubId);
        if(foundClub == null)
            return;
        
        Club tempClub = Club.createNewClub().
                                            setClubId(clubId).
                                            setClubName(foundClub.getClubName()).
                                            setSponsorBrand(foundClub.getSponsorBrand()).
                                            setBudget(foundClub.getBudget());
        
        while(true){
            String clubInfo = tempClub.toString();
            ViewHandler.print(
                    ViewHandler.lineBreak(clubInfo.length())+
                            clubInfo+
                    ViewHandler.lineBreak(clubInfo.length())
            );
            
            ViewHandler.displayMenu(
                    MenuContainer.getInstance().createUpdateClubMenu().getMenu(), 
                    MenuContainer.getHeader(MenuHeaderType.UPDATE_CLUB_MENU_HEADER)
            );
            int choice = inputChoice("Input your option", 0, MenuContainer.getInstance().getNumberOfOptions()-1);
            switch(choice){
                case 0:
                    clubManager.update(tempClub.getClubId(), tempClub);
                    break;
                case 1:
                    break;
                case 2:
                    break;
                case 3:
                    break;
                case 4:
                    clubId = inputIdAndCheckDuplicate();
                    break;
                }
            }
        }
    
    
}
