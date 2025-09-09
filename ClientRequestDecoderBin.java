import java.io.*;  // for ByteArrayInputStream
import java.net.*; // for DatagramPacket

public class ClientRequestDecoderBin implements Decoder, RequestBinConst {

  private String encoding;  // Character encoding

  public ClientRequestDecoderBin() {
    encoding = DEFAULT_ENCODING;
  }

  public ClientRequestDecoderBin(String encoding) {
    this.encoding = encoding;
  }

  public Request decode(InputStream wire) throws IOException { //possibly return a map<Request, byte[]>
  
    DataInputStream src = new DataInputStream(wire);
    byte length        = src.readByte();
    byte opCode       = src.readByte();
    int  leftOperand  = src.readInt();
    int  rightOperand = src.readInt();
    short requestID   = src.readShort();
    byte opNameLength = src.readByte();
    byte[] opNameBytes = new byte[opNameLength];
    src.readFully(opNameBytes);
    String opName     = new String(opNameBytes, encoding); 
    System.out.println(leftOperand + " " + opName + " " + rightOperand);

    return null;
  }

  public Request decode(DatagramPacket p) throws IOException {
    //*use length of byte[] to stop loop*
    for (byte b : p.getData()) {
      System.out.print(String.format("%02X", b));
    }

    ByteArrayInputStream payload =
      new ByteArrayInputStream(p.getData(), p.getOffset(), p.getLength());
    return decode(payload);
  }
}
