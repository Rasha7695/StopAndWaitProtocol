package Main; 
import java.io.IOException; 
import java.net.DatagramPacket; 
import java.net.DatagramSocket; 
import java.net.InetAddress; 
import java.util.Scanner; 
import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;

public class Sender 
{ 
	public static void main(String args[]) throws IOException 
	{ 
		Scanner sc = new Scanner(System.in); 

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

			File file = new File("C:/Users/Rasha/Documents/GitHub/StopAndWaitProtocol/src/Main/hello.txt");
			FileInputStream fin = null;
			try {
				// create FileInputStream object
				fin = new FileInputStream(file);
				double n = (int)file.length();
				int MDS = 50;
				double cycles = Math.ceil(n/MDS);
				for(int i=0;i<cycles;i++){
					byte fileContent[] = new byte[MDS];
				 
				// Reads up to certain bytes of data from this input stream into an array of bytes.
				fin.read(fileContent);
				//create string from byte array

				String s = new String(fileContent);
				System.out.println("File content: " + s);
				


				// convert the String input into the byte array. 
			//buf = inp.getBytes(); 
			//buf = fileContent;
			// Step 2 : Create the datagramPacket for sending 
			// the data. 
			DatagramPacket DpSend = 
				new DatagramPacket(fileContent, fileContent.length, ip, port); 

			// Step 3 : invoke the send call to actually send 
			// the data. 
			ds.send(DpSend); 

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
