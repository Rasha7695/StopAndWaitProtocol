package Main;
import java.io.IOException;
import java.io.InputStreamReader;
import java.net.DatagramPacket;
import java.net.DatagramSocket;
import java.net.InetAddress;
import java.net.Socket;
import java.net.SocketTimeoutException;
import java.net.UnknownHostException;
import java.util.Scanner;

import javax.swing.*;

import java.nio.ByteBuffer;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.BufferedReader;
import java.io.ByteArrayOutputStream;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;

public class Sender extends JFrame implements ActionListener
{ JLabel lblName;
JLabel lblSenderPort;
JLabel lblReceiverPort;
JLabel lblFile;
JLabel lblMax;
JLabel lblTime;
JTextField txtTime;
JTextField txtName;
JTextField txtSenderPort;
JTextField txtReceiverPort;
JTextField txtFile;
JTextField txtMax;
JButton btnProcess;
JTextArea txtS;
	
public Sender(){
	  this.setTitle("Sender GUI");
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
    lblMax = new JLabel("MDS:");
    lblMax.setBounds(10,120,90,21);
    add(lblMax);
    lblTime = new JLabel ("Timeout:");
    lblTime.setBounds(130,120,90,21);
    add(lblTime);
    txtTime = new JTextField();
    txtTime.setBounds(200,120,60,21);
    add(txtTime);
    txtSenderPort = new JTextField();
    txtSenderPort.setBounds(105, 35, 90, 21);
    add(txtSenderPort);
    txtReceiverPort = new JTextField();
    txtReceiverPort.setBounds(105, 60, 90, 21);
    add(txtReceiverPort);
    txtFile = new JTextField();
    txtFile.setBounds(60, 90, 120, 21);
    add(txtFile);
    txtMax = new JTextField();
    txtMax.setBounds(60,120,60,21);
    add(txtMax);

    
    btnProcess = new JButton("TRANSFER");
    btnProcess.setBounds(200, 90, 100, 21);
    btnProcess.addActionListener((ActionListener) this);
    add(btnProcess);

    txtS = new JTextArea();
   txtS.setBounds(10, 200, 290, 120);
   add(txtS);

    this.setVisible(true);
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
    } }

public void processInformation() throws UnknownHostException, IOException {

	InetAddress ip = InetAddress.getByName(txtName.getText());
    int Senderport = Integer.parseInt(txtSenderPort.getText());
    int Receiverport = Integer.parseInt(txtReceiverPort.getText());
	File file = new File(txtFile.getText());
	int MDS = Integer.parseInt(txtMax.getText());
	int timeout = Integer.parseInt(txtTime.getText());
	FileInputStream fin = null;
	DatagramSocket ds = new DatagramSocket(); 
	int sequenceNumber = 0;
	int bytesRead = 0;	 //placeholder for timeout in ms

    try{
    	fin = new FileInputStream(file);
	double n = (int)file.length();
	double cycles = Math.ceil(n/MDS);
	byte header[] = new byte[] {1};
	DatagramPacket handshakePacket = new DatagramPacket(header, 1, ip, Receiverport);
	DatagramPacket handbyte = new DatagramPacket(header, 1);
	ds.send(handshakePacket);
	ds.receive(handbyte);
	while(!handbyte.getData().equals(header)){
		ds.send(handshakePacket);
		ds.receive(handbyte);
	}

	for(int i=0;i<cycles;i++){
		sequenceNumber+=1;
		header = new byte[4];
		header = ByteBuffer.allocate(4).putInt(sequenceNumber).array();
		System.out.println("Header size: "+header.length);
		byte fileContent[] = new byte[MDS];
		bytesRead+=i;

	fin.read(fileContent);

	String s = new String(fileContent);
	System.out.println("File content: " + s);

// Step 2 : Create the datagramPacket for sending 
// the data. 
ByteArrayOutputStream outputStream = new ByteArrayOutputStream( );
outputStream.write( header );
outputStream.write( fileContent );
byte sndBuf[] = outputStream.toByteArray( );
DatagramPacket DpSend = 
	new DatagramPacket(sndBuf, sndBuf.length, ip, Receiverport); 



// Step 3 : invoke the send call to actually send 
// the data. 
ds.send(DpSend); 


 boolean response = false;
while (response ==false) {
	byte seqNum[] = new byte[4];
	DatagramPacket ack = new DatagramPacket(seqNum, 4);

	try {
		ds.receive(ack);


	if (ByteBuffer.wrap(header).getInt()!=ByteBuffer.wrap(seqNum).getInt()) {
			response = true;
	}
		//if it was lost or not valid, we need to resend packet
		else {
			ds.send(DpSend);
		ds.setSoTimeout(timeout);
 		}
 	}
	catch(SocketTimeoutException e ) {
		ds.send(DpSend);
 		ds.setSoTimeout(timeout);
 	}
 }
  
	}
 }
catch (FileNotFoundException e) {
	txtS.setText("File Not Found");
}
catch (IOException ioe) {
	txtS.setText("Exception while reading file " + ioe);
}
finally {
	try {
		if (fin != null) {
			fin.close();
		}
	}
	catch (IOException ioe) {
		txtS.setText("Error while closing stream: " + ioe);
	}
}

}
	
	
	public static void main(String args[]) throws IOException 
	{ 
		 new Sender();

			
		} 
	} 
