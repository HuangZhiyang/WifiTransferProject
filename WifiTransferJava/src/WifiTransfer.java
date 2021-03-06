import java.io.BufferedInputStream;
import java.io.BufferedOutputStream;
import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.ByteArrayInputStream;
import java.io.DataInputStream;
import java.io.DataOutputStream;
import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.OutputStream;
import java.io.OutputStreamWriter;
import java.io.PrintWriter;
import java.net.ConnectException;
import java.net.InetAddress;
import java.net.Socket;
import java.net.UnknownHostException;


public class WifiTransfer {
	
	private static final int SERVER_PORT = 6789;
	private static final String TAG = "WifiTransfer";
	private static WifiTransfer transfer;
	
	public static void main(String[]args){
		if(transfer == null)
		{
			transfer = new WifiTransfer();
		}
		transfer.sendFile("D:\\15071705.xls");
		
	}
	
	public void sendFile(String filePath){
		Socket sock =null;
		while (true) {
			try {
				
				InetAddress addr = InetAddress.getLocalHost();
				System.out.println("本机地址：" + addr.getHostAddress());
				System.out.println("本机名字: " + addr.getHostName());
				System.out.println("连接服务器:"+"127.0.0.1");
				sock = new Socket("127.0.0.1", SERVER_PORT);
				
				if(sock != null){ 
					System.out.println("连接服务器端成功");
					break;
				}
			} catch (ConnectException e) {
				System.out.println("连接失败，请确认服务器端是否已开启？");
			} catch (UnknownHostException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			} catch (IOException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
			try {
				Thread.sleep(2000);
			} catch (InterruptedException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
		}
		
		try {
			
			DataOutputStream doutSock = new DataOutputStream(
					new BufferedOutputStream(
								sock.getOutputStream()
							)
					); 
			//File fileToSend = new File("D:\\15071705.xls");
			File fileToSend = new File(filePath);
			DataInputStream dinFile = new DataInputStream(
						new BufferedInputStream(
									new FileInputStream(fileToSend)
								)
					);
			
		    BufferedReader  dinSock = new BufferedReader(
						new InputStreamReader(
									sock.getInputStream()
								)
					);
		    
			String name = fileToSend.getName();
			
			doutSock.writeInt(name.length());
			doutSock.writeChars(name);
			
			long length = fileToSend.length();
			doutSock.writeLong(length);
			
			byte []buf = new byte[2048];
			long lenTransfer = 0;
			while(true){
				int num = dinFile.read(buf);
				if(num != -1){
					doutSock.write(buf, 0, num);
					lenTransfer += num;
					System.out.println("文件一共传输了"+lenTransfer+"字节");
					doutSock.flush();
				}else{
					System.out.println(TAG+"haha,文件传送完了");
					break;
				}
			}
		} catch (IOException e) {
			// TODO Auto-generated catch block
			System.out.println("io exception ");
			e.printStackTrace();
		}
	}
}
