import java.io.*;  // for ByteArrayInputStream
import java.net.*; // for DatagramPacket

/*
* A class that is used to decode both a
* client's request and a server's response.
*/
public class DecoderBin implements Decoder, RequestBinConst {

  private String encoding;  // Character encoding

  public DecoderBin() {
    encoding = DEFAULT_ENCODING;
  }

  public DecoderBin(String encoding) {
    this.encoding = encoding;
  }

	
  public Request decode(InputStream wire) throws IOException { 
  
    DataInputStream src = new DataInputStream(wire);

		//read frames from input stream
    byte length        = src.readByte();
    byte opCode       = src.readByte();
    int  leftOperand  = src.readInt();
    int  rightOperand = src.readInt();
    short requestID   = src.readShort();
    byte opNameLength = src.readByte();
    byte[] opNameBytes = new byte[opNameLength];
    src.readFully(opNameBytes);
    String opName     = new String(opNameBytes, encoding); 

		//create and return Request object
    Request request = new Request(validOperations.get(opCode), leftOperand, rightOperand, requestID);
    return request;
  }

  //create response version to this
  //probably decodeRequest and decodeResponse
  public Request decode(DatagramPacket p) throws IOException { 
    
		//gets and save the length of the message
    byte[] requestByteArray = p.getData();
    int messageLength = requestByteArray[0];
		

		//display message one byte at a time in hex format
    for (int i = 0; i < messageLength; i++) {
      System.out.print(String.format("%02X", requestByteArray[i]));
    }
    System.out.println("\n");
    

		//create and return byte[] input stream
    ByteArrayInputStream payload =
      new ByteArrayInputStream(p.getData(), p.getOffset(), p.getLength());
    return decode(payload);
  }
}
