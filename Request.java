import java.net.Authenticator.RequestorType;

public class Request implements RequestBinConst{

    public String operation;
    public byte opCode;        // Op Code
    public int leftOperand;   // Left operand
    public int rightOperand;  // Right operand
    public short requestID;     // Request ID
    public byte opNameLength;  // Length of opName
    public String opName;     // Operation name

    public long ID;            // Item identification number
    public String lastName;    // Lastname
    public short streetNumber; // street #
    public int zipCode;        // zip code
    public boolean single;     // Single ?
    public boolean rich;       // Rich ?
    public boolean female;     // Female ?
    

  public Request(String operation, int leftOperand, int rightOperand,
         short requestID) {
      this.opCode            = opCodes.get(operation);
      this.leftOperand       = leftOperand;
      this.rightOperand      = rightOperand;
      this.requestID         = requestID;
      this.opName            = opNames.get(operation);
      this.opNameLength      = (byte)opName.length();    
  }

  public Request(long ID, String lastname, short streetnumber, 
		int zipcode, boolean single, boolean rich, boolean female)  {
      this.ID           = ID;
      this.lastName     = lastname;
      this.streetNumber = streetnumber;
      this.zipCode      = zipcode;
      this.single       = single;
      this.rich         = rich;
      this.female       = female;
  }
}
