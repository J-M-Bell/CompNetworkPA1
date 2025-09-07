import java.io.*;  // for ByteArrayInputStream
import java.net.*; // for DatagramPacket

public class RequestDecoderBin implements RequestDecoder, RequestBinConst {

  private String encoding;  // Character encoding

  public RequestDecoderBin() {
    encoding = DEFAULT_ENCODING;
  }

  public RequestDecoderBin(String encoding) {
    this.encoding = encoding;
  }

  public Request decode(InputStream wire) throws IOException { //possibly return a map<Request, byte[]>
      boolean single, rich, female;
  
    // Convert ByteArrayInputStream to byte array
        byte[] convertedBytes = wire.readAllBytes();

        // Print for verification
        for (byte b : convertedBytes) {
            System.out.print(b + " "); //make it on same line
        }  
    DataInputStream src = new DataInputStream(wire);
    byte length        = src.readByte(); 
    long  ID            = src.readLong();
    short streetnumber  = src.readShort();
    int   zipcode       = src.readInt();
    byte  flags         = src.readByte();  
    
    //Deal with the lastname
    int stringLength = src.read(); // Returns an unsigned byte as an int
    if (stringLength == -1)
      throw new EOFException();
    byte[] stringBuf = new byte[stringLength];
    src.readFully(stringBuf);
    String lastname = new String(stringBuf, encoding);

    return new Request(ID,lastname, streetnumber, zipcode,
      ((flags & SINGLE_FLAG) == SINGLE_FLAG),
		      ((flags & RICH_FLAG) == RICH_FLAG),
		      ((flags & FEMALE_FLAG) == FEMALE_FLAG));
  }

  public Request decode(DatagramPacket p) throws IOException {
    ByteArrayInputStream payload =
      new ByteArrayInputStream(p.getData(), p.getOffset(), p.getLength());
    return decode(payload);
  }
}
