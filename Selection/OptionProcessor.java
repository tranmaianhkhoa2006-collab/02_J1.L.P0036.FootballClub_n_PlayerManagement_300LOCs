 package Selection;

import Business.ClubManager;
import Business.Manager;
import Business.PlayerManager;
import Model.Club;
import Model.Player;
import Utils.Acceptable;
import Utils.ComparatorContainer;
import Utils.Inputter;
import Utils.MenuContainer;
import Utils.ViewHandler;
import static Utils.Inputter.inputInteger;
import Business.ClubAndPlayerConnection;

/**
 *
 * @author admin
 */
public enum OptionProcessor {
    SAVE_BEFORE_EXITING_PROGRAM {
        @Override
        public void processOption(Manager<Player> playerManager, Manager<Club> clubManager) {
            if(playerManager.getSaveStatus()|| clubManager.getSaveStatus()){
                ViewHandler.displayMenu(
                        MenuContainer.getInstance().createYesNoMenu().getMenu(), 
                        MenuContainer.getHeader(MenuHeaderType.YES_NO_MENU_HEADER));
                
              switch(Inputter.inputInteger("Input your choice: ", "Invalid choice!",0, 1)){
                    case -1:                        
                    case 1:
                        return;
                }
                
                playerManager.saveData();
                clubManager.saveData();
            }
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
            String clubId = Inputter.inputStringAndLoop("Input Club ID(Format: CL-XXXX | XXXX is from 0001-9999): ","Invalid format!", Acceptable.CLUB_ID_VALID);
            
            if (clubId == null) {
                return;
            }
             else if(Acceptable.checkExistID(clubId, clubManager)){
                ViewHandler.printError("This id already exists!\n");
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
                ViewHandler.print("Added club successfully\n");
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
                String clubInfo = foundClub.toString();
                ViewHandler.print(
                        ViewHandler.lineBreak(clubInfo.length())
                        + clubInfo
                        + ViewHandler.lineBreak(clubInfo.length())
                );
            } 
            else {
                ViewHandler.print("This club does not exist!\n");
            }
        }
    },
    
    UPDATE_CLUB_BY_ID {
        @Override
        public void processOption(Manager<Player> playerManager, Manager<Club> clubManager) {
            while (true) {
                String clubId = Inputter.inputStringAndLoop("Input club id: ",
                        "Please enter a valid club id!\nFormat : CL-XXXX Where X is a number from 0 -> 9", Acceptable.CLUB_ID_VALID);
                if (clubId == null) {
                    return;
                }

                Club foundClub = (Club) clubManager.search(clubId);
                if (foundClub == null) {
                    ViewHandler.print("This club does not exist!\n");
                    return;
                }

                Club tempClub = Club.createNewClub().
                        setClubId(clubId).
                        setClubName(foundClub.getClubName()).
                        setSponsorBrand(foundClub.getSponsorBrand()).
                        setBudget(foundClub.getBudget());

                while (true) {
                    String clubInfo = tempClub.toString();
                    ViewHandler.print(
                            ViewHandler.lineBreak(clubInfo.length())
                            + clubInfo
                            + ViewHandler.lineBreak(clubInfo.length())
                    );

                    ViewHandler.displayMenu(
                            MenuContainer.getInstance().createUpdateClubMenu().getMenu(),
                            MenuContainer.getHeader(MenuHeaderType.UPDATE_CLUB_MENU_HEADER)
                    );
                    int choice = inputInteger("Input your option: ", "Invalid choice, please enter again!",
                            0, MenuContainer.getInstance().getNumberOfOptions() - 1);
                    switch (choice) {
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
                            return;
                        case 5:
                            break;
                        default:
                            ViewHandler.printError("Invalid choice!\n");
                    }
                    
                    Inputter.inputString("Press enter to continue!\n");
                    ViewHandler.fakeClearScreen();
                    
                    if(choice == MenuContainer.getInstance().getNumberOfOptions()-1)
                        break;
                        
                 
                }
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
            ViewHandler.print("Press -1 to return!\n");
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
            String id = Inputter.inputStringAndLoop("Input ID(Format: PXXXX (XXXX is from 0001-9999)): ","Invalid format!", Acceptable.PLAYER_ID_VALID);
            if(id == null)
                return;
            else if(Acceptable.checkExistID(id, playerManager)){
                ViewHandler.printError("This id already exists!\n");
                return;
            }
            
            String name = Inputter.inputStringAndLoop("Input player name: ",
                    "Please enter correct name!\nName can not contain numbers and special characters", Acceptable.PLAYER_NAME_VALID);
            if(name == null)
                return;
            
            String clubId = Inputter.inputStringAndLoop("Input club id(Format: CL-XXXX | XXXX is from 0001-9999): ", "Invalid club id format", Acceptable.CLUB_ID_VALID);
            if(clubId == null)
                return;
            else if(!Acceptable.checkExistID(clubId, clubManager)){
                ViewHandler.printError("This club is not exist!");
                return;
            }
            
            int shirtNumber=  Inputter.inputInteger("Input shirt number(1->99) or press 0 to return: ","Invalid shirt number!", 1, 99);
            if(shirtNumber == 0)
                return;
            else if(Acceptable.checkExistShirtNumber(clubId, shirtNumber, (ClubManager)clubManager)){
               ViewHandler.printError("This number already exist in this club");
               return;
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
            
            
            playerManager.add(id, player);
            
            ViewHandler.print("Add player successfully!\n");
            
        }
         
    },
    
    REMOVE_PLAYER_BY_ID {
        @Override
        public void processOption(Manager<Player> playerManager, Manager<Club> clubManager) {
            String id = Inputter.inputStringAndLoop("Input id to delete: ", "Please input a valid id!", Acceptable.PLAYER_ID_VALID);
            if(id == null)
                return;
            
            Player player = playerManager.search(id);
            if(player == null){
                ViewHandler.print("This player does not exist!\n");
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
            int choice = -1;
            while(choice !=0 && choice !=1){
                choice = Inputter.inputInteger("Are you sure to delete this player? ","Invalid choice, please enter again!", 0, 1);
                switch(choice){
                    case 0:
                        deletePlayer(playerManager, player);
                        break;
                    case 1:
                        ViewHandler.print("You denied to delete this player!\n");
                        return;
                    default:
                        ViewHandler.printError("Invalid choice!\n");
                        return;
                }
            }
        }
        
        public void deletePlayer(Manager<Player> playerManager,Player player){
             boolean clubHasDeletedThisPLayer = player.getApiClubManager().deleteShirtNumber(player.getClubId(),player.getShirtNumber());
             
             if(clubHasDeletedThisPLayer){
                 playerManager.remove(player.getPlayerId());
                 ViewHandler.print("Deleted player successfully!\n");
             }
             else{
                 ViewHandler.printError("Can not delete shirt number in player club!\nPlease contact developer for supporting!\n");
             }
        }
        
        
    },
    
    UPDATE_PLAYER_BY_ID {
        @Override
        public void processOption(Manager<Player> playerManager, Manager<Club> clubManager) {
            while(true){
              String id = Inputter.inputStringAndLoop("Input player id to search: ","Invalid player ID",Acceptable.PLAYER_ID_VALID);
              if(id==null)
                 return;
            
         
                Player player = playerManager.search(id);
                if(player==null){
                    ViewHandler.print("This player does not exist!");
                    return;
                }
                
                ViewHandler.displayMenu(
                        MenuContainer.getInstance().createUpdatePlayerMenu().getMenu(),
                        MenuContainer.getHeader(MenuHeaderType.UPDATE_Player_MENU_HEADER)
                );
                
                int choice = Inputter.inputInteger("Input your option: ","Invalid option!\nPlease re-enter!", 0, 4);
                switch(choice){
                    case -1:
                    case 0:
                        return;
                    case 1:
                        player = updatePlayerName(player);
                        break;
                    case 2:
                        player = updatePlayerPosition(player);
                        break;
                    case 3:
                        player = updatePlayerShirtNumber(player);
                        break;
                }
                if(choice != MenuContainer.getInstance().getNumberOfOptions()-1)
                    playerManager.update(player.getPlayerId(), player);
            }
        }
        
        public Player updatePlayerName(Player player){
            String name = Inputter.inputStringAndLoop("Input new name: ","Invalid name\nPlease enter again!", Acceptable.PLAYER_NAME_VALID);
            if(name==null)
                return player;
            
            return player.setPlayerName(name);
            
        }
        
        public Player updatePlayerPosition(Player player){
            PlayerType playerType = Inputter.inputPlayerType("Input position: ");
            if(playerType == null)
                return player;
            
            int shirtNumber = player.getShirtNumber();
            player.getApiClubManager().deleteShirtNumber(player.getClubId(), shirtNumber);
            Player updatedPlayer = Player.getNewPlayer(playerType)
                                            .setPlayerId(player.getPlayerId())
                                            .setPlayerName(player.getPlayerName())
                                            .setClubId(player.getClubId())
                                            .setShirtNumber(shirtNumber);
            
           return updatedPlayer; 
        }
        
        public Player updatePlayerShirtNumber(Player player){
            int newShirtNumber = Inputter.inputInteger("Input new shirt number: ","Please input a valid shirt number!(1-99)", 1, 99);
            ClubAndPlayerConnection checker = player.getApiClubManager();
            if(Acceptable.checkExistShirtNumber(player.getClubId(), newShirtNumber, checker)){
                ViewHandler.printError("This shirt number already exist in this club!\n");
                return player;
            }         
            return player.setShirtNumber(newShirtNumber);
        }
    },
    
    LIST_ALL_PLAYERS_BY_POSITION {
        @Override
        public void processOption(Manager<Player> playerManager, Manager<Club> clubManager) {
            PlayerType playerType = Inputter.inputPlayerType("Player type: Defender, Winger, Forward, Goalkeeper, Midfielder\nInput player type: ");
            if(playerType == null)
                return;
            
            PlayerManager tempCastingPlayerManager  = (PlayerManager)playerManager;
            tempCastingPlayerManager.show(tempCastingPlayerManager.filterBySpecificPosition(playerType));
            
        }
    },
    
    SAVE_DATA {
        @Override
        public void processOption(Manager<Player> playerManager, Manager<Club> clubManager) {
           clubManager.saveData();
           playerManager.saveData();
           ViewHandler.print("Save successfully");
        }
    },
     
    LOAD_DATA {
        @Override
        public void processOption(Manager<Player> playerManager, Manager<Club> clubManager) {
           boolean isLoadSuccess ;  
           isLoadSuccess =  ((ClubManager)clubManager).loadData() &&((PlayerManager)playerManager).loadData();
          
           if(isLoadSuccess)
             ViewHandler.print("Load data successfully!\n");
             
           else
             ViewHandler.print("Load data failed!\n");
        }
    };
    
    
    public abstract void processOption(Manager<Player> playerManager, Manager<Club> clubManager);

    public static OptionProcessor get(int option){
        return OptionProcessor.values()[option];
    }
}