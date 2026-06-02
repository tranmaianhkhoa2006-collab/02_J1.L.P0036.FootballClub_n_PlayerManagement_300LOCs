package Utils;


/**
 *
 * @author admin
 */
public interface Acceptable {
       public final String  PLAYER_ID_VALID  = "^[Pp]\\d{4}$"    ;
       
       public final String  PLAYER_NAME_VALID  = "^[A-Z a-z]{1,35}$"    ;
       
       public final String  CLUB_NAME_VALID = "^[A-Za-z0-9 ]{1,40}$"; 
       
       public final String  SPONSOR_BRAND_VALID = "^[A-Za-z0-9 -,$._]+$";
       
       public final String  DOUBLE_VALID = "^([-])?\\d+([.,]\\d+)?$"  ;
       
       public final String  INTEGER_VALID = "^\\d+$";
       
       public final String  CLUB_ID_VALID = "^[Cc][Ll]-\\d{4}$";
       
     
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
           
           //Dev
           String minLengthString ;
           String maxLengthString;
           
           
           if(firstString.length()<secondString.length()){
               minLengthString= firstString;
               maxLengthString= secondString;
           }
           else {
               minLengthString = secondString;
               maxLengthString = firstString;
           }
           
           int minLength = minLengthString.length();
           boolean isEqual = false;
           for(int i=0;i<=maxLengthString.length()-minLength;i++){
              isEqual = minLengthString.equalsIgnoreCase(maxLengthString.substring(i, i+minLength));
              if(isEqual){
                  break;
              }
              
           }
           
           return isEqual;
       }
       
       
}
