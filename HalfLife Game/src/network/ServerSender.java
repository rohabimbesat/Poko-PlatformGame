package network;
import java.net.*;
import java.io.*;
import java.util.concurrent.*;


/**
 * ServerSender --- Continuously reads from message queue for a particular client, forwarding to that client
 * @author Daniel
 *
 */
public class ServerSender extends Thread {
	private BlockingQueue<Message> clientQueue;
	private ObjectOutputStream client;
	private ClientTable clientTable;
	
	/**
	 * Constructor for ServerSender
	 * @param q Queue of messages to send
	 * @param c ObjectOutputStream writing to the open socket to client
	 * @param t Pointer to ClientTable
	 */
	public ServerSender(BlockingQueue<Message> q, ObjectOutputStream c, ClientTable t) {
		clientQueue = q;   
		client = c;
		clientTable = t;
	}

	/**
	 * Run sender loop as separate thread
	 */
	public void run() {
		try {
			while (clientTable.getServerRunning()) {
		        Message msg = clientQueue.take();
				client.writeObject(msg);
				client.flush();
			}
		}
		catch (IOException e) {
				Report.error("Communication broke in ServerSender" + e.getMessage());
		} catch (InterruptedException e) {
			// Return to infinite while loop.
		}
	}
	
}
