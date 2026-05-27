package Utils;

import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.io.InputStream;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.io.OutputStream;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

/**
 *
 * @author admin
 */
public class FileIOHandler<E> {

    public final static String LOG_PATH = "data/Log.txt";

    //By using generic
    // Static method for writting log (error)
    public static void logWriter(String mess) {
        try {
            FileWriter fileWriter = new FileWriter(FileIOHandler.LOG_PATH);
            BufferedWriter bufferWriter = new BufferedWriter(fileWriter);
            LocalDateTime timer = LocalDateTime.now();
            bufferWriter.append(timer + ": " + mess+"\n");
        } 
        catch (IOException e) {
             e.printStackTrace(); 
        }

    }

    public static List<String> readStringFile(String pathFile) {
        if(!(new File(pathFile)).exists()){
            return new ArrayList<>();
        }
        
        List<String> returnValue = new ArrayList<>();
        try {

            FileReader fileReader = new FileReader(pathFile);
            BufferedReader bufferReader = new BufferedReader(fileReader);
            bufferReader.readLine();
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

    public List<E> readFileObject(String pathFile) {
         if(!(new File(pathFile)).exists()){
            return new ArrayList<>();
        }
        
        List<E> readFromFileContent = new ArrayList<>();
        try {
            InputStream inputStream = new FileInputStream(pathFile);
            ObjectInputStream objectInputStream = new ObjectInputStream(inputStream);
            readFromFileContent=(ArrayList<E>) objectInputStream.readObject();

        } 
        catch (IOException | ClassNotFoundException e) {
            FileIOHandler.logWriter(e+"-"+e.getMessage());
        }

        return (List<E>) readFromFileContent;
    }

    public boolean writeFileObject(String pathFile, List<E> contentList) {
        
        
        boolean isSave = false;
        try {
            if (!(new File(pathFile)).exists()) {
                (new File(pathFile)).createNewFile();
            }
            OutputStream outputStream = new FileOutputStream(pathFile);
            ObjectOutputStream objectOutputStream = new ObjectOutputStream(outputStream);

                objectOutputStream.writeObject(contentList);
            
            objectOutputStream.close();
            outputStream.close();
            isSave = true;
        } 
        catch (IOException e) {
            FileIOHandler.logWriter(e.getMessage()+"\n");
        }

        return isSave;
    }

}
