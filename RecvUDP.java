import java.net.*;  // for DatagramSocket and DatagramPacket
import java.io.*;   // for IOException

public class RecvUDP {

  public static void main(String[] args) throws Exception {

      if (args.length != 1 && args.length != 2)  // Test for correct # of args        
	  throw new IllegalArgumentException("Parameter(s): <Destination>" +
					                                " <Port> [<encoding]");
      
      InetAddress destAddr = InetAddress.getByName(args[0]);
      int port = Integer.parseInt(args[0]);   // Receiving Port
      
      DatagramSocket sock = new DatagramSocket(port);  // UDP socket for receiving      
      DatagramPacket packet = new DatagramPacket(new byte[1024],1024);
      sock.receive(packet);
      
      // Receive binary-encoded Request                              
      Decoder decoder = (args.length == 2 ?   // Which encoding              
				                new ClientRequestDecoderBin(args[1]) :
				                new ClientRequestDecoderBin() );


      Request receivedRequest = decoder.decode(packet);

      System.out.println("Received request: \n" + receivedRequest.toString());
      
      //Make reponse class to calculate result
      Response response = new Response(receivedRequest.requestID, receivedRequest.operation, receivedRequest.leftOperand, receivedRequest.rightOperand);
      
      //Encode response

      Encoder encoder = (args.length == 2 ?   // Which encoding              
                       new EncoderBin (args[1]) :
                       new EncoderBin() );

      
      byte[] codedResponse = encoder.encode(response); // Encode Response

      DatagramPacket message = new DatagramPacket(codedResponse, codedResponse.length, 
						  destAddr, port);
      sock.send(message);
      sock.close();
  }
}
