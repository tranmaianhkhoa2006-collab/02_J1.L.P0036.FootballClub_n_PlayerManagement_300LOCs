package Controller;

import Model.ListType;
import java.util.Collection;
import java.util.Collections;
import java.util.HashMap;
import CallBackInterface.LimitMethodAccessManager;

/**
 *
 * @author admin
 */
public abstract class Manager<E> implements LimitMethodAccessManager{
    private HashMap<String, E> dataManager = new HashMap<>();
    
    public boolean add(String id,E data){
         return dataManager.put(id, data)!=null;
    }
    
    public abstract void show();
    
    public boolean update(String id,E data){
       
        if(dataManager.containsKey(id))
            return dataManager.put(id, data)!=null;
        else
            return false;
    }
    
    public boolean remove(String id){
        return dataManager.remove(id)!=null;
    }
    
    public abstract String getPathFile();
    
    public abstract boolean saveData();
    
    public abstract boolean loadData();
        
    
    public boolean containId(String id){
        return dataManager.containsKey(id);
    }
    
    public E search(String id){
        return dataManager.get(id);
    }
    
    public static Manager getNewManagerList(ListType listType){
       return listType.createNewManager();
        //null = invalid manage list
    }
   
    public Collection<E> getReadOnlyManagerList(){
        return Collections.unmodifiableCollection(this.dataManager.values());
    }
}
