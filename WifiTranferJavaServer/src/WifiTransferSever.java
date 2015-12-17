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
			System.out.println("�������ļ�����Ϊ" + length);

			byte[] buf = new byte[2048];
			long lenRecv = 0;
			while (true) {
				int num = in.read(buf);
				if (num != -1) {
					fileOut.write(buf,0,num);
					lenRecv += num;
					System.out.println("�ļ��Ѿ�����"+lenRecv+"�ֽ�");
					if(lenRecv >=  length){
						fileOut.close();
						in.close();
						break;
					}
				}else{
					break;
				}
			}
			System.out.println("�ļ���������");
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}

}
