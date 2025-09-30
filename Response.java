/**
 * A class that implements a response
 * object that handles the server's
 * response to the client's request.
 * 
 * @author JM Bell
 * @version 9/10/25
 */
public class Response implements RequestBinConst {
    public short requestID;     // Request ID
    public String operation;    // Operation
    public int leftOperand;   // Left operand
    public int rightOperand;  // Right operand
    public int result;        // Result of operation
    public byte errorCode; // error code

    /**
     * A constructor for the server's response when
	 * the result has not been calculated.
     *
     * 
     * @param requestID short - ID of client's request
	 * @param operation String - operation of the client's request 
     * @param leftOperand int - the left operand of the equation
     * @param rightOperand int - the right operand of the equation 
     */
    public Response(short requestID, String operation, int leftOperand,
             int rightOperand) {
        this.errorCode = 127;
        this.requestID = requestID;
        this.operation = operation;
        this.leftOperand = leftOperand;
        this.rightOperand = rightOperand;
        this.result = calculateResult();
    }

    /**
     * A constructor for the server's response when
	 * the result is known.
     *
     * 
     * @param requestID short - ID of client's request
	 * @param errorCode byte - error code for the operation  
     * @param result int - the result of the client's requested operation 
     */
    public Response(short requestID, byte errorCode, int result) {
        this.requestID = requestID;
        this.errorCode = errorCode;
        this.result = result;
    }

	/**
     * A constructor for the server's response when
	 * the result has not been calculated.
     *
     * 
     * @return int - result of client's request 
     */
    public int calculateResult() {
        switch (operation) {
            case "+": // ADD
                result = leftOperand + rightOperand;
                errorCode = 0;
                break;
            case "-": // SUB
                result = leftOperand - rightOperand;
                errorCode = 0;
                break;
            case "&": // AND
                result = leftOperand & rightOperand;
                errorCode = 0;
                break;
            case "|": // OR
                result = leftOperand | rightOperand;
                errorCode = 0;
                break;    
            case "*": // MUL
                result = leftOperand * rightOperand;
                errorCode = 0;
                break;
            case "/": // DIV
                if (rightOperand == 0) {
                    errorCode = 1; // Division by zero error
                    result = 0;
                } else {
                    result = leftOperand / rightOperand;
                    errorCode = 0;
                }
                break;
            default:
                result = 0;
        }
        return result;
    }

    /**
     * A simple method for displaying the server's
     * response to the client.
	 * 
	 * @return String - response information
     */
    public String toString() {
        String printString = "Request #" + requestID + "\nResult: " + result + "\n";
        if (errorCode == 0) {
            printString += "Ok";
        }
        else {
            printString += errorCode;
        }

        return printString;
    }

    /**
     * A method for displaying the response
     * byte by byte in hex format.
     */
    public void displayResponseBytes() {
        String responseString = " " + result + errorCode + requestID;
        byte[] responseByteArray = responseString.getBytes();
        responseByteArray[0] = (byte) responseByteArray.length;

        //display message one byte at a time in hex format
        for (int i = 0; i < responseByteArray.length; i++) {
            System.out.print(String.format("%02X", responseByteArray[i]));
        }
        System.out.println("\n");
    }
}
