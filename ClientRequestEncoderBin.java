import java.io.*;  // for ByteArrayOutputStream and DataOutputStream

public class ClientRequestEncoderBin implements Encoder, RequestBinConst {

  private String encoding;  // Character encoding

  public ClientRequestEncoderBin() {
    encoding = DEFAULT_ENCODING;
  }

  public ClientRequestEncoderBin(String encoding) {
    this.encoding = encoding;
  }

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
    for (byte b : data) {
      System.out.print(String.format("%02X", b));
    }
    return data;
  }
}
