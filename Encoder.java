/**
 * An interface for creating an encoder
 * for both a client's request and a 
 * server's response.
 *
 * @author JM Bell
 * @version 9/10/25
 */
public interface Encoder {
  byte[] encode(Request request) throws Exception;
  byte[] encode(Response response) throws Exception;
}
