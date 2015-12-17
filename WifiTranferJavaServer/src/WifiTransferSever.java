import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.OutputStreamWriter;
import java.io.PrintWriter;
import java.net.InetAddress;
import java.net.ServerSocket;
import java.net.Socket;



public class WifiTransferSever {

	private static final int RECV_PORT = 6789; 
	private static final String TAG = "WifiTransferServer";
	
	public static void main(String []args){
		
		ServerSocket ssocket;
		try {
			ssocket = new ServerSocket(RECV_PORT);
			InetAddress addr = InetAddress.getLocalHost();
			System.out.println(addr.getHostAddress());
			System.out.println(addr.getHostName());
			
			System.out.println(TAG+"huanzhiyang");
			Socket sock = ssocket.accept();
			System.out.println("Accept an connection");
			BufferedReader in  = new BufferedReader(
					 new InputStreamReader(
							sock.getInputStream()
							)
					 );
			PrintWriter out = new PrintWriter(
								 new BufferedWriter(
								    new OutputStreamWriter(sock.getOutputStream())
								    ),true
					);

			while(true){
				String str = in.readLine();
				System.out.println(TAG+str);
				out.println("haha, i have received:"+str);
				if(str.equals("END")){
					sock.close();
					break;
				}
			}
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}
	
}
