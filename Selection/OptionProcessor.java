 package Selection;

import Controller.ClubManager;
import Controller.Manager;
import Controller.PlayerManager;
import Model.Club;
import Model.ManagerLimitMethodAccess;
import Model.Player;
import Utils.Acceptable;
import Utils.ComparatorContainer;
import Utils.Inputter;
import static Utils.Inputter.inputChoice;
import Utils.MenuContainer;
import Utils.ViewHandler;

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
            String clubId = inputIdAndDuplicateCheck(clubManager,Acceptable.CLUB_ID_VALID);
            if (clubId == null) {
                return;
            }

            String clubName = Inputter.inputStringAndLoop("Input Club Name: ", Acceptable.CLUB_NAME_VALID);
            if (clubName == null) {
                return;
            }

            String sponsorBrand = Inputter.inputStringAndLoop("Input sponsor brand: ", Acceptable.SPONSOR_BRAND_VALID);
            if (sponsorBrand == null) {
                return;
            }

            double budget = Inputter.inputDouble("Input club budget: ", 0.0);

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
            String clubId = Inputter.inputStringAndLoop("Input id to search: ", Acceptable.CLUB_ID_VALID);
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
        String clubId = Inputter.inputStringAndLoop("Input club id: ", Acceptable.CLUB_ID_VALID);
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
                    clubId = inputIdAndDuplicateCheck(clubManager,Acceptable.CLUB_ID_VALID);
                    break;
                }
                if(choice == 4 && clubId == null)
               Inputter.inputString("Press enter to continue!");
               ViewHandler.fakeClearScreen();
            }
        }

        public void updateClubName(Club club) {
            String newName = Inputter.inputStringAndLoopForUpdate("Input new club name: ", Acceptable.CLUB_NAME_VALID);
            if (newName == null) {
                return;
            }

            club.setClubName(newName);

        }

        public void updateClubSponsorBrand(Club club) {
            String newSponsorBrand = Inputter.inputStringAndLoopForUpdate("Input new sponsor brand: ", Acceptable.SPONSOR_BRAND_VALID);
            if (newSponsorBrand == null) {
                return;
            }

            club.setSponsorBrand(newSponsorBrand);

        }

        public void updatebudget(Club club) {
            ViewHandler.print("If you change your mind, please don't input anything and press enter\n");
            double newBudget = Inputter.inputDouble("Input new budget: ");
            if (newBudget == -1) {
                return;
            }

            club.setBudget(newBudget);

        }
        
    },
    
    LIST_ALL_CLUB_BY_GAIN_ATMOST_BUDGET{
        @Override
        public void processOption(Manager<Player> playerManager, Manager<Club> clubManager) {
            double budget = Inputter.inputDouble("Input budget: ");
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
                
                String searchName = Inputter.inputStringAndLoop("Input name (partial or full) to search: ", Acceptable.PLAYER_NAME_VALID);
                if(searchName == null)
                    return;


                tempCastingManager.show(tempCastingManager.searchByName(searchName));
        }
    },
    
    ADD_PLAYER {
        @Override
        public void processOption(Manager<Player> playerManager, Manager<Club> clubManager) {
            String id = inputIdAndDuplicateCheck(playerManager,Acceptable.PLAYER_ID_VALID);
            if(id == null)
                return;
            
            String name = Inputter.inputStringAndLoop("Input player name: ",Acceptable.PLAYER_NAME_VALID);
            if(name == null)
                return;
            
            String clubId = inputPlayerClubID(clubManager);
            if(clubId == null)
                return;
           
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
                   
                   int choice = inputChoice("Do you want of continue?: ",0, 1);
                  
                   if(choice == 1)
                       return ;
                   else
                       count = 0;
               }
               
                  shirtNumber = Inputter.inputInteger("Input shirt number: ",1);
                  if(shirtNumber == 0)
                      return;
                  
                  if(clubManager.search(clubId).isContainShirtNumber(shirtNumber))
                      ViewHandler.print("This shirt number already exists in this club!”");
                  else 
                      break;
                 
             }
            
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
    },
    
    REMOVE_PLAYER_BY_ID {
        @Override
        public void processOption(Manager<Player> playerManager, Manager<Club> clubManager) {
            throw new UnsupportedOperationException("Not supported yet."); // Generated from nbfs://nbhost/SystemFileSystem/Templates/Classes/Code/GeneratedMethodBody
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
    
    public static String inputIdAndDuplicateCheck(ManagerLimitMethodAccess duplicateChecker, String idPattern) {
                int attemptOfInput = 0;
                String id;
                do {
                    boolean isThreeAttempt = ++attemptOfInput > 3;

                    if (isThreeAttempt) {
                        switch (inputChoice("Do you want of continue?: ", 0, 1)) {
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
                     
                    id = Inputter.inputStringAndLoop(mess, idPattern);
                    if (id == null) {
                        return null;
                    }
                    
                    if(duplicateChecker.containId(id)){
                        if(idPattern.equals(Acceptable.CLUB_ID_VALID))
                            ViewHandler.print("This club ID already exists!\n");
                        else
                            ViewHandler.print("This player ID already exists!\n");
                    }
                    else
                        break;

               } while (true);
             
              return id;  
       }
       
    public static String inputPlayerClubID(ManagerLimitMethodAccess duplicateChecker) {
        int attemptOfInput = 0;
        String id;
        do {
            boolean isThreeAttempt = ++attemptOfInput > 3;

            if (isThreeAttempt) {
                switch (inputChoice("Do you want of continue?: ", 0, 1)) {
                    case 0:
                        attemptOfInput = 0;
                        break;
                    case 1:
                        return null;
                }
            }
            id = Inputter.inputStringAndLoop("Input player club id: ", Acceptable.CLUB_ID_VALID);
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
       
}
