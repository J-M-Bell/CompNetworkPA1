import java.io.*;   // for InputStream and IOException
import java.net.*;  // for DatagramPacket

/**
 * An interface for creating an decoder
 * for both a client's request and a 
 * server's response.
 *
 * @author JM Bell
 * @version 9/10/25
 */
public interface Decoder {
  Object decode(InputStream source, boolean requestOrResponseFlag) throws IOException;
  Object decode(DatagramPacket packet, boolean requestOrResponseFlag) throws IOException;
}
