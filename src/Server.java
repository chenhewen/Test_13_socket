import java.io.*;
import java.net.*;

/**
 * 
 * @author chenhewen
 * 2015-3-20
 */
public class Server {
	
	public static void main(String[] args) {
		
		ServerSocket serverSocket = null;
		try {
			serverSocket = new ServerSocket(5432);
		} catch (IOException e) {
			System.out.println(e);
			System.exit(1);
		}
		
		int i = 1;
		while (true) {
			Socket socket = null;
			try {
				socket = serverSocket.accept();
				System.out.println("accept a new socket");
				
				ServerThread serverThread = ServerThread.getInstance();
				serverThread.addSocket(socket);
				System.out.println("add a new socket");
				if (!serverThread.isAlive()) {
					serverThread.start();
					System.out.println("server thread start");
				} 
				
				System.out.println("接收了 第" + i + "个Client");
				i++;
			} catch (IOException e) {
				System.out.println(e);
			} finally {

			}
		}
	}
}