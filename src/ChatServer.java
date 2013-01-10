import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.PrintStream;
import java.io.PrintWriter;
import java.io.Reader;
import java.net.*;

public class ChatServer extends Thread {
	public final static int PORT = 33003;
	Socket myClientSocket_1;
	String recMesClient;

	public void run() {
		try {
			myClientSocket_1 = new Socket("192.168.1.102", (PORT+1));

			BufferedReader in = new BufferedReader(new InputStreamReader(
					myClientSocket_1.getInputStream()));

			PrintWriter bw = new PrintWriter(
					myClientSocket_1.getOutputStream(), true);

			while (true) {
				recMesClient = MyInput.setMessage();
				bw.println(recMesClient);
				if (recMesClient.equalsIgnoreCase("EXIT")) {
					MyOutput.show("Closing connection.");
					myClientSocket_1.close();
					break;
				}
			}

			myClientSocket_1.close();
		} catch (IOException e) {
			e.printStackTrace();
		}
	}

	public static void main(String[] args) {
		ServerSocket myServSocket;
		String message;
		try {
			myServSocket = new ServerSocket(PORT);
			MyOutput.show("Started on port " + PORT);
			Socket socket = myServSocket.accept();
			MyOutput.show("Connected to " + socket.getInetAddress() + " port "
					+ socket.getPort());
			Thread tr1 = new ChatServer ();
			tr1.setDaemon(true);
			tr1.start();
			BufferedReader isr = new BufferedReader(new InputStreamReader(
					socket.getInputStream()));
			while (true) {
				message = isr.readLine();
				MyOutput.show(socket.getInetAddress() + " : " + message);
				if (message.equalsIgnoreCase("EXIT")) {
					MyOutput.show("Closing connection.");
					myServSocket.close();
					break;
				}

			}
			myServSocket.close();
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}

	}

}
