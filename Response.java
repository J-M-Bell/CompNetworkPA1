public class Response implements RequestBinConst {
    public short requestID;     // Request ID
    public String operation;    // Operation
    public int leftOperand;   // Left operand
    public int rightOperand;  // Right operand
    public int result;        // Result of operation
    public byte errorCode; // error code

    public Response(short requestID, String operation, int leftOperand,
             int rightOperand) {
        this.errorCode         = 127;
        this.requestID         = requestID;
        this.operation         = operation;
        this.leftOperand       = leftOperand;
        this.rightOperand      = rightOperand;
        this.result            = calculateResult();
    }

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
                errorCode = 2; // Invalid operation code error
                result = 0;
        }
        return result;
    }

    /*
     * A simple method for displaying the server's
     * response to the client.
     */
    public String toString() {
        String printString = "Request #" + requestID + " " + result + " ";
        if (errorCode == 0) {
            printString += "Ok";
        }
        else {
            printString += errorCode;
        }

        return printString;
    }
}
