import java.net.*;  // for DatagramSocket and DatagramPacket
import java.io.*;   // for IOException

/**
 * A class for creating the UDP server
 * for receiving a client's request and
 * sending a response to the client.
 * 
 * @author JM Bell
 * @version 9/10/25
 */
public class RecvUDP {

  public static void main(String[] args) throws Exception {

      if (args.length != 1 && args.length != 2)  // Test for correct # of args        
	  throw new IllegalArgumentException("Parameter(s): <Port> [<encoding>]");
      boolean closeSocket = false;

      while (!closeSocket) {
        int port = Integer.parseInt(args[0]);   // Receiving Port
        
        DatagramSocket sock = new DatagramSocket(port);  // UDP socket      
        DatagramPacket packet = new DatagramPacket(new byte[1024],1024);
        sock.receive(packet);
        //create code block for receiving packet of length 1 to close socket and exit program
        

        if (packet.getLength() == 1) {
          closeSocket = true;
          sock.close();
          System.out.println("Exiting program.");
          break;
        }
        InetAddress sender = packet.getAddress();

        System.out.print("Received request (Byte Form): ");
        // Receive binary-encoded Request                              
        Decoder decoder = (args.length == 2 ?   // Which encoding              
                          new DecoderBin(args[1]) :
                          new DecoderBin() );


        Request receivedRequest = (Request) decoder.decode(packet, true); // true for request, false for response

        System.out.println("Received request: \n" + receivedRequest.toString()); //display request to client
        
        //Make reponse class to calculate result
        Response response = new Response(receivedRequest.requestID, receivedRequest.operation, receivedRequest.leftOperand, receivedRequest.rightOperand);
        
        //Encode response

        Encoder encoder = (args.length == 2 ?   // Which encoding              
                        new EncoderBin (args[1]) :
                        new EncoderBin() );

        
        byte[] codedResponse = encoder.encode(response); // Encode Response
        DatagramPacket message = new DatagramPacket(codedResponse, codedResponse.length, sender, port);
        sock.send(message);
      };
  }
}
