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
	
	
	public static void main(String args[]) throws IOException 
	{ 
		Scanner sc = new Scanner(System.in); 
		int sequenceNumber = 0;
		long start = System.nanoTime();
		byte packet =1;
		int bytesRead = 0;
		int timeout = 2000; //temporary timeout placeholder in miliseconds


		DatagramSocket ds = new DatagramSocket(); 


		byte buf[] = null; 
		System.out.println("Please enter the IP address of the receiver");
		InetAddress ip = InetAddress.getByName(sc.nextLine());
		System.out.println("Please enter port number of the receiver");
		int port = sc.nextInt();
		sc.nextLine();


			File file = new File("I:\\cp372-a2\\src\\hello.txt");
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
				new DatagramPacket(sndBuf, sndBuf.length, ip, port); 

			

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
				System.out.println("File not found" + e);
			}
			catch (IOException ioe) {
				System.out.println("Exception while reading file " + ioe);
			}
			finally {
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
