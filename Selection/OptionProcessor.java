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
import Utils.FileIOHandler;

/**
 *
 * @author admin
 */
public enum OptionProcessor {
    SAVE_BEFORE_EXITING_PROGRAM {
        @Override
        public void processOption(Manager<Player> playerManager, Manager<Club> clubManager) {
            if(!playerManager.getSaveStatus() || !clubManager.getSaveStatus()){                
                OptionProcessor.SAVE_DATA.processOption(playerManager, clubManager);
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
                ViewHandler.printError("This club ID already exists!\n");
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

            double budget = Inputter.inputDouble("Input club budget: ", 1, 
                    "Please enter valid budget or press 0 to return to menu!");
            
            if(budget == 0)
                return;

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
            else{
                ViewHandler.printError("Added club unsuccessfully\n");
            }

        }
    },
    
    SEARCH_FOR_CLUB_ID {
        @Override
        public void processOption(Manager<Player> playerManager, Manager<Club> clubManager) {
            String clubId = Inputter.inputString("Input id to search: ");
            
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
                String clubId = Inputter.inputString("Input club id: ");
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
                            clubManager.update(tempClub.getClubId(), tempClub);
                            break;
                        default:
                            ViewHandler.printError("Invalid choice!\n");
                    }
                    
                    Inputter.inputString("Press enter to continue!\n");
                    ViewHandler.fakeClearScreen();
                    
                    if(choice == 5){
                        clubManager.setSaveStatus(false);
                        break;
                    }
                    
                    if(choice == 0)
                         clubManager.setSaveStatus(false); 
                 
                }
            }
            
        }

        public void updateClubName(Club club) {
            ViewHandler.print("Valid name: Need to contain atleast 1 character, No special character\n");
            String newName = Inputter.inputStringAndLoopForUpdate("Input new club name: ",
            "Please enter a valid club name!\n", Acceptable.CLUB_NAME_VALID);
            
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
            ViewHandler.print("If you change your mind, please press 0 and press enter\n");
            
                double newBudget = Inputter.inputDouble("Input new budget: ",1,
                    "Please enter a valid budget!" );
            
            if (newBudget == 0) {
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
                
                String searchName = Inputter.inputString("Input name (partial or full) to search: ");
                
                if(searchName == null)
                    return;

                tempCastingManager.show(tempCastingManager.searchByName(searchName));
        }
    },
    
    ADD_PLAYER {
        @Override
        public void processOption(Manager<Player> playerManager, Manager<Club> clubManager) {
            
            //can not add player when there is no club.
            if(clubManager.getReadOnlyManagerList().isEmpty()){
                ViewHandler.printError("Please add club before adding new players");
                return;
            }
            
            String id = Inputter.inputStringAndLoop("Input ID(Format: PXXXX (XXXX is from 0001-9999)): ","Invalid format!", Acceptable.PLAYER_ID_VALID);
            if(id == null)
                return;
            else if(Acceptable.checkExistID(id, playerManager)){
                ViewHandler.printError("This player ID already exists!\n");
                return;
            }
            
            String name = Inputter.inputStringAndLoop("Input player name: ",
                    "Please enter correct name!\nName can not contain numbers and special characters", Acceptable.PLAYER_NAME_VALID);
            if(name == null)
                return;
            
            ViewHandler.print(String.format("%-"+ViewHandler.CLUB_TABLE_LENGTH+"s","Club List"));
            clubManager.show();
            String clubId = Inputter.inputStringAndLoop("Input club id(Format: CL-XXXX | XXXX is from 0001-9999): ", "Invalid club id format", Acceptable.CLUB_ID_VALID);
            if(clubId == null)
                return;
            else if(!Acceptable.checkExistID(clubId, clubManager)){
                ViewHandler.printError("This club does not exist!");
                return;
            }
            
            int shirtNumber=  Inputter.inputInteger("Input shirt number(1->99) or press 0 to return: ","Invalid shirt number!", 1, 99);
            if(shirtNumber == 0)
                return;
            
            if(Acceptable.checkExistShirtNumber(clubId, shirtNumber, (ClubManager)clubManager)){
               ViewHandler.printError("This shirt number already exists in this club!\n");
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
            String id = Inputter.inputString("Input id to delete: ");
            if(id == null)
                return;
            
            Player player = playerManager.search(id.toUpperCase());
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
             while(true){   
                ViewHandler.print(ViewHandler.lineBreak(70));
                ViewHandler.print(player.toString());
                ViewHandler.print(ViewHandler.lineBreak(70));
                
                ViewHandler.displayMenu(
                        MenuContainer.getInstance().createUpdatePlayerMenu().getMenu(),
                        MenuContainer.getHeader(MenuHeaderType.UPDATE_PlAYER_MENU_HEADER)
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
                if(choice != 4)
                    playerManager.update(player.getPlayerId(), player);
                
                if(choice == 4)
                    break;
                    
                Inputter.inputString("Enter to continue!");
                ViewHandler.fakeClearScreen();
              }
           }
        }
        
        public Player updatePlayerName(Player player){
            String name = Inputter.inputStringAndLoopForUpdate("Input new name: ","Invalid name\nPlease enter again!", Acceptable.PLAYER_NAME_VALID);
            if(name==null)
                return player;
            
            return player.setPlayerName(name);
            
        }
        
        public Player updatePlayerPosition(Player player){
            PlayerType playerType = Inputter.inputPlayerType("Player type: Defender, Winger, Forward, Goalkeeper, Midfielder\nInput position: ");
            if(playerType == null)
                return player;
            
            int shirtNumber = player.getShirtNumber();
            player.getApiClubManager().deleteShirtNumber(player.getClubId(), shirtNumber);
            Player updatedPlayer = Player.getNewPlayer(playerType)
                                            .setApiClubManager(player.getApiClubManager())
                                            .setPlayerId(player.getPlayerId())
                                            .setPlayerName(player.getPlayerName())
                                            .setClubId(player.getClubId())
                                            .setShirtNumber(shirtNumber);
            
           return updatedPlayer; 
        }
        
        public Player updatePlayerShirtNumber(Player player){
            int newShirtNumber = Inputter.inputInteger("Input new shirt number: ","Please input a valid shirt number!(1-99)", 1, 99);
            if(newShirtNumber==0)
                return player;
            
            if(newShirtNumber == player.getShirtNumber()){
                ViewHandler.printError("This shirt number is this player current shirt number!\n");
                return player;
              }
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
           if(playerManager.getReadOnlyManagerList().isEmpty() && clubManager.getReadOnlyManagerList().isEmpty()){
               ViewHandler.printError("There is no data to save\n");
               return;
           }
            
           boolean isClubSaveSuccess = clubManager.saveData();
           boolean isPlayerSaveSuccess = playerManager.saveData();
           if(isClubSaveSuccess && isPlayerSaveSuccess){
               ViewHandler.print("Save successfully\n");
           }
           else
               ViewHandler.printError("Save unsuccessfully\nPlease contact developer for support");
        }
    },
     
    LOAD_DATA {
        @Override
        public void processOption(Manager<Player> playerManager, Manager<Club> clubManager) {
            try {                            
                boolean isClubLoadSuccess = clubManager.loadData() ;
                if(!isClubLoadSuccess)
                    throw new IllegalArgumentException("LOAD FAILED TYPE: CLUB_MANAGER\n");
                    
                boolean isPlayerLoadSuccess = playerManager.loadData();
                if(!isPlayerLoadSuccess)
                    throw new IllegalArgumentException("LOAD FAILED TYPE: PLAYER_MANAGER\n");
    
                    ViewHandler.print("Load data successfully!\n");
                
            } 
            catch (NumberFormatException | NullPointerException e) {
                ViewHandler.printError("Load data failed!\n");
                FileIOHandler.logWriter(e + "-" + e.getMessage());
            }
            catch (IllegalArgumentException ex){
                 ViewHandler.printError("Load data failed!\n");
                FileIOHandler.logWriter(ex + "-" + ex.getMessage());
            }
        }
    };
    
    
    public abstract void processOption(Manager<Player> playerManager, Manager<Club> clubManager);

    public static OptionProcessor get(int option){
        if(!(option<=OptionProcessor.values().length && option>=0))
            return null;
        
        return OptionProcessor.values()[option];
    }
}