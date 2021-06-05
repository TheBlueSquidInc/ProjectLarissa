package ProjectLarissa;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.ItemEvent;
import java.awt.event.ItemListener;
import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;
import java.io.IOException;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import javax.swing.*;


import java.net.*;

public class Viewer extends JFrame{
	
	String testVar = null;
	String localStr1 ="";
	String localStr2 ="";
	byte[] inputTxtBytes = new byte[512];
	 
	String zeroTo255 = "(\\d{1,2}|(0|1)\\" + "d{2}|2[0-4]\\d|25[0-5])";
	String pat1Str = zeroTo255 + "\\." + zeroTo255 
            + "\\." + zeroTo255 
            + "\\." + zeroTo255;
	String pat2Str = "/0|([1-5][0-9]{0,4})|(6[1-4,0][0-9]{0,3})|(65[1-4,0][0-9]{0,2})|(655[1-2,0][0-9])|(6553[1-5,0])";

	Pattern pat1Obj = Pattern.compile(pat1Str);
	Pattern pat2Obj = Pattern.compile(pat2Str);
	Matcher mat1Obj;
	Matcher mat2Obj;
	
	JLabel hostNameLabel = new JLabel("Local Host Name: ");
	JLabel hostNameLabelData = new JLabel("PLACE HOLDER");
	JLabel localHostIpLabel = new JLabel("Local Host IP: ");
	JLabel localHostIpLabelData = new JLabel("PLACE HOLDER");
	JLabel localHostMacAddrLabel = new JLabel("Host MAC Address: ");
	JLabel localHostMacAddrLabelData = new JLabel("PLACE HOLDER");
	JLabel destIpLabel = new JLabel("Dest. IP: ");
	JLabel portNumLabel = new JLabel("Dest. Port: ");
	static JTextField destIpLabelData = new JTextField();
	static JTextField portNumLabelData = new JTextField();
	
	
	JLabel aotLabel = new JLabel("Always on top?");
	JCheckBox aotLabelData = new JCheckBox();

	
	JLabel transportLabel = new JLabel("Transport?");
	String[] transportStringArray = {"UDP"};
	JComboBox transportLabelDropDown = new JComboBox(transportStringArray);
	JLabel sndRecLabel = new JLabel("Send/Receive? ");
	String[] sndRecArray = {"Send","Receive"};
	JComboBox sendRecLabelDropDown = new JComboBox(sndRecArray);
	
	static JTextField inputTextField = new JTextField("Type some data here to send!");
	
	
	JButton enterButton = new JButton("Snd/Rcv!");
	static InetAddress inetObj = null;
	static DatagramSocket dgSkt = null;
	static DatagramPacket dgPkt;
	
	Viewer(){
		 
		
		
		//Position and add to JFrame
		hostNameLabel.setBounds(1,20,109,20);
		add(hostNameLabel);
		
		hostNameLabelData.setBounds(110,20,200,20);
		add(hostNameLabelData);
		
		localHostIpLabel.setBounds(1,40,85,20);
		add(localHostIpLabel);
		
		localHostIpLabelData.setBounds(90,40,95,20);
		add(localHostIpLabelData);
		
		localHostMacAddrLabel.setBounds(1, 60, 120, 20);
		add(localHostMacAddrLabel);
		
		localHostMacAddrLabelData.setBounds(120, 60, 105, 20);
		add(localHostMacAddrLabelData);
		
		aotLabel.setBounds(1, 80, 95, 20);
		add(aotLabel);
		
		aotLabelData.setBounds(95, 80, 20, 20);
		add(aotLabelData);
		
		aotLabelData.addItemListener(new ItemListener() {
			public void itemStateChanged(ItemEvent e) {
				if(e.getStateChange() == 1) {
					setAlwaysOnTop(true);
				} else if (e.getStateChange() == 2) {
					setAlwaysOnTop(false);
				}
			}
		});
		
		transportLabel.setBounds(125, 80, 65, 20);
		add(transportLabel);
		
		transportLabelDropDown.setBounds(200, 80, 65, 20);
		add(transportLabelDropDown);
		
		sndRecLabel.setBounds(275, 80, 95, 20);
		add(sndRecLabel);
		
		sendRecLabelDropDown.setBounds(370, 80, 95, 20);
		add(sendRecLabelDropDown);
		
		
		destIpLabel.setBounds(1, 100, 55, 20);
		add(destIpLabel);
		
		destIpLabelData.setBounds(65, 102, 110, 20);
		add(destIpLabelData);
		
		portNumLabel.setBounds(185, 100, 75, 20);
		add(portNumLabel);
		
		portNumLabelData.setBounds(255, 102, 50, 20);
		add(portNumLabelData);
		
		enterButton.setBounds(1, 125, 100, 20);
		add(enterButton);
		enterButton.addActionListener(new ActionListener() {
			
			public void actionPerformed(ActionEvent e) {
				

				localStr1 = destIpLabelData.getText();
				localStr2 = portNumLabelData.getText();
				mat1Obj = pat1Obj.matcher(localStr1);
				mat2Obj = pat2Obj.matcher(localStr2);


				 

				
				//System.out.println("Inet obj getHostAddress output: " + inetObj.getHostAddress());
				
				System.out.println("Matcher 1 value: " + mat1Obj.matches());
				System.out.println("Matcher 2 value: " + mat2Obj.matches());
				
				if((mat1Obj.matches()) && mat2Obj.matches() && ((transportLabelDropDown.getSelectedItem() == "UDP") && (sendRecLabelDropDown.getSelectedItem() == "Send"))){
						
					//
					try {
						sendPkt();
					} catch (UnknownHostException e1) {
						// TODO Auto-generated catch block
						e1.printStackTrace();
					} catch (SocketException e1) {
						// TODO Auto-generated catch block
						e1.printStackTrace();
					} catch (IOException e1) {
						// TODO Auto-generated catch block
						e1.printStackTrace();
					}
					
					
				} else if(mat2Obj.matches() && ((transportLabelDropDown.getSelectedItem() == "UDP") && (sendRecLabelDropDown.getSelectedItem() == "Receive"))) {
					try {
						receivePkt();
					} catch (SocketException e1) {
						// TODO Auto-generated catch block
						e1.printStackTrace();
					} catch (IOException e1) {
						// TODO Auto-generated catch block
						e1.printStackTrace();
					}
				} else {
					System.out.println("Check your inputs!");
				}
				
				
			}
		});
		
		inputTextField.setBounds(105, 125, 370, 20);
		add(inputTextField);
		inputTextField.addMouseListener(new MouseListener() {
			String str1 = "Type some data here to send!";
			public void mouseEntered(MouseEvent e) {
				
			}
			
			public void mousePressed(MouseEvent e) {
				
			}
			
			public void mouseClicked(MouseEvent e) {
				if(inputTextField.getText().contentEquals(str1)) {
					inputTextField.setText("");
				}
				
			}
			
			public void mouseReleased(MouseEvent e) {
				
			}
			
			public void mouseExited(MouseEvent e) {
				
			}
		});
		
		
		setSize(500,190);
		setTitle("Packet-O-Matic V0.1 by TheBlueSquid.com");
		setLayout(null);
		setVisible(true);
		setDefaultCloseOperation(EXIT_ON_CLOSE);
		show(true);
		
	}
	
	
	public static void setJLabel(JLabel jL, String jtValue) {
		jL.setText(jtValue);
	}
	
	
	public static void sendUDP(String destIp, String portNum, String msg) {
		System.out.println("Running sendUDP()");
		
	}
	
	
	public static void sendPkt() throws UnknownHostException, SocketException, IOException {
		inetObj = InetAddress.getByName(destIpLabelData.getText().toString());
		byte[] tempbyte = new byte[65000];
		String tempStr = inputTextField.getText().toString();
		int tempPort = Integer.parseInt(portNumLabelData.getText());
		tempbyte = tempStr.getBytes();
		dgSkt = new DatagramSocket(Integer.parseInt(portNumLabelData.getText().toString()));
		dgPkt = new DatagramPacket(tempbyte, tempbyte.length, inetObj, tempPort);
		dgSkt.send(dgPkt);
		dgSkt.close();
	}
	
	public static void receivePkt() throws SocketException, IOException{
		byte[] tempbyte = new byte[65000];
		String textStr = "";
		int tempPort = Integer.parseInt(portNumLabelData.getText());
		dgSkt = new DatagramSocket(Integer.parseInt(portNumLabelData.getText().toString()));
		dgPkt = new DatagramPacket(tempbyte, tempbyte.length);
		dgSkt.setSoTimeout(10000);
		dgSkt.receive(dgPkt);
		textStr = new String(dgPkt.getData(), 0, tempbyte.length);
		inputTextField.setText(textStr.toString());
		dgSkt.close();
	}
	
}
