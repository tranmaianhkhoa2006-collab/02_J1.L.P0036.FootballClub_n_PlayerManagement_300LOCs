package Controller;

import Selection.ListType;
import java.util.Collection;
import java.util.Collections;
import java.util.HashMap;
import Model.ManagerLimitMethodAccess;

/**
 *
 * @author admin
 */
public abstract class Manager<E> implements ManagerLimitMethodAccess{
    private HashMap<String, E> dataManager = new HashMap<>();
    private boolean saveStatus;
    
    public void setSaveStatus(boolean status){
        this.saveStatus = status;
    }
    
    public boolean getSaveStatus(){
        return this.saveStatus;
    }
    
    public boolean add(String id,E data){
        boolean isAddSuccess;
        if(dataManager.containsKey(id))
            return false;
        
        isAddSuccess = dataManager.put(id.toUpperCase(), data)==null;
        if(isAddSuccess)
            saveStatus = !isAddSuccess;
        
        return isAddSuccess;
    }
    
    public abstract void show();
    
    public boolean update(String id,E data){
       
        if(dataManager.containsKey(id.toUpperCase()))
            return dataManager.put(id, data)!=null;
        else
            return false;
    }
    
    public boolean remove(String id){
        return dataManager.remove(id.toUpperCase())!=null;
    }
    
    public abstract String getPathFile();
    
    public abstract boolean saveData();
    
    public abstract boolean loadData();
        
    
    public boolean containId(String id){
        return dataManager.containsKey(id.toUpperCase());
    }
    
    public E search(String id){
        return dataManager.get(id.toUpperCase());
    }
    
    public static Manager getNewManagerList(ListType listType){
       return listType.createNewManager();
        //null = invalid manage list
    }
   
    public Collection<E> getReadOnlyManagerList(){
        return Collections.unmodifiableCollection(this.dataManager.values());
    }
}
