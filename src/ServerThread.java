import java.io.DataInputStream;
import java.io.DataOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.net.Socket;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.concurrent.BlockingQueue;
import java.util.concurrent.LinkedBlockingQueue;
import java.util.concurrent.PriorityBlockingQueue;

public class ServerThread extends Thread {
	static String hello = "From Server: Hello world";
	
	BlockingQueue<Socket> mSocketQueue = new LinkedBlockingQueue<Socket>();
	
	Map<String, ProtocolModle> mPool = new HashMap<String, ProtocolModle>();
	
	List<Socket> mSocketList = new ArrayList<Socket>();
	
	private static ServerThread sInstance;
	
	public static ServerThread getInstance() {
		if (sInstance == null) {
			sInstance = new ServerThread();
		}
		return sInstance;
	}
	
	private ServerThread() {		
		
	}
	
	public void addSocket(Socket socket) {
		mSocketQueue.add(socket);
	}
	
	public void run() {
		
		while (true) {
			Socket socket = null;
			try {
				socket = mSocketQueue.take();
				System.out.println("take a socket from block queue");
			} catch (InterruptedException e1) {
				e1.printStackTrace();
			}
			
			if (socket == null) {
				continue;
			}

			//将新的socket连接保存起来
			GlobalSocketStore.put(socket.getRemoteSocketAddress().toString(), socket);
			System.out.println("a new socket put into GlobalSocketStore: " + socket.getRemoteSocketAddress().toString());
			
			//进行流操作
			new DataChangeThread(socket).start();
			System.out.println("start a new thread to deal with data");
		}
	}
}
