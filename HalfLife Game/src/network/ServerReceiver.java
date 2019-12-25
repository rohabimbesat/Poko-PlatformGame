package network;
import java.net.*;
import java.io.*;
import java.util.concurrent.*;

/**
 * ServerReceiver --- Receives messages from clients through socket whilst
 * from a separate thread. Passes messages to client Table
 * 
 * @author Daniel
 *
 */
public class ServerReceiver extends Thread {
	private String myClientsName;
	private ObjectInputStream myClient;
	private ClientTable clientTable;
	
	/**
	 * Constructor for ServerReceiver
	 * @param n Client name - name of the connected client, e.g. player0
	 * @param c ObjectInputStream reading from the open socket to client
	 * @param t Pointer to ClientTable class
	 */
	public ServerReceiver(String n, ObjectInputStream c, ClientTable t) {
		myClientsName = n;
		myClient = c;
		clientTable = t;
	}

	/**
	 * Run receiver loop as separate thread
	 */
	public void run() {
		try {
			while (clientTable.getServerRunning()) {
					Message receivedMessage = (Message) myClient.readObject();
					
					//Security measure set message sender to what we know the sender is
					//(prevents forging sender and manipulating another player)
					receivedMessage.setSender(myClientsName);
					//System.out.println(receivedMessage.toString());
					String recipient = "server";
					BlockingQueue<Message> recipientsQueue = clientTable.getQueue(recipient); // Matches EEEEE in ServerSender.java
					if (recipientsQueue != null)
						recipientsQueue.offer(receivedMessage);
					else
						Report.error("Message for non existent client "+ recipient + ": " + receivedMessage.toString());
					
				}
		}
		catch (IOException e) {
			clientTable.remove(myClientsName);
			Report.error("Something went wrong with the client " + myClientsName + " " + e.getMessage()); 
		} catch (ClassNotFoundException e) {
			e.printStackTrace();
		}
	}
	
}


