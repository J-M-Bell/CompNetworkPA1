import java.io.*;   // for InputStream and IOException
import java.net.*;  // for DatagramPacket

public interface Decoder {
  Request decode(InputStream source) throws IOException;
  Request decode(DatagramPacket packet) throws IOException;
}
