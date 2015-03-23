import java.io.*;
import java.net.*;

/**
 * 客户端 哈哈是吧
 * @author chenhewen
 *
 */
public class Client {

	public Client() {
		try {
			Socket s = new Socket("127.0.0.1", 5432);

			OutputStream out = s.getOutputStream();

			DataOutputStream dout = new DataOutputStream(out);

			dout.writeUTF("oftenlin");

			InputStream in = s.getInputStream();
			DataInputStream din = new DataInputStream(in);

			String st = din.readUTF();

			System.out.println(st);
			in.close();
			out.close();
			s.close();
		} catch (IOException e) {
		}
	}

	public static void main(String[] args) {
		new Client();
	}
}