package Business;

/**
 *
 * @author admin
 */


public interface ManagerLimitMethodAccess {
    //Limit for input and check id combination module can only acess containId by using functional Interface
    boolean containId(String id);
}
