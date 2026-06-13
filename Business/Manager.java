package Business;

import Selection.ManagerType;
import java.util.Collection;
import java.util.Collections;
import java.util.HashMap;

/**
 *
 * @author admin
 */
public abstract class Manager<E> implements ExistIDChecker{
    private HashMap<String, E> dataManager = new HashMap<>();
    private boolean saveStatus = true;
    
    public void setSaveStatus(boolean status){
        this.saveStatus = status;
    }
    
    public boolean getSaveStatus(){
        return this.saveStatus;
    }
    
    public boolean add(String id,E data){
        boolean isAddSuccess;
        if(this.containId(id))
            return false;
        
        isAddSuccess = dataManager.put(id.toUpperCase(), data)==null;
        if(isAddSuccess)
            saveStatus = false;
        
        return isAddSuccess;
    }
    
    public abstract void show();
    
    public boolean update(String id,E data){
       
        if(this.containId(id)){
            saveStatus = false;
            return dataManager.put(id.toUpperCase(), data)!=null;
        }
        else
            return false;
    }
    
    public boolean remove(String id){
        boolean isDeleteSuccess = dataManager.remove(id.toUpperCase())!=null;
        if(isDeleteSuccess){
            saveStatus = false;
        }
        
        return isDeleteSuccess;
    }
    
    public abstract String getPathFile();
    
    public abstract boolean saveData();
    
    public abstract boolean loadData() throws NullPointerException, NumberFormatException;
    
    @Override
    public boolean containId(String id){
        return dataManager.containsKey(id.toUpperCase());
    }
    
    public E search(String id){
        return dataManager.get(id.toUpperCase());
    }
    
    //Avoid clear from main and controller class
    protected void clear(){
        this.dataManager = new HashMap<>();
    }
    
    public static Manager getNewManagerList(ManagerType listType){
       return listType.createNewManager();
        //null = invalid manage list
    }
    
   
    public Collection<E> getReadOnlyManagerList(){
        return Collections.unmodifiableCollection(this.dataManager.values());
    }
}
