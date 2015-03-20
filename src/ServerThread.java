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
	
	Worker mWorker = new Worker();
	
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
			
			InputStream in = null;
			DataInputStream din = null;
			OutputStream out = null;
			DataOutputStream dos = null;
			
			Socket socket = null;
			try {
				socket = mSocketQueue.take();
				System.out.println("take a socket  from block queue");
			} catch (InterruptedException e1) {
				// TODO Auto-generated catch block
				e1.printStackTrace();
			}
			
			if (socket == null) {
				continue;
			}
			
			mSocketList.add(socket);
			System.out.println("add a socket mSocketList");
			for (Socket s : mSocketList) {
				System.out.println("遍历socket");
				try {
					in = s.getInputStream();
					din = new DataInputStream(in);
					String modleString = din.readUTF();
					ProtocolModle protocolModle = new ProtocolModle(modleString);
					
//					mWorker.work(protocolModle);
					
					out = s.getOutputStream();
					dos = new DataOutputStream(out);
					dos.writeUTF(mWorker.work(protocolModle));
					
				} catch (IOException e) {
					System.out.println(e);
				} finally {
					/*if (in != null) {
						try {
							in.close();
						} catch (IOException e) {
							e.printStackTrace();
						}
					}
					if (out != null) {
						try {
							out.close();
						} catch (IOException e) {
						}
					}*/
				}
				
			}
		}
	}
}
