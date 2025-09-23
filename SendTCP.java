import java.io.*;   // for Input/OutputStream
import java.net.*;  // for Socket
import java.util.Random;
import java.util.Scanner;

public class SendTCP {

  public static void main(String args[]) throws Exception {

    if (args.length != 2)  // Test for correct # of args
      throw new IllegalArgumentException("Parameter(s): <Destination> <Port>");

      InetAddress destAddr = InetAddress.getByName(args[0]);  // Destination address
      int destPort = Integer.parseInt(args[1]);               // Destination port

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
        // DatagramPacket message = new DatagramPacket(new byte[1], 1, 
        //       destAddr, destPort);
        // sock.send(message);

        // closeSocket = true;
        sock.close();
        scanner.close();
        System.out.println("Exiting program.");
        // break;
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
        
      // Use the encoding scheme given on the command line (args[2])
      Encoder encoder = (args.length == 3 ?
          new EncoderBin(args[2]) :
          new EncoderBin());
        

      byte[] codedRequest = encoder.encode(request); // Encode Request


      //System.out.println("Sending Friend (Binary)");
      OutputStream out = sock.getOutputStream(); // Get a handle onto Output Stream
      out.write(codedRequest); // Encode and send

      // Receive binary-encoded Response
      Decoder decoder = (args.length == 3 ?
          new DecoderBin(args[2]) :
          new DecoderBin());
      Response response = (Response) decoder.decode(sock.getInputStream(), false); // true for request, false for response  
      System.out.println("Received response: \n" + response.toString()); //display response to client
      scanner.close();
      sock.close();

  }
}
