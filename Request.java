import java.net.Authenticator.RequestorType;

/**
 * A class that implements a reqest
 * object that handles the client's
 * request and calculates the result
 * of the equation.
 * 
 * @author JM Bell
 * @version 9/10/25
 */
public class Request implements RequestBinConst{

    public String operation;
    public byte opCode;        // Op Code
    public int leftOperand;   // Left operand
    public int rightOperand;  // Right operand
    public short requestID;     // Request ID
    public byte opNameLength;  // Length of opName
    public String opName;     // Operation name
    

  /**
   * A method that creates a object that represents
   * the client's request
   *
   * @param operation String - the sign of the operation for the request 
   * @param leftOperand int - the left operand of the equation
   * @param rightOperand int - the right operand of the equation
   * @param requestID short - the ID of the client's request 
   */
  public Request(String operation, int leftOperand, int rightOperand,
         short requestID) {
      this.opCode            = (byte) validOperations.indexOf(operation);
      this.leftOperand       = leftOperand;
      this.rightOperand      = rightOperand;
      this.requestID         = requestID;
      this.opName            = validOpNames[this.opCode];
      this.opNameLength      = (byte)opName.length();
      this.operation         = operation;    
  }

  /**
   * A constructor for creating a quit
   * request when the client wants to
   * exit the program.
   * 
   * @param operation String - the "q" operation to quit the program
   */
  public Request(String operation) {
      this.operation = operation;
  }
  /**
   * A simple method for displaying the request 
   * made by the client.
   *
   * @return String - request information
   */
  public String toString() {
      return "Request# " + requestID + "\n" + 
              "Request: " + leftOperand + " " + operation + " " + rightOperand;
  }

  /**
   * A method for displaying the request
   * byte by byte in hex format.
   */
  public void displayRequestBytes() {
      String requestString = " " + opCode + leftOperand + rightOperand + requestID + opNameLength + opName;
      byte[] requestByteArray = requestString.getBytes();
      requestByteArray[0] = (byte) requestByteArray.length;

      //display message one byte at a time in hex format
      for (int i = 0; i < requestByteArray.length; i++) {
        System.out.print(String.format("%02X", requestByteArray[i]));
      }
      System.out.println("\n");
  }
}
