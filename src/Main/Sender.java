package Main;

import java.io.IOException;
import java.net.DatagramPacket;
import java.net.DatagramSocket;
import java.net.InetAddress;
import java.net.SocketTimeoutException;
import java.util.Scanner;

import java.nio.ByteBuffer;

import java.io.ByteArrayOutputStream;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;

public class Sender 
{ 
	//long start = System.currentTimeMillis();
	
	public static void main(String args[]) throws IOException 
	{ 
		Scanner sc = new Scanner(System.in); 
		int sequenceNumber = 0;
		long start = System.nanoTime();
		byte packet =1;
		int bytesRead = 0;
		int timeout = 200; //temporary timeout placeholder in miliseconds

		// Step 1:Create the socket object for 
		// carrying the data. 
		DatagramSocket ds = new DatagramSocket(); 

		//InetAddress ip = InetAddress.getLocalHost(); 
		byte buf[] = null; 
		System.out.println("Please enter the IP address of the receiver");
		InetAddress ip = InetAddress.getByName(sc.nextLine());
		System.out.println("Please enter port number of the receiver");
		int port = sc.nextInt();
		sc.nextLine();
		// loop while user not enters "bye" 
	//	while (true) 
	//	{ 
		//	String inp = sc.nextLine(); 

			File file = new File("Main\\hello.txt");
			FileInputStream fin = null;
			try {
				// create FileInputStream object
				fin = new FileInputStream(file);
				double n = (int)file.length();
				int MDS = 50;
				double cycles = Math.ceil(n/MDS);
				byte header[] = new byte[] {1};
				DatagramPacket handshakePacket = new DatagramPacket(header, 1, ip, port);
				DatagramPacket handbyte = new DatagramPacket(header, 1);
				ds.send(handshakePacket);
				ds.receive(handbyte);
				while(!handbyte.getData().equals(header)){
					ds.send(handshakePacket);
					ds.receive(handbyte);
				}




				//byte header[] = new byte[10];
				for(int i=0;i<cycles;i++){
					sequenceNumber+=1;
					//byte seq = (byte) sequenceNumber;
					header = new byte[4];
					header = ByteBuffer.allocate(4).putInt(sequenceNumber).array();
					System.out.println("Header size: "+header.length);
					byte fileContent[] = new byte[MDS];
					bytesRead+=i;
					//fileContent[0]=(byte)(sequenceNumber>>8);
					//fileContent[1]=(byte)(sequenceNumber);
					
					//set sequence acknowledgement code (valid and not valid)
					//if (packet == 0) {packet = 1;}
					//else {packet = 0;}
					//fileContent[0] = packet;
					
				// Reads up to certain bytes of data from this input stream into an array of bytes.
				fin.read(fileContent);
				//create string from byte array

				String s = new String(fileContent);
				System.out.println("File content: " + s);

			// Step 2 : Create the datagramPacket for sending 
			// the data. 
			ByteArrayOutputStream outputStream = new ByteArrayOutputStream( );
			outputStream.write( header );
			outputStream.write( fileContent );
			byte sndBuf[] = outputStream.toByteArray( );
			DatagramPacket DpSend = 
				new DatagramPacket(sndBuf, sndBuf.length, ip, port); 

			

			// Step 3 : invoke the send call to actually send 
			// the data. 
			ds.send(DpSend); 
			byte seqNum[] = new byte[4];
			DatagramPacket ack = new DatagramPacket(seqNum, 4);
			ds.receive(ack);
			System.out.println(ByteBuffer.wrap(header).getInt());
			System.out.println(ByteBuffer.wrap(seqNum).getInt());

			//Compare ByteBuffer.wrap(header).getInt() == ByteBuffer.wrap(seqNum).getInt()

			// ByteBuffer wrapped = ByteBuffer.wrap(ack.getData()); // big-endian by default
			// int num = wrapped.getInt(); // 1
			// System.out.println("Acked packet sequence number: "+num);
			//ds.setSoTimeout(timeout);
			
			//Receive acknowledgement 
			// boolean response = false;
			// while (response ==false) {
			// 	byte[] ack = new byte[4];
			// 	DatagramPacket dsAck = new DatagramPacket(ack,4);
			// 	try {
			// 		ds.receive(dsAck);
			// 		ack = dsAck.getData();
			// 		//check if ack is valid and packet is not lost 
			// 		if (fileContent[0] == (ack[dsAck.getLength()-1]%2)) {
			// 			response = true;
			// 		}
			// 		//if it was lost or not valid, we need to resend packet
			// 		else {
			// 			ds.send(DpSend);
			// 			ds.setSoTimeout(timeout);
			// 		}
			// 	}
			// 	catch(SocketTimeoutException e ) {
			// 		ds.send(DpSend);
			// 		ds.setSoTimeout(timeout);
			// 	}
			// }

			// break the loop if user enters "bye" 
			// if (inp.equals("bye")) 
			// 	break; 
				}
				
	 
				
			 }
			catch (FileNotFoundException e) {
				System.out.println("File not found" + e);
			}
			catch (IOException ioe) {
				System.out.println("Exception while reading file " + ioe);
			}
			finally {
				// close the streams using close method
				try {
					if (fin != null) {
						fin.close();
					}
				}
				catch (IOException ioe) {
					System.out.println("Error while closing stream: " + ioe);
				}
			}

			
		} 
	} 
//} 
