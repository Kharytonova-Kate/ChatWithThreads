
import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.PrintWriter;
import java.net.*;

public class MyClient extends Thread {

	public static InetAddress serverAddress;
	static Socket myClientSocket;
	static String message;
	String recMessage;
	ServerSocket myServSocket_1;
	public void run () {
		
		try{ 
			myServSocket_1 = new ServerSocket(ChatServer.PORT+1);
			Socket socket = myServSocket_1.accept ();
			BufferedReader isr = new BufferedReader(new InputStreamReader(socket.getInputStream()));
			while (true) {
				recMessage = isr.readLine();
			 MyOutput.show(socket.getInetAddress() + " : " + recMessage);
			 if (recMessage.equalsIgnoreCase("EXIT")) {
				 MyOutput.show("Closing connection.");
				 myServSocket_1.close();
				 break;
			 }
			 
			}
			myServSocket_1.close();
		}
		 catch (IOException e) {
			
			e.printStackTrace();
		}
	}
	
	public static void main(String[] args) throws UnknownHostException,
			SocketTimeoutException {
		serverAddress = InetAddress.getLocalHost();
		
		
		try {
			myClientSocket = new Socket(serverAddress, ChatServer.PORT);
			
			if (myClientSocket.isConnected()) {
				MyOutput.show("Connected to " + serverAddress + " port "
						+ ChatServer.PORT);
				Thread tr = new MyClient();
				tr.setDaemon(true);
				tr.start();
				BufferedReader in = new BufferedReader(new InputStreamReader(
						myClientSocket.getInputStream()));

				PrintWriter bw = new PrintWriter(myClientSocket.getOutputStream(), true);

				while (true) {
					message = MyInput.setMessage();
					bw.println(message);
					if (message.equalsIgnoreCase("EXIT")) {
						MyOutput.show("Closing connection.");
						myClientSocket.close();
						break;
					}
					
				}
			} 
			myClientSocket.close();
		} catch (IOException e) {
			e.printStackTrace();
		}
	}

}
