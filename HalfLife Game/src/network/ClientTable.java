package network;

import java.util.Collection;
import java.util.Set;
import java.util.concurrent.*;
/**
 * ClientTable --- Various resources shared between Server, ServerSender, ServerReceiver threads
 * e.g Queues of messages sent and received listed per connected client
 * 
 * @author Daniel
 *
 */
public class ClientTable {

  private static ConcurrentMap<String,BlockingQueue<Message>> queueTable
    = new ConcurrentHashMap<String,BlockingQueue<Message>>();
  private boolean running = true;
  
  /**
   * Add a new connecting player to the table
   * @param nickname player nickname e.g. player0
   * @return true on success, false on failure
   */
  public boolean add(String nickname) {
	  if(queueTable.get(nickname) == null) {
		  queueTable.put(nickname, new LinkedBlockingQueue<Message>());
		  return true;
	  }
	  else {
		  Report.error("Error: Player with that name is already connected");
		  return false;
	  }
  }

  	/** Remove a player from the table
  	 * 
  	 * @param nickname Nickname of player to remove
  	 */
	public void remove(String nickname) {
		queueTable.remove(nickname);
	}
	/** Gets the contents of the player table
	 * 
	 * @return Set of strings, each string is a player nickname
	 */
	protected Set<String> showAll() {
		return queueTable.keySet();
	}
	
	/** Returns the next message in the queue
	 * Returns null if the nickname is not in the table:
	 *  
	 * @param nickname
	 * @return Get the queue of messages
	 */
	BlockingQueue<Message> getQueue(String nickname) {
		return queueTable.get(nickname);
	}
	/**
	 * Gets the size of the table (i.e number of clients connected)
	 * 
	 * @return int size of table
	 */
	public static int size() {
		return queueTable.size();
	}
	
	/** Gets whether the server is running or not 
	 * 
	 * @return Boolean true if the server is running, false if now
	 */
	boolean getServerRunning() {
		return running;
	}
	/** Called if server is stopping. This stops all sender and receiver threads
	 */
	void stopServer() {
		this.running = false;
		queueTable.clear();
	}
}
