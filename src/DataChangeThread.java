import java.awt.image.BufferStrategy;
import java.io.BufferedInputStream;
import java.io.DataInputStream;
import java.io.DataOutputStream;
import java.io.EOFException;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.net.Socket;
import java.util.Map;
import java.util.Set;

public class DataChangeThread extends Thread {

	Socket mSocket;

	InputStream in = null;
	DataInputStream din = null;
	OutputStream out = null;
	DataOutputStream dos = null;

	Worker mWorker = new Worker();
	DataChangeController mController = new DataChangeController();
	DataParser mParser = new DataParser();

	public DataChangeThread(Socket mSocket) {
		this.mSocket = mSocket;
	}

	@Override
	public void run() {
		while (true) {
			try {
				in = mSocket.getInputStream();
				din = new DataInputStream(in);
				String modleString = din.readUTF();
				
				//ProtocolModle protocolModle = new ProtocolModle(modleString);
				ProtocolModle protocolModle = mParser.parseProtocolModle(modleString);
				//mController.dispatch(protocolModle);
				
				Set<Map.Entry<String, Socket>> entries = GlobalSocketStore.sGlobalSocketMap.entrySet();
				for (Map.Entry<String, Socket> entry : entries) {
					Socket socket = entry.getValue();
					out = socket.getOutputStream();
					dos = new DataOutputStream(out);
					dos.writeUTF(mWorker.work(protocolModle));
				}
				/*out = mSocket.getOutputStream();
				dos = new DataOutputStream(out);
				dos.writeUTF(mWorker.work(protocolModle));*/

			} catch (EOFException e) {
				e.printStackTrace();
				shutDown();
				break;
			} catch (IOException e) {
				e.printStackTrace();
			} finally {

			} 
		}
	}

	public void shutDown() {
		
		//移除socket
		GlobalSocketStore.remove(mSocket.getRemoteSocketAddress().toString());
		
		if (mSocket != null) {
			try {
				mSocket.shutdownInput();
				mSocket.shutdownOutput();
				mSocket.close();
				mSocket = null;
			} catch (IOException e) {
				e.printStackTrace();
			}
		}
		if (in != null) {
			try {
				in.close();
				in = null;
			} catch (IOException e) {
				e.printStackTrace();
			}
		}
		if (out != null) {
			try {
				out.close();
				out = null;
			} catch (IOException e) {
				e.printStackTrace();
			}
		}
	}
}
