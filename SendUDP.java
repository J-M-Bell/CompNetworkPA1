import java.net.*;  // for DatagramSocket, DatagramPacket, and InetAddress
import java.io.*;   // for IOException
import java.util.Scanner;
import java.util.Random;


public class SendUDP implements RequestBinConst{

  public static void main(String args[]) throws Exception {

    if (args.length != 2 && args.length != 3)  // Test for correct # of args        
	    throw new IllegalArgumentException("Parameter(s): <Destination>" +
					                                " <Port> [<encoding]");
      
      
      InetAddress destAddr = InetAddress.getByName(args[0]);  // Destination address
      int destPort = Integer.parseInt(args[1]);               // Destination port
      
      //ask user for input
      Scanner scanner = new Scanner(System.in);

      // Prompt the user for their name
      System.out.print("Please enter the op code: " + "\n" +
           "- - subtraction" + "\n" +
           "+ - addition" + "\n" +
           "& - and" + "\n" +
           "| - or" + "\n" +
           "* - multiplication" + "\n" +
           "/ - division" + "\n");
      String operation = scanner.nextLine();
 
      // get operands
      System.out.print("Please enter operand 1: ");
      int leftOperand = scanner.nextInt();
      System.out.print("Please enter operand 2: ");
      int rightOperand = scanner.nextInt();

      //create random requestID
      Random random = new Random();
      int randomNumber = random.nextInt(100) + 1;
      short requestID = (short) randomNumber;


      Request request = new Request(operation, leftOperand, rightOperand, requestID);
      
      DatagramSocket sock = new DatagramSocket(); // UDP socket for sending
      
      
      // Use the encoding scheme given on the command line (args[2])
      Encoder encoder = (args.length == 3 ?
				  new EncoderBin(args[2]) :
				  new EncoderBin());
      

      byte[] codedRequest = encoder.encode(request); // Encode Request

      


      DatagramPacket message = new DatagramPacket(codedRequest, codedRequest.length, 
						  destAddr, destPort);
      sock.send(message);

      DatagramPacket packet = new DatagramPacket(new byte[1024],1024);

      sock.receive(packet);

      // Decoder decoder = (args.length == 3 ?   // Which encoding              
      //                   new ServerResponseDecoderBin(args[2]) :
      //                   new ServerResponseDecoderBin() );
      
      // Response receivedResponse = decoder.decode(packet);
      sock.close();
  }
}
