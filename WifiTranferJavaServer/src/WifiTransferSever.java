import java.io.*;
import java.net.InetAddress;
import java.net.ServerSocket;
import java.net.Socket;

public class WifiTransferSever {

	private static final int RECV_PORT = 6789;
	private static final String TAG = "WifiTransferServer";

	public static void main(String[] args) {

		ServerSocket ssocket;
		try {
			ssocket = new ServerSocket(RECV_PORT);
			InetAddress addr = InetAddress.getLocalHost();
			System.out.println(addr.getHostAddress());
			System.out.println(addr.getHostName());

			System.out.println(TAG + "huanzhiyang");
			Socket sock = ssocket.accept();
			System.out.println("Accept an connection");
			DataInputStream in = new DataInputStream(new BufferedInputStream(
					sock.getInputStream()));
			PrintWriter out = new PrintWriter(new BufferedWriter(
					new OutputStreamWriter(sock.getOutputStream())), true);
			
			DataOutputStream fileOut = new DataOutputStream(
						new BufferedOutputStream(
									new FileOutputStream("./received.xls")
								)
					);
			long length = in.readLong();
			System.out.println("待接收文件长度为" + length);

			byte[] buf = new byte[2048];
			long lenRecv = 0;
			while (true) {
				int num = in.read(buf);
				if (num != -1) {
					fileOut.write(buf,0,num);
					lenRecv += num;
					System.out.println("文件已经接收"+lenRecv+"字节");
					if(lenRecv >=  length){
						fileOut.close();
						in.close();
						break;
					}
				}else{
					break;
				}
			}
			System.out.println("文件接收完了");
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}

}
