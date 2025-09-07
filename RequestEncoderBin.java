import java.io.*;  // for ByteArrayOutputStream and DataOutputStream

public class RequestEncoderBin implements RequestEncoder, RequestBinConst {

  private String encoding;  // Character encoding

  public RequestEncoderBin() {
    encoding = DEFAULT_ENCODING;
  }

  public RequestEncoderBin(String encoding) {
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
    System.out.println(data);
    return data;
  }
}
