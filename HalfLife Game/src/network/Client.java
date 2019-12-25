package network;

import java.io.*;
import java.net.*;
import java.util.concurrent.BlockingQueue;
import java.util.concurrent.LinkedBlockingQueue;

import main.Game;
import main.Level_Info;

/**
 * Client --- Attempt to connect to a Server and set up communication threads to it
 * 
 * @author Daniel
 *
 */
public class Client extends Thread {
	ObjectOutputStream toServer;
	ObjectInputStream fromServer;
	Socket server;
	boolean clientStart;
	int port;
	String nickname;
	String hostname;
	private BlockingQueue<Message> sendQueue;
	private BlockingQueue<Message> receiveQueue;
	
	/**
	 * Constructor for client
	 * 
	 * @param port Port number to connect to server on
	 * @param nickname My client nickname
	 * @param hostname Hostname of server to connect to
	 */
	public Client(int port,String nickname,String hostname) {

		// Open sockets:
		toServer = null;
		fromServer = null;
		server = null;
		clientStart = false;
		this.port = port;
		this.nickname = nickname;
		this.hostname = hostname;
		this.sendQueue = new LinkedBlockingQueue<Message>();
		this.receiveQueue = new LinkedBlockingQueue<Message>();
		
	}
	/**
	 * Run client in separate thread
	 */
	public void run(){
		try {
			server = new Socket(hostname, port);
			toServer = new ObjectOutputStream(server.getOutputStream());
			fromServer = new ObjectInputStream(server.getInputStream());
			clientStart = true;
		}
		catch (UnknownHostException e) {
			Report.error("Unknown host: " + hostname);
		} 
		catch (IOException e) {
			Report.error("The server doesn't seem to be running " + e.getMessage());
		}
		
		if(clientStart) {
			// Create two threads to send and receive
			ClientSender sender = new ClientSender(sendQueue,nickname,toServer,server);
			ClientReceiver receiver = new ClientReceiver(receiveQueue, fromServer);
	
			// Run them in parallel:
			sender.start();
			receiver.start();
		
			// Wait for them to end and close sockets.
			try {
				sender.join();
				toServer.close();
				receiver.join();
				fromServer.close();
				server.close();
			}
			catch (IOException e) {
				Report.errorAndGiveUp("Something wrong " + e.getMessage());
			}
			catch (InterruptedException e) {
			Report.errorAndGiveUp("Unexpected interruption " + e.getMessage());
			}
		}
	}
	/**
	 * Method to send a message object to server
	 * @param message Message object to send
	 */
	public void sendToServer(Message message) {
		BlockingQueue<Message> recipientsQueue = sendQueue;
		recipientsQueue.offer(message);
	}
	/**
	 * Method to receive message from server
	 * Beware: Hangs current thread until message is received.
	 * This method should only be called in its own thread
	 * 
	 * @return Message object received
	 * @throws InterruptedException
	 */
	public Message waitForMessage() throws InterruptedException {
		Message msg = receiveQueue.take();
		//Message msg = new Message("asdfghjk");

		//System.out.println(msg.getText());
		
		return msg;
	}
	/**
	 * Gets the queue of messages received by this client
	 * @return BlockingQueue of message objects
	 */
	public BlockingQueue<Message> getRecieved(){
		return receiveQueue;
	}
}
