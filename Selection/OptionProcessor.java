 package Selection;

import Controller.ClubManager;
import Controller.Manager;
import Controller.PlayerManager;
import Model.Club;
import Model.ClubPlayerInterface;
import Model.ManagerLimitMethodAccess;
import Model.Player;
import Utils.Acceptable;
import Utils.ComparatorContainer;
import Utils.Inputter;
import Utils.MenuContainer;
import Utils.ViewHandler;
import static Utils.Inputter.inputInteger;

/**
 *
 * @author admin
 */
public enum OptionProcessor {
    SAVE_BEFORE_EXITING_PROGRAM {
        @Override
        public void processOption(Manager<Player> playerManager, Manager<Club> clubManager) {
            throw new UnsupportedOperationException("Not supported yet."); // Generated from nbfs://nbhost/SystemFileSystem/Templates/Classes/Code/GeneratedMethodBody
        }
    },
    
    LIST_OF_ALL_CLUB {
        @Override
        public void processOption(Manager<Player> playerManager, Manager<Club> clubManager) {
           clubManager.show();
        }
      
    },
    
    ADD_CLUB {
        @Override
        public void processOption(Manager<Player> playerManager, Manager<Club> clubManager) {
            String clubId = inputIdAndDuplicateCheck(clubManager,Acceptable.CLUB_ID_VALID,
                    "Please enter a valid club id!\nFormat : CL-XXXX Where X is a number from 0 -> 9");
            
            if (clubId == null) {
                return;
            }

            String clubName = Inputter.inputStringAndLoop("Input Club Name: ",
                    "Please enter a valid club name!", Acceptable.CLUB_NAME_VALID);
            
            if (clubName == null) {
                return;
            }

            String sponsorBrand = Inputter.inputStringAndLoop("Input sponsor brand: ",
                    "Please enter a valid sponsor brand name!", Acceptable.SPONSOR_BRAND_VALID);
            
            if (sponsorBrand == null) {
                return;
            }

            double budget = Inputter.inputDouble("Input club budget: ", 0, 
                    "Please enter valid budget or press -1 to return to skip!");
            
            if(budget == -1)
                budget = 0;

            boolean isAddSuccess = clubManager.add(clubId,
                    Club.createNewClub().
                            setClubId(clubId).
                            setClubName(clubName).
                            setSponsorBrand(sponsorBrand).
                            setBudget(budget)
            );
            
            if (isAddSuccess) {
                ViewHandler.print("Added club successfully");
            }

        }
    },
    
    SEARCH_FOR_CLUB_ID {
        @Override
        public void processOption(Manager<Player> playerManager, Manager<Club> clubManager) {
            String clubId = Inputter.inputStringAndLoop("Input id to search: ",
                    "Please enter a valid club id!\nFormat : CL-XXXX Where X is a number from 0 -> 9", Acceptable.CLUB_ID_VALID);
            
            if (clubId == null) {
                return;
            }

            Club foundClub = (Club) clubManager.search(clubId);

            if (foundClub != null) {
                String clubInfo = clubId;
                ViewHandler.print(
                        ViewHandler.lineBreak(clubInfo.length())
                        + clubInfo
                        + ViewHandler.lineBreak(clubInfo.length())
                );
            } 
            else {
                ViewHandler.print("This club does not exist!");
            }
        }
    },
    
    UPDATE_CLUB_BY_ID {
        @Override
        public void processOption(Manager<Player> playerManager, Manager<Club> clubManager) {
        String clubId = Inputter.inputStringAndLoop("Input club id: ",
                "Please enter a valid club id!\nFormat : CL-XXXX Where X is a number from 0 -> 9", Acceptable.CLUB_ID_VALID);
        if(clubId == null)
            return;
        
        Club foundClub = (Club) clubManager.search(clubId);
        if(foundClub == null){
            ViewHandler.print("This club does not exist!");
            return;
        }
        
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
            int choice = inputInteger("Input your option: ","Invalid choice, please enter again!",
                                                      0, MenuContainer.getInstance().getNumberOfOptions()-1);
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
                    clubId = inputIdAndDuplicateCheck(clubManager,Acceptable.CLUB_ID_VALID,
                            "Please enter a valid club id!\nFormat : CL-XXXX Where X is a number from 0 -> 9");
                    break;
                }
                if(choice == 4 && clubId == null)
               Inputter.inputString("Press enter to continue!");
               ViewHandler.fakeClearScreen();
            }
        }

        public void updateClubName(Club club) {
            String newName = Inputter.inputStringAndLoopForUpdate("Input new club name: ",
            "Please enter a valid club id!\nFormat : CL-XXXX Where X is a number from 0 -> 9", Acceptable.CLUB_NAME_VALID);
            
            if (newName == null) {
                return;
            }

            club.setClubName(newName);

        }

        public void updateClubSponsorBrand(Club club) {
            String newSponsorBrand = Inputter.inputStringAndLoopForUpdate("Input new sponsor brand: ",
                    "Please enter a valid sponsor brand name", Acceptable.SPONSOR_BRAND_VALID);
            
            if (newSponsorBrand == null) {
                return;
            }

            club.setSponsorBrand(newSponsorBrand);

        }

        public void updatebudget(Club club) {
            ViewHandler.print("If you change your mind, please press -1 and press enter\n");
            double newBudget = Inputter.inputDouble("Input new budget: ",0,
                    "Please enter a valid budget!" );
            
            if (newBudget == -1) {
                return;
            }

            club.setBudget(newBudget);

        }
        
    },
    
    LIST_ALL_CLUB_BY_GAIN_ATMOST_BUDGET{
        @Override
        public void processOption(Manager<Player> playerManager, Manager<Club> clubManager) {
            ViewHandler.print("Press -1 to return!");
            double budget = Inputter.inputDouble("Input budget: ",0,
                    "Please enter a valid budget");
            if(budget == -1)
                return;
            
            ClubManager tmpCastingClubManager = (ClubManager) clubManager; 
            
           tmpCastingClubManager.show(tmpCastingClubManager.filterByBudgetValue(budget));
        }
    },
   
    LIST_PLAYERS_BY_CLUB_NAME_SHIRT_NUMBER {
        @Override
        public void processOption(Manager<Player> playerManager, Manager<Club> clubManager) {
            
            PlayerManager tempCastingManager = (PlayerManager) playerManager;
            tempCastingManager.show(
                    tempCastingManager.sortByComparator(ComparatorContainer.clubNamesShirtNumberAscending)
            );
        }
    },
    
    SEARCH_PLAYERS_BY_PLAYER_NAME {
        @Override
        public void processOption(Manager<Player> playerManager, Manager<Club> clubManager) {
           
                PlayerManager tempCastingManager = (PlayerManager) playerManager;
                
                String searchName = Inputter.inputStringAndLoop("Input name (partial or full) to search: ",
                        "Please enter a valid name or partial name (Atleast 2 characters)", Acceptable.PLAYER_NAME_VALID);
                
                if(searchName == null)
                    return;

                tempCastingManager.show(tempCastingManager.searchByName(searchName));
        }
    },
    
    ADD_PLAYER {
        @Override
        public void processOption(Manager<Player> playerManager, Manager<Club> clubManager) {
            String id = inputIdAndDuplicateCheck(playerManager,Acceptable.PLAYER_ID_VALID,
                    "Please enter a valid player id!");
            if(id == null)
                return;
            
            String name = Inputter.inputStringAndLoop("Input player name: ",
                    "Please enter correct name!\nName can not contain numbers and special characters", Acceptable.PLAYER_NAME_VALID);
            if(name == null)
                return;
            
            String clubId = inputPlayerClubID(clubManager);
            if(clubId == null)
                return;
           
            int shirtNumber= inputShirtNumberSupportter((ClubManager) clubManager, clubId);
            if(shirtNumber == 0)
                return;
            
            PlayerType playerType = Inputter.inputPlayerType("Player type: Defender, Winger, Forward, Goalkeeper, Midfielder\n"
                    + "Input player type: ");
            if (playerType == null)
                return;
            
            Player player = Player.getNewPlayer(playerType).setApiClubManager((ClubManager) clubManager)
                                                                                       .setPlayerId(id)
                                                                                       .setPlayerName(name)
                                                                                       .setClubId(clubId)
                                                                                       .setShirtNumber(shirtNumber);
            
            PlayerManager tempCasting = (PlayerManager)playerManager;
            
            tempCasting.add(id, player);
            
            ViewHandler.print("Add player successfully!");
            
        }
        
        public int inputShirtNumberSupportter(ClubPlayerInterface clubManagerMethodInterface,String clubId){
            int shirtNumber;
            ViewHandler.print("Input 0 to return main menu\n");
            int count=0; 
             while(true){
                   count++;
               
            
               if(count>3){
                   ViewHandler.displayMenu(
                           MenuContainer.getInstance().createYesNoMenu().getMenu(),
                           MenuContainer.getHeader(MenuHeaderType.YES_NO_MENU_HEADER)
                   );
                   
                   int choice = inputInteger("Do you want of continue?: ","Invalid choice, please enter again!",0, 1);
                  
                   if(choice == 1)
                       return 0;
                   else
                       count = 0;
               }
               
                  shirtNumber = Inputter.inputInteger("Input shirt number: ","Please input a valid shirt number (1->99)", 1);
                  
                  
                  boolean thisNumberHasBeenTaken = clubManagerMethodInterface.isContainShirtNumber(clubId, shirtNumber);
                  if(thisNumberHasBeenTaken)
                      ViewHandler.print("This shirt number already exists in this club!”");
                  else 
                      break;
                 
             }
             
             return shirtNumber;
        }
        
        public String inputPlayerClubID(ManagerLimitMethodAccess duplicateChecker) {
        int attemptOfInput = 0;
        String id;
        do {
            boolean isThreeAttempt = ++attemptOfInput > 3;

            if (isThreeAttempt) {
                switch (inputInteger("Do you want of continue?: ","Invalid choice, please enter again!", 0, 1)) {
                    case 0:
                        attemptOfInput = 0;
                        break;
                    case 1:
                        return null;
                }
            }
            id = Inputter.inputStringAndLoop("Input player club id: ","Please enter a valid club id!\n(Format : CL-XXXX | X is a number from 0 -> 9)", Acceptable.CLUB_ID_VALID);
            if (id == null) {
                return null;
            }

            if (!duplicateChecker.containId(id)) {
                ViewHandler.print("This club id does not exist\n");
            }
            else
                break;
        } 
        while (true);
        return id;
        
    }
        
    },
    
    REMOVE_PLAYER_BY_ID {
        @Override
        public void processOption(Manager<Player> playerManager, Manager<Club> clubManager) {
            String id = Inputter.inputStringAndLoop("Input id to delete", "Please input a valid id!", Acceptable.PLAYER_ID_VALID);
            if(id == null)
                return;
            
            Player player = playerManager.search(id);
            if(player == null){
                ViewHandler.print("This player does not exist!");
                return;
            }
            
            String playerInfo = player.toString();
            ViewHandler.print(
                    ViewHandler.lineBreak(playerInfo.length())+
                     playerInfo +
                    ViewHandler.lineBreak(playerInfo.length())
            );
            
            ViewHandler.displayMenu(
                    MenuContainer.getInstance().createYesNoMenu().getMenu(),
                    MenuContainer.getHeader(MenuHeaderType.YES_NO_MENU_HEADER)
            );
            
            int choice = Inputter.inputInteger("Are you sure to delete this player? ","Invalid choice, please enter again!", 0, 1);
            switch(choice){
                case 0:
                    deletePlayer(playerManager, player);
                    break;
                case 1:
                    ViewHandler.print("You denied to delete this player!");
                    return;
            }
            
        }
        
        public void deletePlayer(Manager<Player> playerManager,Player player){
             boolean clubHasDeletedThisPLayer = player.getApiClubManager().deleteShirtNumber(player.getClubId(),player.getShirtNumber());
             
             if(clubHasDeletedThisPLayer){
                 playerManager.remove(player.getPlayerId());
                 ViewHandler.print("Deleted player successfully!\n");
             }
             else{
                 ViewHandler.printError("Can not delete shirt number in player club!\nPlease contact developer for supporting!");
             }
        }
        
        
    },
    
    UPDATE_PLAYER_BY_ID {
        @Override
        public void processOption(Manager<Player> playerManager, Manager<Club> clubManager) {
            throw new UnsupportedOperationException("Not supported yet."); // Generated from nbfs://nbhost/SystemFileSystem/Templates/Classes/Code/GeneratedMethodBody
        }
    },
    
    UPDATE_PLAYER_NAME {
        @Override
        public void processOption(Manager<Player> playerManager, Manager<Club> clubManager) {
            throw new UnsupportedOperationException("Not supported yet."); // Generated from nbfs://nbhost/SystemFileSystem/Templates/Classes/Code/GeneratedMethodBody
        }
    },
    
    UPDATE_PLAYER_SHIRT_NUMBER {
        @Override
        public void processOption(Manager<Player> playerManager, Manager<Club> clubManager) {
            throw new UnsupportedOperationException("Not supported yet."); // Generated from nbfs://nbhost/SystemFileSystem/Templates/Classes/Code/GeneratedMethodBody
        }
    },
    
    LIST_ALL_PLAYERS_BY_POSITION {
        @Override
        public void processOption(Manager<Player> playerManager, Manager<Club> clubManager) {
            throw new UnsupportedOperationException("Not supported yet."); // Generated from nbfs://nbhost/SystemFileSystem/Templates/Classes/Code/GeneratedMethodBody
        }
    },
    
    LOAD_DATA {
        @Override
        public void processOption(Manager<Player> playerManager, Manager<Club> clubManager) {
            throw new UnsupportedOperationException("Not supported yet."); // Generated from nbfs://nbhost/SystemFileSystem/Templates/Classes/Code/GeneratedMethodBody
        }
    },
    
    SAVE_DATA {
        @Override
        public void processOption(Manager<Player> playerManager, Manager<Club> clubManager) {
            throw new UnsupportedOperationException("Not supported yet."); // Generated from nbfs://nbhost/SystemFileSystem/Templates/Classes/Code/GeneratedMethodBody
        }
    };
    
    
    public abstract void processOption(Manager<Player> playerManager, Manager<Club> clubManager);

    public static OptionProcessor get(int option){
        return OptionProcessor.values()[option];
    }
    
    public static String inputIdAndDuplicateCheck(ManagerLimitMethodAccess duplicateChecker, String idPattern,String errorMess) {
                int attemptOfInput = 0;
                String id;
                boolean isDuplicate;
                do {
                    boolean isThreeAttempt = ++attemptOfInput > 3;

                    if (isThreeAttempt) {
                        switch (inputInteger("Do you want of continue?: ","Invalid choice, please enter again!", 0, 1)) {
                            case 0:
                                attemptOfInput = 0;
                                break;
                            case 1:
                                return null;
                        }
                    }
                    
                    String mess ;
                     if(idPattern.equals(Acceptable.CLUB_ID_VALID))
                            mess = "Input Club ID: ";
                        else
                            mess = "Input player ID: ";
                     
                    id = Inputter.inputStringAndLoop(mess,"Please enter a valid ID formaat\n(Club : CL-XXXX | Player : P - XXXX)\nWhere X is number from 0 -> 9", idPattern);
                    if (id == null) {
                        return null;
                    }
                    
                   isDuplicate= duplicateChecker.containId(id);
                    if(isDuplicate){
                        ViewHandler.printError(errorMess);
                    }
                    else
                        break;

               } while (true);
             
              return id;  
       }       
}
