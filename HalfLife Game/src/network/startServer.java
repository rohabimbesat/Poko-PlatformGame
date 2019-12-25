package network;
//Class for Testing only
import java.net.*;
import java.util.Enumeration;
import java.io.*;

public class startServer {

	public static void main(String [] args) {
		System.out.println("Start server...");
		//Can't use 0 - 1023, Use 1024 - 65 535
		final int port = 1035;
		System.out.println("port: "+port);
		Server server = new Server(port);
		server.start();
				
		BufferedReader user = new BufferedReader(new InputStreamReader(System.in));
		try {
			while(true) {
				System.out.println("IP Address is: "+server.getIpAddress());
				System.out.println("Full IP Address is: "+server.getIpAddress()+":"+server.getPort());
				System.out.println("----- Inbox: -----");
				System.out.println(server.getReceived());
				System.out.println("----- End Inbox -----");
				System.out.println("Connected:");
				for(String item:server.showConnected()) {
					System.out.println(item);
				}
		    	System.out.println("----- Message Object -----");
				System.out.print("From: ");
				String namefrom;
				namefrom = user.readLine();
				System.out.print("Content: ");
				String msgcontent = user.readLine();
				Message messagetoSend = new Message(msgcontent);
		
				System.out.println("----- ----- ----- -----");
				server.sendToAll(messagetoSend);
				//server.stopServer();
			}
		}
		catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}
}
