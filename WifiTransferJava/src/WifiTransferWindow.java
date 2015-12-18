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

import javax.swing.JProgressBar;


public class WifiTransferWindow extends JFrame {

	private JPanel contentPane;
	private JTextField textField;
	private JTextField textField_1;
	private JFrame frame;
	private static final int SERVER_PORT = 6789;
	private static final String TAG = "WifiTransfer";
	
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
				transferFile(new File(chooserFileToSend.getSelectedFile().getAbsolutePath()));
			}
		});
		btnTransFile.setBounds(45, 206, 93, 23);
		contentPane.add(btnTransFile);
		
		JProgressBar progressBar = new JProgressBar();
		progressBar.setBounds(171, 210, 146, 19);
		contentPane.add(progressBar);
	}
	
	
	public void transferFile(File fileToSend){
		try {
			InetAddress addr = InetAddress.getLocalHost();
			System.out.println("本机地址：" + addr.getHostAddress());
			System.out.println("本机名字: " + addr.getHostName());
			
			Socket sock = new Socket("127.0.0.1",SERVER_PORT);
			//Socket sock = new Socket("127.0.0.1",SERVER_PORT);
			DataOutputStream doutSock = new DataOutputStream(
					new BufferedOutputStream(
								sock.getOutputStream()
							)
					); 
			//File fileToSend = new File("D:\\15071705.xls");
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
	
}
