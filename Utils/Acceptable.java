package Utils;

import Business.ClubAndPlayerConnection;
import Business.ExistIDChecker;


/**
 *
 * @author admin
 */
public interface Acceptable {
       public final String  PLAYER_ID_VALID  = "^[Pp]((000[1-9])|([1-9]{3}[0-9]))$"    ;
       
       public final String  PLAYER_NAME_VALID  = "^[\\p{L} ]{2,}$"    ;
       
       public final String  CLUB_NAME_VALID = "^[\\p{L} 0-9]+$"; 
       
       public final String  SPONSOR_BRAND_VALID = "^[\\p{L} 0-9-,$._]+$";
       
       public final String  DOUBLE_VALID = "^([-])?\\d+([.,]\\d+)?$"  ;
       
       public final String  INTEGER_VALID = "^\\d+$";
       
       public final String  CLUB_ID_VALID = "^[Cc][Ll]-(000[1-9])|([1-9]{3}[0-9])$";
       
     
       public static boolean isValid(String data, String pattern){   
            return data.matches(pattern);
       }
       
       public static boolean isDigitInRange(int digit,int min,int max){
           return digit>=min && digit<=max;
       }
       
       public static boolean isPartialEqual(String firstString,String secondString){
           //If both string are equal return true;
           if(firstString.equals(secondString))
               return firstString.equals(secondString);
           
           boolean isPartialEqualString = 
                   firstString.toLowerCase().contains(secondString.toLowerCase()) || 
                   secondString.toLowerCase().contains(firstString.toLowerCase());
           
           return isPartialEqualString;
       }
       
       public static boolean checkExistID(String id, ExistIDChecker checker){
           return checker.containId(id);
       }
       
       public static boolean checkExistShirtNumber(String clubID,int number, ClubAndPlayerConnection checker){
           return checker.isContainShirtNumber(clubID, number);
       }
}
