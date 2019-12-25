package network;

import java.net.*;
import java.io.*;
import java.util.Enumeration;
import java.util.concurrent.*;

import main.Game;

/**
 * Server --- Receives incoming connections from new clients and sets up
 * communication threads for them
 * 
 * @author Daniel
 *
 */
public class Server extends Thread {
	private volatile boolean allowNewPlayers;
	static ClientTable clientTable;
	int numClients;
	int port;
	ServerSocket serverSocket;
	private ServerReceiver receiver;
	private ServerSender sender;
	Socket socket;
	
	/**
	 * Constructor for server
	 * @param port Port to open server on
	 */
	public Server(int port) {
		//Initialise and open Socket
		//Boolean, true if new connections to host are allowed
		this.port = port;
		numClients = 0;
		allowNewPlayers = true;
		clientTable = new ClientTable();
		clientTable.add("server");
		serverSocket = null;
		//running = true;
	}
	/**
	 * Run server loop on separate thread
	 */
	public void run() {
		try {
			serverSocket = new ServerSocket(port);
		} 
		catch (IOException e) {
			System.out.println("Error opening port " + port);
		}
		// Start loop of waiting for new connections
		try { 
			while (allowNewPlayers) {
				// Listen to the socket waiting for new clients wanting to connect
				socket = serverSocket.accept();
				//if(!allowNewPlayers) break;
				String clientName = "Player" + numClients;
				numClients++;
				
				// We add the client to the table checking if already exists:
				if(clientTable.add(clientName)) {
					ObjectInputStream fromClient = new ObjectInputStream(socket.getInputStream());
					
					System.out.println(clientName + " connected");
				
					//Once client is connected, hand over to sender receiver threads
					(new ServerReceiver(clientName, fromClient, clientTable)).start();
	
					// Create and start a new thread to write to the client:
					ObjectOutputStream toClient = new ObjectOutputStream(socket.getOutputStream());
					(new ServerSender(clientTable.getQueue(clientName), toClient, clientTable)).start();
				}else {
					Report.error("client name already in use");
				}
			}	
		}catch (IOException e) {
			Report.error("Network error " + e.getMessage());
		}
	}
	
	/**
	 * Toggle whether new players can connect to the server or not
	 * @param allowNewPlayers Boolean true if new players allowed
	 */
	public void setAllowNewPlayers(boolean allowNewPlayers) {
		this.allowNewPlayers = allowNewPlayers;
	}
	/**
	 * Returns array of the names (player0, player1 etc...) of players currently connected
	 * Will be returned in any order
	 * @return String array of player names
	 */
	public static String[] showConnected() {
		String[] outArr;
		//If client (client table wont exist) etc return empty array
		if (clientTable != null) {
			outArr =new String[ClientTable.size()];
			int i = 0;
			for(String item : clientTable.showAll()){
				outArr[i] = item;
				i++;
			}
		}else{
			outArr = new String[0];
			
		}
		return outArr;
	}
	
	/**
	 * Stops the server and disconnects all clients
	 */
	public void stopServer() {
		System.out.println("Server stopping...");
		allowNewPlayers = false;
		clientTable.stopServer();
		//running = false;
		try {
			if(socket != null) {
				socket.close();
			}
			if(serverSocket != null) {
				serverSocket.close();
			}
		} catch (IOException e) {
			e.printStackTrace();
		}
		Report.behaviour("Server stopped");
		//System.out.println("Server Running: "+this.clientTable.getServerRunning());
	}
	
	/**
	 * Method to send a message object to all connected clients
	 * @param message The message to be sent
	 */
	public void sendToAll(Message message) {
		for(String client : clientTable.showAll()) {
			BlockingQueue<Message> recipientsQueue = clientTable.getQueue(client);
			if(client == "server") {
			}else if (recipientsQueue != null && client != "server") {
				recipientsQueue.offer(message);
			}
			else
				Report.error("Can't/won't send to "+ client + ": " + message);//DEBUG----------------------
		}
	}
	
	/**
	 * Gets message queue of messages received by the server
	 * @return BlockingQueue of message objects
	 */
	public BlockingQueue<Message> getReceived() {
		String recipient = "server";
		BlockingQueue<Message> recipientsQueue = clientTable.getQueue(recipient);
		return recipientsQueue;
	}
	/*
	public ClientTable getclientTable() {
		return clientTable;
	}*/
	
	/**
	 * Get the current LAN IP address of the device
	 * @return LAN IP Address as a String
	 */
	public String getIpAddress() {
		String currentHostIpAddress = "";
		Enumeration e;
		try {
			e = NetworkInterface.getNetworkInterfaces();
			while(e.hasMoreElements()){
			    NetworkInterface n = (NetworkInterface) e.nextElement();
			    Enumeration ee = n.getInetAddresses();
			    while (ee.hasMoreElements()){
			        InetAddress i = (InetAddress) ee.nextElement();
		            if (i.getHostAddress().indexOf(":") == -1 && !i.isLoopbackAddress() && i.isSiteLocalAddress() && currentHostIpAddress.equals("")) {
		                currentHostIpAddress = i.getHostAddress();
		            }
			    }
			}
		} catch (SocketException e1) {
			e1.printStackTrace();
			return null;
		}
		return currentHostIpAddress;
	}
	
	/**
	 * Get the port the server is currently hosted on
	 * @return port number as an Int
	 */
	public int getPort() {
		return port;
	}
}
