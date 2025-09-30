import java.io.*;   // for Input/OutputStream
import java.net.*;  // for Socket
import java.util.Random;
import java.util.Scanner;

/**
 * A class for creating the TCP client
 * for sending the request and
 * receiving the server's response.
 * 
 * @author JM Bell
 * @version 9/10/25
 */
public class SendTCP {

  public static void main(String args[]) throws Exception {

    if (args.length != 2)  // Test for correct # of args
      throw new IllegalArgumentException("Parameter(s): <Destination> <Port>");

      InetAddress destAddr = InetAddress.getByName(args[0]);  // Destination address
      int destPort = Integer.parseInt(args[1]);               // Destination port


      


      boolean closeSocket = false;
      while (!closeSocket) {
        Socket sock = new Socket(destAddr, destPort);
        //ask user for input
        Scanner scanner = new Scanner(System.in);

        // Prompt the user for their name
        System.out.print("Please enter the op code: " + "\n" +
            "- - subtraction" + "\n" +
            "+ - addition" + "\n" +
            "& - and" + "\n" +
            "| - or" + "\n" +
            "* - multiplication" + "\n" +
            "/ - division" + "\n" +
            "q - quit\n");
        String operation = scanner.nextLine();

        if (operation.equals("q")) {
          Request quitRequest = new Request(operation);

          Encoder encoder = (args.length == 3 ?
            new EncoderBin(args[2]) :
            new EncoderBin());

          byte[] codedRequest = encoder.encode(quitRequest); // Encode Request
          OutputStream out = sock.getOutputStream(); // Get a handle onto Output Stream
          out.write(codedRequest); // Encode and send
          closeSocket = true;
          sock.close();
          scanner.close();
          System.out.println("Exiting program.");
          break;
        }

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
        System.out.print("Sending request (Byte Form): "); //display request to client
        request.displayRequestBytes();
          
        // Use the encoding scheme given on the command line (args[2])
        Encoder encoder = (args.length == 3 ?
            new EncoderBin(args[2]) :
            new EncoderBin());
          

        byte[] codedRequest = encoder.encode(request); // Encode Request


        OutputStream out = sock.getOutputStream(); // Get a handle onto Output Stream
        out.write(codedRequest); // Encode and send
        long sentTime = System.currentTimeMillis();


        // Receive binary-encoded Response
        Decoder decoder = (args.length == 3 ?
            new DecoderBin(args[2]) :
            new DecoderBin());
        Response response = (Response) decoder.decode(sock.getInputStream(), false); // true for request, false for response
        long receivedTime = System.currentTimeMillis();
        long rtt = receivedTime - sentTime;
        System.out.print("Response received (byte form): ");
        response.displayResponseBytes();
        
        System.out.println("Received response: \n" + response.toString()); //display response to client
        System.out.println("RTT: " + rtt + " milliseconds\n");
        sock.close();
    }; // not sure about semicolon here
  }
}
