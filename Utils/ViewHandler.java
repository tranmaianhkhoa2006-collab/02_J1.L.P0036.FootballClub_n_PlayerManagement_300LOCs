

package Utils;

import java.text.NumberFormat;
import java.util.List;
import java.util.Locale;
/**
 *
 * @author admin
 */
public class ViewHandler {
    public static int PLAYER_TABLE_LENGTH = 
            ViewHandler.attributeOfPlayerList("","","","","").length();
    public static int CLUB_TABLE_LENGTH =
            ViewHandler.attributeOfClubList("","","","","").length();
    
    //using local and numberformatter to get vietnam money format
     public static String getVietnamMoneyFormat(double fee){
         Locale vn = new Locale("vi", "vn");
         NumberFormat moneyFormatter = NumberFormat.getCurrencyInstance(vn);
         String returnValue = moneyFormatter.format(fee);
         
         return returnValue.substring(0, returnValue.length()-2);
         
     }
     
     //return row of studentList with player info or return attribute for its table header
     public static String attributeOfPlayerList(String playerId, String playerName, String clubName, String shirtNumber,String position) {
         String returnValue =
                 String.format("| %-13s| %-35s| %-40s| %-13s| %-20s|", playerId,playerName,clubName,shirtNumber,position);
         return returnValue;
     }
     
     public static String attributeOfClubList(String clubId, String clubName, String sponsorBrand,String budget,String numberOfPlayers){
         String returnValue =
                 String.format("| %-10s| %-40s| %-25s| %-20s| %-20s|",clubId,clubName,sponsorBrand,budget,numberOfPlayers);
         return returnValue;
     }
     
     //return linebreak
     public static String lineBreak(int length){
         StringBuilder returnValue = new StringBuilder();
         returnValue.append("\n");
         for(int i=0;i<length;i++){
             returnValue.append("-");
         }
         returnValue.append("\n");
        
         return returnValue.toString();
     }
     
     //use for print message
      public static void print(String mess){
          System.out.print(mess);
      }
      
      //print error
      public static void printError(String mess){
          System.err.print(mess);
      }
      
      //get String menuHeader from method static String getHeader(Header Type) and
      //display menu from MenuContainer
      public static void displayMenu(List<String> menu,String menuHeader){
           ViewHandler.print(menuHeader);
           int count=0;
           String pattern = "| %-"+(MenuContainer.HEADER_WIDTH-4)+"s |";
           for(String option : menu){
               ViewHandler.print(String.format(pattern+"\n",(count++)+"."+option));
           }
           ViewHandler.print(String.format(pattern,""));
           ViewHandler.print(ViewHandler.lineBreak(MenuContainer.HEADER_WIDTH));   
           ViewHandler.print("Note: The system will ask for whether continuing to input information after 3 times failed except choosing options\n");
      }
      
      //name formatter uppercase first character and lowercase remainning character
      public static String nameFormatter(String rawString){
          if(rawString == null)
              return null;
          
          
          String words[] = rawString.toLowerCase().trim().split("\\s+");
          StringBuilder returnValue = new StringBuilder();
          
          for(String word : words){
              returnValue.append(word.substring(0,1).toUpperCase()).append(word.substring(1)).append(" ");
          }
          
          return returnValue.toString().trim();
      }
      
      //for display a list of Player
      
      
      //for display a list of club
  
     
     
     public static void fakeClearScreen(){

         for(int i=0;i<80;i++){
             System.out.println();
         }    
     }
}
