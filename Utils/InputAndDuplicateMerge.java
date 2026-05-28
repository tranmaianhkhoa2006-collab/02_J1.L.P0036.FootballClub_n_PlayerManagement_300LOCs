package Utils;

import Model.ManagerLimitMethodAccess;
import static Utils.Inputter.inputChoice;

/**
 *
 * @author admin
 */
public class InputAndDuplicateMerge {
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
                            mess = "Input player ID";
                     
                    id = Inputter.inputStringAndLoop(mess, idPattern);
                    if (id == null) {
                        return null;
                    }
                    
                    if(duplicateChecker.containId(id)){
                        if(idPattern.equals(Acceptable.CLUB_ID_VALID))
                            ViewHandler.print("This club ID already exists!");
                        else
                            ViewHandler.print("This player ID already exists!");
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
            id = Inputter.inputStringAndLoop("Input player club id", Acceptable.CLUB_ID_VALID);
            if (id == null) {
                return null;
            }

            if (!duplicateChecker.containId(id)) {
                ViewHandler.print("This club id does not exist");
            }
            else
                break;
        } 
        while (true);
        return id;
        
    }

           
}  

