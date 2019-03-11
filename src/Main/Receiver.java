package Main;
import java.io.FileOutputStream;
import java.io.IOException;
import java.net.DatagramPacket;
import java.net.DatagramSocket;
import java.net.InetAddress;
import java.net.SocketException;
import java.util.ArrayList;
import java.util.Arrays;
import java.nio.ByteBuffer;
import java.util.HashMap;

public class Receiver 
{ 
	static byte[] trim(byte[] bytes)
{
    int i = bytes.length - 1;
    while (i >= 0 && bytes[i] == 0)
    {
        --i;
    }

    return Arrays.copyOf(bytes, i + 1);
}
	public static void main(String[] args) throws IOException 
	{ 
		// Step 1 : Create a socket to listen at port 1234 
		DatagramSocket ds = new DatagramSocket(1234); 
		byte[] receive = new byte[65535];
		byte[] handbyte = new byte[1]; 

		DatagramPacket DpReceive = null; 
		DatagramPacket AckPacket = null;
		FileOutputStream fileOuputStream = new FileOutputStream("outtt",true);
		DpReceive = new DatagramPacket(handbyte, 1);
		ds.receive(DpReceive);
		//DatagramPacket handshPacket = new DatagramPacket(handbyte, 1);
		ds.send(DpReceive);
		handbyte = new byte[65535];
		HashMap<Integer, Integer> dupPackets = new HashMap<Integer, Integer>();

		while (true) 
		{ 

			// Step 2 : create a DatgramPacket to receive the data. 
			DpReceive = new DatagramPacket(receive, receive.length);

			// Step 3 : revieve the data in byte buffer. 
			ds.receive(DpReceive);
			System.out.println(data(receive));
			byte sequenceNumber[] = Arrays.copyOfRange(receive, 0, 4);
			//int message = ByteBuffer.wrap(DpReceive.getData()).getInt();

			// ByteBuffer wrapped = ByteBuffer.wrap(sequenceNumber); // big-endian by default
			// int num = wrapped.getInt(); // 1
			System.out.println("Sequence Number:-" + ByteBuffer.wrap(sequenceNumber).getInt());
			InetAddress IPAddress = DpReceive.getAddress();
			int port = DpReceive.getPort();
			AckPacket = new DatagramPacket(sequenceNumber, sequenceNumber.length, IPAddress,port);
			byte data[] = Arrays.copyOfRange(receive, 4, receive.length);


			try {
				if (ByteBuffer.wrap(sequenceNumber).getInt()%3==0){			 
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
				}}
			} catch (IOException e) {
				e.printStackTrace();
			}
	
			

			System.out.println("Client:-" + data(data)); 

			// Exit the server if the client sends "bye" 
			if (data(receive).toString().equals("bye")) 
			{ 
				System.out.println("Client sent bye.....EXITING"); 
				//break; 
			} 

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
