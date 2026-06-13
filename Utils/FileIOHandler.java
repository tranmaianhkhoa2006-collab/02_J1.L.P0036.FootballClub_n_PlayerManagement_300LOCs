package Utils;

import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.File;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

/**
 *
 * @author admin
 */
public class FileIOHandler {

    public final static String LOG_PATH = "data/Log.txt";

    public static void logWriter(String mess) {
        try (
                FileWriter fileWriter = new FileWriter(FileIOHandler.LOG_PATH,true);
                BufferedWriter bufferWriter = new BufferedWriter(fileWriter);
              )
        {
            LocalDateTime timer = LocalDateTime.now();
            bufferWriter.append(timer + ": " + mess+"\n");
        } 
        catch (IOException e) {
        }
     

    }

    public static List<String> readStringFile(String pathFile) {
        if(!(new File(pathFile)).exists()){
            return new ArrayList<>();
        }
        
        List<String> returnValue = new ArrayList<>();
        try( FileReader fileReader = new FileReader(pathFile);
                BufferedReader bufferReader = new BufferedReader(fileReader);
             ) 
        {
            String line;
            while ((line = bufferReader.readLine()) != null) {
                returnValue.add(line.trim());
            }

            bufferReader.close();
            fileReader.close();

            return returnValue;
        } 
        catch (IOException e) {
            FileIOHandler.logWriter(e+"-"+e.getMessage());
        }
        
        return returnValue;  
    }
    
    public static boolean writeStringFile(String pathFile, List<String> contentList) {
        
        
        boolean isSave = false;
        if(!(new File(pathFile)).exists())
            return false;
        
        try (
                FileWriter fileWriter = new FileWriter(pathFile);
                BufferedWriter bufferWriter = new BufferedWriter(fileWriter);
              )
        {   
           for(int i = 0;i<contentList.size();i++){
               bufferWriter.write(contentList.get(i)+"\n");
           }
           
            bufferWriter.close();
            fileWriter.close();
            isSave = true;
        } 
        catch (IOException e) {
            FileIOHandler.logWriter(e.getMessage()+"\n");
        }

        return isSave;
    }

}
