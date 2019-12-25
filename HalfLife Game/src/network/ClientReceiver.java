package network;

import java.io.*;
import java.net.*;
import java.util.concurrent.BlockingQueue;

// Gets messages from other clients via the server (by the
// ServerSender thread).
/**
 * ClientReceiver --- Receives messages from server through socket whilst
 * from a separate thread. Puts messages in queue for collection by game
 * 
 * @author Daniel
 *
 */
public class ClientReceiver extends Thread {

  private ObjectInputStream server;
  private BlockingQueue<Message> recipientsQueue;
/**
 * Constructor for ClientReceiver
 * @param q Queue to put received messages into
 * @param server ObjectInputStream reading from the open socket to server
 */
  ClientReceiver(BlockingQueue<Message> q, ObjectInputStream server) {
    this.server = server;
    recipientsQueue = q;
  }
  /**
   * Run receiver loop as separate thread
   */
  public void run() {
    // Print to the user whatever we get from the server:
    try {
      while (true) {
		Message receivedMessage = (Message) server.readObject();
		receivedMessage.setSender("server");
        if (receivedMessage != null) {     
        	recipientsQueue.offer(receivedMessage);
          /*System.out.println(receivedMessage);*/
        }
        else
          Report.errorAndGiveUp("Server seems to have died"); 
      }
    }
    catch (IOException e) {
      Report.errorAndGiveUp("Server seems to have died " + e.getMessage());
    } catch (ClassNotFoundException e) {
		// TODO Auto-generated catch block
		e.printStackTrace();
	}
  }
}

