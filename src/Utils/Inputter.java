package Utils;


import Model.MenuHeaderType;
import Model.PlayerType;
import java.util.Scanner;

/**
 *
 * @author admin
 */
public class Inputter {
     private static Scanner scan= new Scanner(System.in);
     
     public static String inputString(String mess){
         System.out.print(mess);
         return scan.nextLine().trim();
     }
     
     public static String inputStringAndLoop(String mess, String pattern){
        String returnValue;
        boolean isValidString = true;
        int count=0;
         do{
       
                count++;
            
               if(count>3){
                   ViewHandler.displayMenu(MenuContainer.getInstance().createYesNoMenu().getMenu(),MenuContainer.getHeader(MenuHeaderType.YES_NO_MENU_HEADER));
                   int choice = inputChoice("Do you want of continue?: ",0, 1);
                   if(choice == 0){
                       count =0;
                   }
                   else if(choice == 1){
                       return null;
                   }
               }
            
            returnValue = inputString(mess);
            
            isValidString = Acceptable.isValid(returnValue, pattern);
                    
            if(!isValidString && count <=3)
                   ViewHandler.printError("Please enter again!\n");
             
        }
        while(!isValidString);
         
         return returnValue;
     }
     
       public static String inputStringAndLoopForUpdate(String mess, String pattern){
        String returnValue;
        boolean isValidString = true;
        int count = 0;
         do{
       
                count++;
            
               if(count>3){
                   ViewHandler.displayMenu(
                           MenuContainer.getInstance().createYesNoMenu().getMenu(),
                           MenuContainer.getHeader(MenuHeaderType.YES_NO_MENU_HEADER)
                   );
                   
                   int choice = inputChoice("Do you want of continue?: ",0, 1);
                   
                  switch(choice){
                      case 0:
                          count = 0;
                          break;
                      case 1:
                          return null;
                  }
                  
               }
            
            returnValue = inputString(mess);
            if(returnValue.isEmpty())
                return null;
            
            isValidString = Acceptable.isValid(returnValue, pattern);
                    
            if(!isValidString && count <=3)
                   ViewHandler.printError("Please enter again!\n");
             
        }
        while(!isValidString);
         
         return returnValue;
     }
     
     public static int inputInteger(String mess){
         String returnValue;
         boolean isValid;
         do{
             returnValue = inputString(mess);
             isValid = Acceptable.isValid(returnValue,Acceptable.INTEGER_VALID);
             if(!isValid)
                    ViewHandler.printError("Please enter again!\n");
         }
         while(!isValid);
         return Integer.parseInt(returnValue);
     }
     
     public static double inputDouble(String mess){
         String returnValue;
         boolean isValid;
         do{
             returnValue = inputString(mess);
             isValid = Acceptable.isValid(returnValue,Acceptable.DOUBLE_VALID);
             if(!isValid)
                    ViewHandler.printError("Please enter again!\n");
         }
         while(!isValid);
         return Double.parseDouble(returnValue);
     }
     
      public static double inputDoubleForUpdate(String mess){
         String returnValue;
         boolean isValid;
         do{
             returnValue = inputString(mess);
             if(returnValue.isEmpty())
                 return -1;
             
             isValid = Acceptable.isValid(returnValue,Acceptable.DOUBLE_VALID);
             if(!isValid)
                    ViewHandler.printError("Please enter again!\n");
         }
         while(!isValid);
         return Double.parseDouble(returnValue);
     }
     
     public static double inputDouble(String mess, double min){
         double returnValue;
         do{
             returnValue = inputDouble(mess);
         }
         while(returnValue < min);
         return returnValue;
     }
     
     public static PlayerType inputPlayerType(String mess){
         PlayerType type;
         do{
             type = PlayerType.searchPlayerType(inputString(mess));
         }
         while(type == null);
         return type;
     }
     
     public static int inputChoice(String mess, int min , int max){
         int returnValue;
         boolean isValidChoice = false;
         do{
              returnValue = Inputter.inputInteger(mess);
              isValidChoice = Acceptable.isDigitInRange(returnValue, min, max);
              
              if(!isValidChoice)
                  ViewHandler.printError("Invalid choice, please enter again!\n");
              
         }
         while(!isValidChoice);
         return returnValue;
     }
     
}
