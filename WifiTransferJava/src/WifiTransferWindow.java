import java.awt.BorderLayout;
import java.awt.EventQueue;

import javax.swing.JFileChooser;
import javax.swing.JFrame;
import javax.swing.JPanel;
import javax.swing.border.EmptyBorder;
import javax.swing.filechooser.FileNameExtensionFilter;
import javax.swing.JButton;
import javax.swing.JTextField;
import java.awt.event.ActionListener;
import java.awt.event.ActionEvent;
import java.io.BufferedInputStream;
import java.io.BufferedOutputStream;
import java.io.BufferedReader;
import java.io.DataInputStream;
import java.io.DataOutputStream;
import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.io.InputStreamReader;
import java.net.ConnectException;
import java.net.InetAddress;
import java.net.Socket;
import java.net.UnknownHostException;
import java.util.Timer;
import java.util.TimerTask;


import javax.swing.JProgressBar;
import javax.swing.JLabel;
import javax.swing.SwingConstants;
import java.awt.Color;


public class WifiTransferWindow extends JFrame {

	private JPanel contentPane;
	private JTextField textField;
	private JTextField textField_1;
	private JFrame frame;
	private static final int SERVER_PORT = 6789;
	private static final String TAG = "WifiTransfer";
	private JProgressBar progressBarFileSend; 
	FileSendProgress  fileSendProgress = null;
	private int sendPercent=50;
	/**
	 * Launch the application.
	 */
	public static void main(String[] args) {
		EventQueue.invokeLater(new Runnable() {
			public void run() {
				try {
					WifiTransferWindow frame = new WifiTransferWindow();
					frame.setVisible(true);
				} catch (Exception e) {
					e.printStackTrace();
				}
			}
		});
	}

	/**
	 * Create the frame.
	 */
	public WifiTransferWindow() {
		setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		setBounds(100, 100, 450, 344);
		contentPane = new JPanel();
		contentPane.setBorder(new EmptyBorder(5, 5, 5, 5));
		setContentPane(contentPane);
		contentPane.setLayout(null);
		
		JButton btnSendMsg = new JButton("\u53D1\u9001\u4FE1\u606F");
		btnSendMsg.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent arg0) {
			}
		});
		btnSendMsg.setBounds(45, 162, 93, 23);
		contentPane.add(btnSendMsg);
		
		textField = new JTextField();
		textField.setBounds(35, 10, 116, 129);
		contentPane.add(textField);
		textField.setColumns(10);
		
		JButton btnRecvMsg = new JButton("\u63A5\u6536\u4FE1\u606F");
		btnRecvMsg.setBounds(205, 162, 93, 23);
		contentPane.add(btnRecvMsg);
		
		textField_1 = new JTextField();
		textField_1.setBounds(187, 10, 111, 129);
		contentPane.add(textField_1);
		textField_1.setColumns(10);
		
		progressBarFileSend = new JProgressBar();
		progressBarFileSend.setForeground(Color.MAGENTA);
		progressBarFileSend.setBounds(225, 210, 146, 19);
		contentPane.add(progressBarFileSend);
		
		JButton btnTransFile = new JButton("\u53D1\u9001\u6587\u4EF6");
		btnTransFile.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				
				JFileChooser chooserFileToSend = new JFileChooser();
				/*
				FileNameExtensionFilter filter = new FileNameExtensionFilter("JPG & PNG File",
																"jpg","png","JPEG","PNG","JPG"
						);
				chooserStylePhoto.setFileFilter(filter);
				 */
				int returnVal = chooserFileToSend.showOpenDialog(frame);
				    if(returnVal == JFileChooser.APPROVE_OPTION) {
				       System.out.println("You chose to open this file: " +
				    		   chooserFileToSend.getSelectedFile().getAbsolutePath());
				       		   //+chooserStylePhoto.getSelectedFile().getName());
				    }
					sendFile(chooserFileToSend.getSelectedFile().getAbsolutePath());
			}
		});
		btnTransFile.setBounds(45, 210, 93, 23);
		contentPane.add(btnTransFile);
		
		JLabel label = new JLabel("\u53D1\u9001\u8FDB\u5EA6\uFF1A");
		label.setBounds(148, 214, 67, 15);
		contentPane.add(label);
		

	}
	
	
	public void sendFile(String  filePath){
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
			
			sendPercent = 0;
			fileSendProgress = new FileSendProgress();
			fileSendProgress.start();
			while(true){
				int num = dinFile.read(buf);
				if(num != -1){
					doutSock.write(buf, 0, num);
					lenTransfer += num;
					//System.out.println("文件一共传输了"+lenTransfer+"字节");
					doutSock.flush();
					if(lenTransfer == length){
						System.out.println(TAG+"haha,文件传送完了");
						break;
					}
					sendPercent = (int)( ((lenTransfer*1.0)/length) * 100);
				}else{
					break;
				}
			}
			sendPercent = 100;
			progressBarFileSend.setValue(100);
		} catch (UnknownHostException e) {
			// TODO Auto-generated catch block
			System.out.println("UnkownHostException");
			e.printStackTrace();
		}catch(ConnectException e){
			System.out.println("连接服务器端失败，请先开启服务器端");
		} catch (IOException e) {
			// TODO Auto-generated catch block
			System.out.println("io exception ");
			e.printStackTrace();
		} 
	}
	
	class FileSendProgress extends Thread{
		
		@Override
		public void run() {
			// TODO Auto-generated method stub
			while (sendPercent < 100) {
				try {
					System.out.println("文件已发送"+sendPercent+"%");
					progressBarFileSend.setValue(sendPercent);
					Thread.sleep(1000);
				} catch (InterruptedException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				}
			}
		}
	}
}
