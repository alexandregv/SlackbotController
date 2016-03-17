package fr.triinoxys.slackbotcontroller;

import java.awt.EventQueue;
import java.awt.Toolkit;
import java.awt.Window.Type;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.BufferedReader;
import java.io.DataOutputStream;
import java.io.InputStreamReader;
import java.net.URL;
import javax.net.ssl.HttpsURLConnection;
import javax.swing.DefaultComboBoxModel;
import javax.swing.JButton;
import javax.swing.JComboBox;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JTextField;

/**
 * 
 * @author TriiNoxYs
 *
 */

public class GUI{

	private JFrame form;
	private JTextField txtChannel;
	private JTextField txtUsername;
	private JTextField txtMessage;
	private JTextField txtIcon;
	private JComboBox<?> comboIcontype;
	
	static GUI window;

	/**
	 * Launch the application.
	 */
	public static void main(String[] args){
		EventQueue.invokeLater(new Runnable(){

			public void run(){
				try{
					window = new GUI();
					window.form.setVisible(true);
				}catch(Exception e){
					e.printStackTrace();
				}
			}
		});
	}

	/**
	 * Create the application.
	 */
	public GUI(){
		initialize();
	}

	/**
	 * Initialize the contents of the frame.
	 */
	@SuppressWarnings({"unchecked", "rawtypes"})
	private void initialize(){
		form = new JFrame();
		form.setType(Type.POPUP);
		form.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		form.setIconImage(Toolkit.getDefaultToolkit().getImage(GUI.class.getResource("/fr/triinoxys/slackbotcontroller/Slack.png")));
		form.setResizable(false);
		form.setTitle("Slackbot Controller - by TriiNoxYs");                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                      form.setTitle("Slackbot Controller - by TriiNoxYs");
		form.setBounds(100, 100, 653, 232);
		form.getContentPane().setLayout(null);
		
		JLabel lblChannel = new JLabel("Username:");
		lblChannel.setBounds(10, 43, 64, 21);
		form.getContentPane().add(lblChannel);
		
		JLabel lblChannel_1 = new JLabel("Channel:");
		lblChannel_1.setBounds(10, 11, 64, 21);
		form.getContentPane().add(lblChannel_1);
		
		JLabel lblMessage = new JLabel("Message:");
		lblMessage.setBounds(10, 75, 64, 21);
		form.getContentPane().add(lblMessage);
		
		txtChannel = new JTextField();
		txtChannel.setText("#discussion");
		txtChannel.setBounds(75, 12, 552, 20);
		form.getContentPane().add(txtChannel);
		txtChannel.setColumns(10);
		
		txtUsername = new JTextField();
		txtUsername.setText("JavaBOT");
		txtUsername.setBounds(75, 44, 552, 20);
		form.getContentPane().add(txtUsername);
		txtUsername.setColumns(10);
		
		txtMessage = new JTextField();
		txtMessage.setText("Hello World !");
		txtMessage.setBounds(75, 76, 552, 20);
		form.getContentPane().add(txtMessage);
		txtMessage.setColumns(10);
		
		comboIcontype = new JComboBox();
		comboIcontype.setModel(new DefaultComboBoxModel(new String[]{"Icon Emoji", "Icon URL"}));
		comboIcontype.setBounds(10, 116, 101, 20);
		form.getContentPane().add(comboIcontype);
		
		txtIcon = new JTextField();
		txtIcon.setText(":st-pasteak:");
		txtIcon.setColumns(10);
		txtIcon.setBounds(121, 116, 506, 20);
		form.getContentPane().add(txtIcon);
		
		JButton btnNewButton = new JButton("Send message");
		btnNewButton.addActionListener(new ActionListener(){
			public void actionPerformed(ActionEvent arg0){
				try{
					window.sendPost();
				}catch(Exception e){
					e.printStackTrace();
				}
			}
		});
		btnNewButton.setBounds(10, 147, 617, 42);
		form.getContentPane().add(btnNewButton);
	}
	
	// -----HTTP POST request-----
	private void sendPost() throws Exception{
		
		String url = "https://hooks.slack.com/services/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";
		URL obj = new URL(url);
		HttpsURLConnection con = (HttpsURLConnection) obj.openConnection();
		String channel, username, text, icon;
		int iconType;
		
		// Add reuqest header
		con.setRequestMethod("POST");
		
		channel = txtChannel.getText();
		username = txtUsername.getText();
		text = txtMessage.getText();
		iconType = comboIcontype.getSelectedIndex();
		icon = txtIcon.getText();
		
		String urlParameters = null;
		
		if(iconType==0){
			urlParameters = "payload={\"channel\": \"" +
					channel + "\", \"username\": \"" + 
					username + "\", \"text\": \"" + 
					text + "\", \"icon_emoji\": \"" + 
					icon + "\"}" ;
		}	
		else if(iconType==1){
			urlParameters = "payload={\"channel\": \"" +
					channel + "\", \"username\": \"" + 
					username + "\", \"text\": \"" + 
					text + "\", \"icon_url\": \"" + 
					icon + "\"}" ;
		}
		
		// Send POST request
		con.setDoOutput(true);
		DataOutputStream wr = new DataOutputStream(con.getOutputStream());
		wr.writeBytes(urlParameters);
		wr.flush();
		wr.close();
		
		BufferedReader in = new BufferedReader(new InputStreamReader(con.getInputStream()));
		String inputLine;
		StringBuffer response = new StringBuffer();
		
		while ((inputLine = in.readLine()) != null){
			response.append(inputLine);
		}
		in.close();
	}
}
