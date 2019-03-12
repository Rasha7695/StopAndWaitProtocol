package Main;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.net.DatagramPacket;
import java.net.DatagramSocket;
import java.net.InetAddress;
import java.net.SocketException;
import java.net.UnknownHostException;
import java.util.ArrayList;
import java.util.Arrays;
import java.nio.ByteBuffer;
import java.util.HashMap;

import javax.swing.*;

public class Receiver  extends JFrame implements ActionListener
{ JLabel lblName;
JLabel lblSenderPort;
JLabel lblReceiverPort;
JLabel lblFile;
JLabel lblNum;
JTextField txtName;
JTextField txtSenderPort;
JTextField txtReceiverPort;
JTextField txtFile;
JButton btnProcess;
JTextField txtS;
	public Receiver(){
		  this.setTitle("Receiver GUI");
	    this.setSize(500,500);
	    this.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
	    getContentPane().setLayout(null);

	    lblName = new JLabel("IP Address: ");
	    lblName.setBounds(10, 10, 90, 21);
	    add(lblName);

	    txtName = new JTextField();
	    txtName.setBounds(105, 10, 90, 21);
	    add(txtName);

	    lblSenderPort = new JLabel("Sender Port: ");
	    lblSenderPort.setBounds(10, 35, 90, 21);
	    add(lblSenderPort);
	    lblReceiverPort = new JLabel("Receiver Port: ");
	    lblReceiverPort.setBounds(10,60,90,21);
	    add(lblReceiverPort);
	    lblFile = new JLabel("File: ");
	    lblFile.setBounds(10, 90, 90, 21);
	    add(lblFile);
	 

	    txtSenderPort = new JTextField();
	    txtSenderPort.setBounds(105, 35, 90, 21);
	    add(txtSenderPort);
	    txtReceiverPort = new JTextField();
	    txtReceiverPort.setBounds(105, 60, 90, 21);
	    add(txtReceiverPort);
	    txtFile = new JTextField();
	    txtFile.setBounds(60, 90, 120, 21);
	    add(txtFile);
	
	    
	    btnProcess = new JButton("TRANSFER");
	    btnProcess.setBounds(200, 90, 100, 21);
	    btnProcess.addActionListener((ActionListener) this);
	    add(btnProcess);
        lblNum = new JLabel("current number of received in-order packet:");
        lblNum.setBounds(10,120,250,21);
        add(lblNum);
	    txtS = new JTextField();
	   txtS.setBounds(10, 150, 120, 21);
	   add(txtS);

	    this.setVisible(true);
	}
	
	
	
	static byte[] trim(byte[] bytes)
{
    int i = bytes.length - 1;
    while (i >= 0 && bytes[i] == 0)
    {
        --i;
    }

    return Arrays.copyOf(bytes, i + 1);
}
public void actionPerformed(ActionEvent e) {
		 if (e.getSource().equals(btnProcess)) {
	        try {
	            processInformation();
	        } catch (UnknownHostException e1) {
	            e1.printStackTrace();
	        } catch (IOException e1) {
	            e1.printStackTrace();
	        }
	    }
		 if (e.getSource().equals(reliable)) {
			 if (reliable.isSelected()) {//reliable option 
				 reliable.setText("Unreliable");
				 }
			 else {
				 //unreliable option
				 reliable.setText("Reliable");
			 }
			 }
		 
		 
}

	public void processInformation() throws UnknownHostException, IOException{
		// InetAddress ip = InetAddress.getByName(txtName.getText());
	    // int Senderport = Integer.parseInt(txtSenderPort.getText());
	    // int Receiverport = Integer.parseInt(txtReceiverPort.getText());
	    // File file = new File(txtFile.getText());
	    
	    // DatagramSocket ds = new DatagramSocket(Receiverport);
		// byte[] receive = new byte[65535];
		// byte[] handbyte = new byte[1]; 

		// DatagramPacket DpReceive = null; 
		// DatagramPacket AckPacket = null;
		// FileOutputStream fileOuputStream = new FileOutputStream(file,true);
		// DpReceive = new DatagramPacket(handbyte, 1);
		// ds.receive(DpReceive);
		// ds.send(DpReceive);
		// handbyte = new byte[65535];
		// HashMap<Integer, Integer> dupPackets = new HashMap<Integer, Integer>();
		// int packet_num = 0;
		// while (true) 
		// {
		// 	// Step 2 : create a DatgramPacket to receive the data. 
		// 	DpReceive = new DatagramPacket(receive, receive.length);

		// 	// Step 3 : revieve the data in byte buffer. 
		// 	ds.receive(DpReceive);
		// 	InetAddress IPAddress = DpReceive.getAddress();
		// 	int port = DpReceive.getPort();
		// 	if (data(receive).toString().equals("EOT")) 
		// 	{ 
		// 		AckPacket = new DatagramPacket("EOT".getBytes(),3, IPAddress,port);
		// 		ds.send(AckPacket);
		// 		System.out.println("Client sent bye.....EXITING"); 
		// 		break; 
		// 	}
		// 	packet_num++;
		// 	System.out.println(data(receive));
		// 	byte sequenceNumber[] = Arrays.copyOfRange(receive, 0, 4);
		// 	System.out.println("Sequence Number:-" + ByteBuffer.wrap(sequenceNumber).getInt());
			
		// 	AckPacket = new DatagramPacket(sequenceNumber, sequenceNumber.length, IPAddress,port);
		// 	byte data[] = Arrays.copyOfRange(receive, 4, receive.length);
		// 	System.out.println("Packet Num: "+packet_num);

		// 	try {
		// 		if (packet_num%3==0){			 
		// 			continue;
		// 		}
		// 		else {
		// 		if(dupPackets.containsValue(ByteBuffer.wrap(sequenceNumber).getInt())){
		// 			ds.send(AckPacket);
		// 		}
				
		// 		else{
		// 			dupPackets.put(ByteBuffer.wrap(sequenceNumber).getInt(),1);
		// 			fileOuputStream.write(trim(data));
		// 			ds.send(AckPacket);
		// 		}
		// 		}
		// 	} catch (IOException e) {
		// 		e.printStackTrace();
		// 	}
	
			

		// 	System.out.println("Client:-" + data(data)); 

		// 	// Exit the server if the client sends "bye" 
			
		// 	// Clear the buffer after every message. 
		// 	receive = new byte[65535]; 
		// } 
	}
	
	
	
	public static void main(String[] args) throws IOException 
	{ 
	  //new Receiver();
	  	// InetAddress ip = InetAddress.getByName(txtName.getText());
	    // int Senderport = Integer.parseInt(txtSenderPort.getText());
	    // int Receiverport = Integer.parseInt(txtReceiverPort.getText());
	    // File file = new File(txtFile.getText());
	    
	    DatagramSocket ds = new DatagramSocket(1234);
		byte[] receive = new byte[65535];
		byte[] handbyte = new byte[1]; 

		DatagramPacket DpReceive = null; 
		DatagramPacket AckPacket = null;
		FileOutputStream fileOuputStream = new FileOutputStream("Main\\outt",true);
		DpReceive = new DatagramPacket(handbyte, 1);
		ds.receive(DpReceive);
		ds.send(DpReceive);
		handbyte = new byte[65535];
		HashMap<Integer, Integer> dupPackets = new HashMap<Integer, Integer>();
		int packet_num = 0;
		while (true) 
		{
			// Step 2 : create a DatgramPacket to receive the data. 
			DpReceive = new DatagramPacket(receive, receive.length);
			// Step 3 : revieve the data in byte buffer.
			ds.receive(DpReceive);
			InetAddress IPAddress = DpReceive.getAddress();
			int port = DpReceive.getPort();
			if (data(receive).toString().equals("EOT")) 
			{ 
				AckPacket = new DatagramPacket("EOT".getBytes(),3, IPAddress,port);
				ds.send(AckPacket);
				System.out.println("Client sent bye.....EXITING"); 
				packet_num =0;
				dupPackets.clear();
				receive = new byte[65535]; 
				continue; 
			}
			packet_num++;
			System.out.println(data(receive));
			byte sequenceNumber[] = Arrays.copyOfRange(receive, 0, 4);
			System.out.println("Sequence Number:-" + ByteBuffer.wrap(sequenceNumber).getInt());
			
			AckPacket = new DatagramPacket(sequenceNumber, sequenceNumber.length, IPAddress,port);
			byte data[] = Arrays.copyOfRange(receive, 4, receive.length);
			System.out.println("Packet Num: "+packet_num);

			try {
				if (packet_num%3==0){			 
					continue;
				}
				else {
				if(dupPackets.containsValue(ByteBuffer.wrap(sequenceNumber).getInt())){
					ds.send(AckPacket);
				}
				
				else{
					dupPackets.put(ByteBuffer.wrap(sequenceNumber).getInt(),1);
					fileOuputStream.write(trim(data));
					ds.send(AckPacket);
				}
				}
			} catch (IOException e) {
				e.printStackTrace();
			}
	
			

			System.out.println("Client:-" + data(data)); 

			// Exit the server if the client sends "bye" 
			
			// Clear the buffer after every message. 
			receive = new byte[65535]; 
		}
	} 

	// A utility method to convert the byte array 
	// data into a string representation. 
	public static StringBuilder data(byte[] a) 
	{ 
		if (a == null) 
			return null; 
		StringBuilder ret = new StringBuilder(); 
		int i = 0; 
		while (a[i] != 0) 
		{ 
			ret.append((char) a[i]); 
			i++; 
		} 
		return ret; 
	}
	
} 
