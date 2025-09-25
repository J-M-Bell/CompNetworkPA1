import java.io.*;   // for Input/OutputStream
import java.net.*;  // for Socket and ServerSocket

public class RecvTCP {

  public static void main(String args[]) throws Exception {

    if (args.length != 1)  // Test for correct # of args
      throw new IllegalArgumentException("Parameter(s): <Port>");

      int port = Integer.parseInt(args[0]);   // Receiving Port
    
      ServerSocket servSock = new ServerSocket(port);
      byte[] buffer = new byte[1024];
      boolean closeSocket = false;

      while (!closeSocket) {
        Socket clntSock = servSock.accept();
        InputStream in = clntSock.getInputStream();
        // Receive binary-encoded Request                              
        Decoder decoder = (args.length == 2 ?   // Which encoding              
                          new DecoderBin(args[1]) :
                          new DecoderBin() );

        Request receivedRequest = (Request) decoder.decode(in, true); // true for request, false for response
        
        //Do Check to see if operation is "q" to close socket and exit program
        if (receivedRequest.operation.equals("q")) {
          closeSocket = true;
          clntSock.close();
          servSock.close();
          System.out.println("Exiting program.");
          return;
        }

        System.out.print("Received request (Byte Form): ");
        receivedRequest.displayRequestBytes(); //display request byte by byte in hex format

        System.out.println("Received request: \n" + receivedRequest.toString()); //display request to client
        
        //Make reponse class to calculate result
        Response response = new Response(receivedRequest.requestID, receivedRequest.operation, receivedRequest.leftOperand, receivedRequest.rightOperand);
        
        //Encode response

        Encoder encoder = (args.length == 2 ?   // Which encoding              
                        new EncoderBin (args[1]) :
                        new EncoderBin() );

        
        byte[] codedResponse = encoder.encode(response); // Encode Response

        OutputStream out = clntSock.getOutputStream(); // Get a handle onto Output Stream
        out.write(codedResponse); // Encode and send
        clntSock.close();
      }
  }
}
