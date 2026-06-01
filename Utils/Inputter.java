package Utils;


import Selection.MenuHeaderType;
import Selection.PlayerType;
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
     
     public static String inputStringAndLoop(String mess, String errorMess, String pattern){
        String returnValue;
        boolean isValidString = true;
        int count=0;
         do{
       
                count++;
            
               if(count>3){
                   ViewHandler.displayMenu(MenuContainer.getInstance().createYesNoMenu().getMenu(),MenuContainer.getHeader(MenuHeaderType.YES_NO_MENU_HEADER));
                   int choice = inputInteger("Do you want of continue?: ","Invalid choice, please enter again!",0, 1);
                   if(choice == 0){
                       count = 0;
                   }
                   else if(choice == 1){
                       return null;
                   }
               }
            
            returnValue = inputString(mess);
            
            isValidString = Acceptable.isValid(returnValue, pattern);
                    
            if(!isValidString && count <=3)
                   ViewHandler.printError(errorMess+"\n");
             
        }
        while(!isValidString);
         
         return returnValue;
     }
     
     public static String inputStringAndLoopForUpdate(String mess, String errorMess, String pattern){
        String returnValue;
        boolean isValidString = true;
        int count = 0;
         do{
       
                count++;
            
              if (count > 3) {
                 ViewHandler.displayMenu(
                         MenuContainer.getInstance().createYesNoMenu().getMenu(),
                         MenuContainer.getHeader(MenuHeaderType.YES_NO_MENU_HEADER)
                 );
                 int choice = -1;
                 while (choice != 0 || choice != 1) {
                     choice = inputInteger("Do you want of continue?: ", "Invalid choice, please enter again!", 0, 1);
                     switch (choice) {
                         case 0:
                             count = 0;
                             break;
                         case 1:
                             return null;
                         default:
                     }

                 }
             }
               
            returnValue = inputString(mess);
            if(returnValue.isEmpty())
                return null;
            
            isValidString = Acceptable.isValid(returnValue, pattern);
                    
            if(!isValidString && count <=3)
                   ViewHandler.printError(errorMess+"!\n");
             
        }
        while(!isValidString);
         
         return returnValue;
     }
     
     public static int inputInteger(String mess,String errorMess){
         String returnValue;
         boolean isValid;
         do{
             returnValue = inputString(mess);
             isValid = Acceptable.isValid(returnValue,Acceptable.INTEGER_VALID);
             if(!isValid)
                    ViewHandler.printError(errorMess+"\n");
         }
         while(!isValid);
         return Integer.parseInt(returnValue);
     }
     
     public static int inputInteger(String mess, String errorMess, int min){
         int returnValue;
         boolean isValid;
         int attempt=0;
         do{
              attempt++;
            
               if(attempt>3){
                   ViewHandler.displayMenu(
                           MenuContainer.getInstance().createYesNoMenu().getMenu(),
                           MenuContainer.getHeader(MenuHeaderType.YES_NO_MENU_HEADER)
                   );
                   
                   int choice=-1;
               while(choice !=0 || choice !=1){
                   choice = inputInteger("Do you want of continue?: ","Invalid choice, please enter again!",0, 1);
                   switch(choice){
                            case 0:
                                attempt = 0;
                                break;
                            case 1:
                                return min-1;
                            default:
                                ViewHandler.printError("Invalid choice, please enter again!");
                                break;
                        }
                   }
                   
               }
               
             returnValue = Inputter.inputInteger(mess,"Please input a number!");
             isValid = returnValue >= min;
             if(!isValid)
                    ViewHandler.printError(errorMess+"\n");
         }
         while(!isValid);
         return returnValue;
     }
     
     
     public static double inputDouble(String mess,String errorMess){
         String returnValue;
       
            returnValue = inputStringAndLoop(mess,errorMess, Acceptable.DOUBLE_VALID);
          
         return Double.parseDouble(returnValue);
     }
     
     public static double inputDouble(String mess, double min,String errorMess){
         double returnValue;
         do{
             returnValue = inputDouble(mess,errorMess);
             if(returnValue== min-1)
                 return min - 1;    
         }
         while(returnValue < min);
         return returnValue;
     }
     
     public static PlayerType inputPlayerType(String mess){
         PlayerType type;
         int count=0;
         do{
             count++;
               
            
               if(count>3){
                   ViewHandler.displayMenu(
                           MenuContainer.getInstance().createYesNoMenu().getMenu(),
                           MenuContainer.getHeader(MenuHeaderType.YES_NO_MENU_HEADER)
                   );
                   
                   int choice = inputInteger("Do you want of continue?: ","Invalid choice, please enter again!",0, 1);
                  
                   if(choice == 1)
                       return null;
                   else
                       count = 0;
                }
             
             type = PlayerType.searchPlayerType(inputString(mess));
             
             if(type == null)
                 ViewHandler.printError("Please enter a valid player type!"+"\n");
         }
         while(type == null);
         return type;
     }
     
     public static int inputInteger(String mess, String errorMess,int min , int max){
         int returnValue;
         boolean isValidChoice = false;
         do{
              returnValue = Inputter.inputInteger(mess,"Please input number!");
              isValidChoice = Acceptable.isDigitInRange(returnValue, min-1, max);
              
              if(!isValidChoice)
                  ViewHandler.printError(errorMess+"\n");
              
         }
         while(!isValidChoice);
         return returnValue;
     }
     
     
     
}
