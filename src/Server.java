import java.io.*;
import java.net.*;

/**
 * 
 * @author chenhewen
 * 2015-3-20
 */
public class Server {
	
	@SuppressWarnings("resource")
	public static void main(String[] args) {
		
		ServerSocket serverSocket = null;
		try {
			serverSocket = new ServerSocket(Constant.BIND_PORT);
			System.out.println("server started...");
		} catch (IOException e) {
			System.out.println(e);
			System.exit(1);
		}
		
		int i = 1;
		while (true) {
			Socket socket = null;
			try {
				socket = serverSocket.accept();
				System.out.println("accept a new socket SOCKET_" + i++);
				
				ServerThread serverThread = ServerThread.getInstance();
				serverThread.addSocket(socket);
				System.out.println("add the socket to socket queue");
				
				if (!serverThread.isAlive()) {
					serverThread.start();
					System.out.println("server start a new thread to read and write data");
				} 
				
			} catch (IOException e) {
				System.out.println(e);
			} finally {

			}
		}
	}
}