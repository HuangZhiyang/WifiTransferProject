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
		
		ServerSocket ssocket = null;
		Socket sock;
		
		try {
			InetAddress addr = InetAddress.getLocalHost();
			System.out.println("本服务器地址:"+ addr.getHostAddress());
			System.out.println("本服务器名:"  + addr.getHostName());
			ssocket = new ServerSocket(RECV_PORT);
		} catch (IOException e1) {
			// TODO Auto-generated catch block
			e1.printStackTrace();
		}
		
		while(true)
		{
			try {
				sock = ssocket.accept();
				
				if(sock != null ){
					System.out.println("接收到一个连接，对方地址为:"
									+sock.getInetAddress().getHostAddress());
					
					RecvThread recvThread = new RecvThread(sock);
					recvThread.start();
					
				}
			} catch (IOException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
		}
	}
	
	private class RecvThread extends Thread{
		Socket sock;
		RecvThread(Socket sock){
			this.sock = sock;
		}
		@Override
		public void run() {
			// TODO Auto-generated method stub
			super.run();
			try {
				DataInputStream in = new DataInputStream(new BufferedInputStream(
						this.sock.getInputStream()));
				
				int fileNameLen = in.readInt();
				String fileName = "";
				for (int i = 0; i < fileNameLen; i++) {
					fileName = fileName + in.readChar();
				}
				System.out.println("待接收文件名字为" + fileName);
				
				long length = in.readLong();
				System.out.println("待接收文件长度为" + length);

				DataOutputStream fileOut = new DataOutputStream(
						new BufferedOutputStream(new FileOutputStream(new File(
								fileName))));

				byte[] buf = new byte[2048];
				long lenRecv = 0;
				System.out.println("开始接收文件");
				while (true) {
					int num = in.read(buf);
					if (num != -1) {
						fileOut.write(buf, 0, num);
						lenRecv += num;
						//System.out.println("文件已经接收" + lenRecv + "字节");
						if (lenRecv >= length) {
							fileOut.close();
							in.close();
							break;
						}
					} else {
						break;
					}
				}
				System.out.println("文件接收完了，共接收"+lenRecv+"字节");
			} catch (IOException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
		}
	};
}
