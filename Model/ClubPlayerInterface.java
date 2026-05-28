package Model;

/**
 *
 * @author admin
 */
public interface ClubPlayerInterface {
     String getClubName(String clubId);
     boolean addShirtNumber(String clubId,String playerId,int nums);
     boolean updateShirtNumber(String clubId,int oldNums, int newNums);
     boolean deleteShirtNumber(String clubId,int nums);
}
