import java.io.BufferedInputStream;
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
import java.net.InetAddress;
import java.net.Socket;
import java.net.UnknownHostException;


public class WifiTransfer {
	
	private static final int SERVER_PORT = 6789;
	
	public static void main(String[]args){

		try {
			InetAddress addr = InetAddress.getLocalHost();
			System.out.println(addr.getHostAddress());
			System.out.println(addr.getHostName());
			
			Socket sock = new Socket("127.0.0.1",SERVER_PORT);
			//Socket sock = new Socket("127.0.0.1",SERVER_PORT);
			BufferedReader in = new BufferedReader(
					new InputStreamReader(sock.getInputStream())
					);
			
			PrintWriter out = new PrintWriter(
					new BufferedWriter(
							new OutputStreamWriter(sock.getOutputStream()
							)),true
					);
			
			DataInputStream din = new DataInputStream(
									new BufferedInputStream(
										new FileInputStream("D:\15070715.xls")
							)
					);
			
			DataInputStream dout = new DataOutputStream(
					new BfferedOutputStream(
						new FileInputStream("D:\15070715.xls")
							)
			);
			while(din.readByte()!= -1){
				
				
			}
			for(int i=0;i< 10;i++){
				//System.out.println("please input:");
				out.println("hello " +i);
				String str= in.readLine();
				System.out.println(str);
			}
			out.println("END");
			
		} catch (UnknownHostException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}
}
