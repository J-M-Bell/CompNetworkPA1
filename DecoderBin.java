import java.io.*;  // for ByteArrayInputStream
import java.net.*; // for DatagramPacket

/**
 * A class that is used to decode both
 * a request from the client and a response
 * from the user.
 *
 * @author JM Bell
 * @version 9/10/25
 */
public class DecoderBin implements Decoder, RequestBinConst {

  private String encoding;  // Character encoding

  /**
	 * A constructor for instantiating
   * the DecoderBin class with the
   * default character encoding.
   */
  public DecoderBin() {
    encoding = DEFAULT_ENCODING;
  }

	/**
	 * A constructor for instantiating
   * the DecoderBin class with the
   * user-defined character encoding.
   */
  public DecoderBin(String encoding) {
    this.encoding = encoding;
  }

	/**
   * A class that is used to decode
   * either a request from the client
   * or a response from the server using
   * an InputStream.
   *
   * @param wire InputStream - input stream containing the bytes of either a request or reponse
   * @param requestOrResponseFlag boolean - flag for whether decoding a request or a response 
   *
   * @return byte[] - client's request or server's response encoded into bytes
   */
  public Object decode(InputStream wire, boolean requestOrResponseFlag) throws IOException { 
  
    DataInputStream src = new DataInputStream(wire);
    if (requestOrResponseFlag) {
      //read frames from input stream
      byte firstByte = src.readByte();
      //check for "q" operation to send quit signal
      if (firstByte == "q".getBytes()[0]) {
        return new Request("q");
      }
      byte opCode = src.readByte();
      int  leftOperand = src.readInt();
      int  rightOperand = src.readInt();
      short requestID = src.readShort();
      byte opNameLength = src.readByte();
      byte[] opNameBytes = new byte[opNameLength];
      src.readFully(opNameBytes);
      String opName = new String(opNameBytes, "UTF-16"); 

      //create and return Request object
      Request request = new Request(validOperations.get(opCode), leftOperand, rightOperand, requestID);
      return request;
    } 
    else {
      byte length = src.readByte();
      int result = src.readInt();
      byte errorCode = src.readByte();
      short requestID = src.readShort();
      Response response = new Response(requestID, errorCode, result);
      return response;
    }
  }

  /**
   * A class that is used to decode
   * either a request from the client
   * or a response from the server using
   * a DatagramPacket.
   *
   * @param p DatagramPacket - packet reaceived for decoding 
   * @param requestOrResponseFlag boolean - flag for whether decoding a request or a response
   *
   * @return byte[] - client's request or server's response encoded into bytes
   */
  public Object decode(DatagramPacket p, boolean requestOrResponseFlag) throws IOException { 
     
		//create and return byte[] input stream
    ByteArrayInputStream payload =
      new ByteArrayInputStream(p.getData(), p.getOffset(), p.getLength());
    return decode(payload, requestOrResponseFlag);
  }
}
