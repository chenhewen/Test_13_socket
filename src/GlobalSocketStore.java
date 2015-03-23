import java.net.Socket;
import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;


public class GlobalSocketStore {
	
	public static Map<String, Socket> sGlobalSocketMap = new ConcurrentHashMap<String, Socket>();
	
	public static void put(String id, Socket socket) {
		sGlobalSocketMap.put(id, socket);
	}
	
	public static Socket get(String id) {
		return sGlobalSocketMap.get(id);
	}
	
}
