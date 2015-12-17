package com.huang.wifitransfer;

import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.OutputStreamWriter;
import java.io.PrintWriter;
import java.net.InetAddress;
import java.net.ServerSocket;
import java.net.Socket;

import android.os.Bundle;
import android.app.Activity;
import android.util.Log;
import android.view.Menu;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.Button;

public class MainActivity extends Activity implements OnClickListener{

	private static final int RECV_PORT = 6789; 
	private static final String TAG = "WifiTransfer";
	
	private Button btnRecv 			=null;
	private Button btnTrans	  		=null;
	private RecvThread recvThread 	=null;
	private TransThread transThread = null;
	
	

	
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		Log.d(TAG,"start Wifi Transfer apk huang1");
		setContentView(R.layout.activity_main);
		Log.d(TAG,"start Wifi Transfer apk huang");
		findViews();
		registerListeners();
		
	}
	
	private void findViews(){
		
		btnRecv  = (Button) findViewById(R.id.button_recv);
		btnTrans = (Button) findViewById(R.id.button_trans);
		
	}
	
	private void registerListeners() {
		
		btnRecv.setOnClickListener(this);
		btnTrans.setOnClickListener(this);	
	}

	@Override
	public boolean onCreateOptionsMenu(Menu menu) {
		// Inflate the menu; this adds items to the action bar if it is present.
		getMenuInflater().inflate(R.menu.main, menu);
		return true;
	}
	
	@Override
	public void onClick(View v) {
		// TODO Auto-generated method stub
		Log.d(TAG,"on click"+v.getId());
		switch(v.getId()){
			case R.id.button_recv:
					if(recvThread == null){
						recvThread = new RecvThread();
						recvThread.start();
					}
				break;
			case R.id.button_trans:
					if(transThread == null ){
						transThread = new TransThread();
					}
				break;
		}
	}
	
	
	class RecvThread extends Thread{
		
		
		@Override
		public void run() {
			// TODO Auto-generated method stub
			
			try {
				
				InetAddress addr = InetAddress.getLocalHost();
				
				Log.d(TAG,"begin to recieve");
				ServerSocket ssocket = new ServerSocket(RECV_PORT);
				Socket sock = ssocket.accept();
				Log.d(TAG,"accept an socket");
				BufferedReader in  = new BufferedReader(
									 new InputStreamReader(
											sock.getInputStream())
						);
				
				PrintWriter out = new PrintWriter(
							new BufferedWriter(
									new OutputStreamWriter(sock.getOutputStream()))
							,true
						);

				while(true){
					String str = in.readLine();
					Log.d(TAG,"received"+str);
					out.println("i'm android server,i have received"+str);
					
					if(str.equals("END")){
						sock.close();
						break;
					}
				}
			} catch (IOException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
			super.run();
		}	
	}
	
	class TransThread extends Thread{
		
		
		
	}
}
