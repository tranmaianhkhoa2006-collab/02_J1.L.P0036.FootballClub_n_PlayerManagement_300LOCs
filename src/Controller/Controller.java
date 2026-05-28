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

// pathFile for local git: d:\\javaBaiTap\\LabTwo_FootballClub\\src

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
                case 0:
                    saveBeforeExiting();
                   return;
                case 1:
                    this.listOfAllClub();
                    break;
                case 2:
                    this.addNewClub();
                    break;
                case 3:
                    this.searchClubByID();
                    break;
                case 4:
                    this.updateClubByID();
                    break;
                case 5:
                    
                    break;
                case 6:
                    
                    break;
                case 7:
                    
                    break;
                case 8:
                    
                    break;
                case 9:
                    
                    break;
                case 10:
                    
                    break;
                case 11:
                    
                    break;
                case 12:
                    
                    break;
                case 13:
                    
                    break;
                case 14:
                    
                    break;
                    
            }
        }
    }
    
    public void saveBeforeExiting(){
        
    }
    
    public void listOfAllClub(){
        clubManager.show();
    }
    
    private String inputIdAndCheckDuplicate(){
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
            int choice = inputChoice("Input your option: ", 0, MenuContainer.getInstance().getNumberOfOptions()-1);
            switch(choice){
                case 0:
                    clubManager.update(tempClub.getClubId(), tempClub);
                    return;
                case 1:
                    updateClubName(tempClub);
                    break;
                case 2:
                    updateClubSponsorBrand(tempClub);
                    break;
                case 3:
                    updatebudget(tempClub);
                    break;
                case 4:
                    clubId = inputIdAndCheckDuplicate();
                    break;
                }
               Inputter.inputString("Press enter to continue!");
               ViewHandler.fakeClearScreen();
            }
        }
    
    public void updateClubName(Club club){
         String newName = Inputter.inputStringAndLoopForUpdate("Input new club name: ", Acceptable.CLUB_NAME_VALID);
         if(newName.isEmpty())
             return;
         
         club.setClubName(newName);
         
    }
    
    public void updateClubSponsorBrand(Club club){
        String newSponsorBrand = Inputter.inputStringAndLoopForUpdate("Input new sponsor brand: ", Acceptable.SPONSOR_BRAND_VALID);
        if(newSponsorBrand.isEmpty())
            return;
        
        club.setSponsorBrand(newSponsorBrand);
        
    }
    
    public void updatebudget(Club club){
        ViewHandler.print("If you change your mind, please don't input anything and press enter\n");
        double newBudget = Inputter.inputDoubleForUpdate("Input new budget: ");
        if(newBudget == -1)
            return;
        
        club.setBudget(newBudget);
        
    }  
    
}
