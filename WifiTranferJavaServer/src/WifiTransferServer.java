import java.io.*;
import java.net.InetAddress;
import java.net.ServerSocket;
import java.net.Socket;

public class WifiTransferServer {

	private static final int RECV_PORT = 6789;
	private static final String TAG = "WifiTransferServer";
	private static WifiTransferServer transferServer;
	
	public static void main(String[] args) {
		
		if(transferServer == null){
			transferServer = new WifiTransferServer();
		}
		transferServer.Recv();
		
	}
	public void Recv(){
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
			
		
			int  fileNameLen = in.readInt();
			String fileName ="";
			for(int i=0;i<fileNameLen;i++){
				fileName = fileName + in.readChar();
			}
			System.out.println("�������ļ�����Ϊ" + fileName);
			long length = in.readLong();
			System.out.println("�������ļ�����Ϊ" + length);
			
			DataOutputStream fileOut = new DataOutputStream(
					new BufferedOutputStream(
								new FileOutputStream(
										new File(fileName))
							)
				);
			
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