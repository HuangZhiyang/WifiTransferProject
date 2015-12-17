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
import javax.swing.JProgressBar;


public class WifiTransferWindow extends JFrame {

	private JPanel contentPane;
	private JTextField textField;
	private JTextField textField_1;
	private JFrame frame;
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
				
				JFileChooser chooserStylePhoto = new JFileChooser();
				/*
				FileNameExtensionFilter filter = new FileNameExtensionFilter("JPG & PNG File",
																"jpg","png","JPEG","PNG","JPG"
						);
				chooserStylePhoto.setFileFilter(filter);
				 */
				int returnVal = chooserStylePhoto.showOpenDialog(frame);
				    if(returnVal == JFileChooser.APPROVE_OPTION) {
				       System.out.println("You chose to open this file: " +
				    		   chooserStylePhoto.getSelectedFile().getAbsolutePath());
				       		   //+chooserStylePhoto.getSelectedFile().getName());
				    }
			}
		});
		btnTransFile.setBounds(45, 206, 93, 23);
		contentPane.add(btnTransFile);
		
		JProgressBar progressBar = new JProgressBar();
		progressBar.setBounds(171, 210, 146, 19);
		contentPane.add(progressBar);
	}
}
