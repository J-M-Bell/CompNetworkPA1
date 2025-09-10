public class ServerResponseEncoderBin implements Encoder, RequestBinConst{

  private String encoding;  // Character encoding

  public ServerResponseEncoderBin() {
    encoding = DEFAULT_ENCODING;
  }

  public ServerResponseEncoderBin(String encoding) {
    this.encoding = encoding;
  }

  public byte[] encode(Response response) throws Exception {

    ByteArrayOutputStream buf = new ByteArrayOutputStream();
    DataOutputStream out = new DataOutputStream(buf);

    
    out.writeByte(0); // Message length placeholder
    out.writeShort(response.requestID);
    out.writeByte(response.statusCode);
    out.writeInt(response.result);
    out.flush();
    byte[] data = buf.toByteArray();
    data[0] = (byte) data.length; // Set the length
    for (byte b : data) {
      System.out.print(String.format("%02X", b));
    }
    return data;
  }
    
}
