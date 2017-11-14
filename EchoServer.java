import java.util.Scanner;
import java.io.*;
import java.net.Socket;
import java.net.ServerSocket;

public class EchoServer extends Thread {
	private Socket socket;
	public EchoServer(Socket s) {
		this.socket = s;
	}

	public static void main(String[] args) {

		  String s;
		  ServerSocket serverSocket = null;
		  Socket socket = null;
		  try
		  {
			 // Wait for connection on port 6789
			 serverSocket = new ServerSocket(6789);
			 while(true) {
				 socket = serverSocket.accept();
				 EchoServer es = new EchoServer(socket);
				 es.start();
			 }

		  } catch (Exception e) {
		     System.out.println("Error " + e);
		  }
		  finally {
      	if (socket != null) {
          try {
          	socket.close();
          } catch (IOException ex) {
					}
        }
      	if (serverSocket != null) {
          try {
          	serverSocket.close();
          } catch (IOException ex) {
					}
      }
    }
	}

	public void run() {
		try {
			System.out.println("Starting new connection... " + socket.getRemoteSocketAddress());
			String s = null;
			Scanner inputStream = new Scanner(new InputStreamReader(socket.getInputStream()));
			PrintWriter outputStream = new PrintWriter(new DataOutputStream(socket.getOutputStream()));

			// Respond to messages from the client
			while(true)
			{
			 s = inputStream.nextLine();
			 System.out.println(s);
			 // exit if message from client is "bye"
			 if(s.equalsIgnoreCase("bye"))
			 {
				 outputStream.println("bye");
				 outputStream.flush();
				 break;
			 }
			 outputStream.println(s);
			 outputStream.flush();
			} //while looop
			System.out.println("Closing connection... " + socket.getRemoteSocketAddress());
			inputStream.close();
			outputStream.close();
		} catch (Exception e) {
		}
	}
}
