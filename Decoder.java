import java.io.*;   // for InputStream and IOException
import java.net.*;  // for DatagramPacket

/*
* An interface for decoding packets sent between
* the client and the server
*/
public interface Decoder {
  Request decode(InputStream source) throws IOException;
  Request decode(DatagramPacket packet) throws IOException;
}
