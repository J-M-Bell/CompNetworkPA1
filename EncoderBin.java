import java.io.*;  // for ByteArrayOutputStream and DataOutputStream

/**
 * A class that is used to encode both
 * a request from the client and a response
 * from the user.
 *
 * @author JM Bell
 * @version 9/10/25
 */
public class EncoderBin implements Encoder, RequestBinConst {

  private String encoding;  // Character encoding

	/**
	 * A constructor for instantiating
   * the EncoderBin class with the
   * default character encoding.
   */
  public EncoderBin() {
    encoding = DEFAULT_ENCODING;
  }

	/**
	 * A constructor for instantiating
   * the EncoderBin class with the
   * user-defined character encoding.
   */
  public EncoderBin(String encoding) {
    this.encoding = encoding;
  }

	/**
   * A class that is used to encode
   * a request from the client.
   *
   * @param Request Request - Request object for client's request
   *
   * @return byte[] - client's request encoded into bytes
   */
  public byte[] encode(Request Request) throws Exception {

    ByteArrayOutputStream buf = new ByteArrayOutputStream();
    DataOutputStream out = new DataOutputStream(buf);

    
    out.writeByte(0); // Message length placeholder
    out.writeByte(Request.opCode);
    out.writeInt(Request.leftOperand);
    out.writeInt(Request.rightOperand);
    out.writeShort(Request.requestID);
    out.writeByte(Request.opNameLength);
    byte[] encodedOperation = Request.opName.getBytes(encoding);
    out.write(encodedOperation);
    out.flush();
    byte[] data = buf.toByteArray();
    data[0] = (byte) data.length; // Set the length
		
		//print request byte by byte
    for (byte b : data) {
      System.out.print(String.format("%02X", b));
    }
    System.out.println("\n");
    return data;
  }

  /**
   * A class that is used to encode
   * a response from the server.
   *
   * @param response Response - Response object for server's response
   *
   * @return byte[] - server's response encoded into bytes
   */
  public byte[] encode(Response response) throws Exception {
    ByteArrayOutputStream buf = new ByteArrayOutputStream();
    DataOutputStream out = new DataOutputStream(buf);

    //write encoded bytes to output stream
    out.writeByte(0); // Message length placeholder
    out.writeInt(response.result);
    out.writeByte(response.errorCode);
    out.writeShort(response.requestID);
    out.flush();
    byte[] data = buf.toByteArray();
    data[0] = (byte) data.length; // Set the length

		//print response byte by byte
    for (byte b : data) {
      System.out.print(String.format("%02X", b));
    }
    System.out.println("\n");
    return data;
  }
}
