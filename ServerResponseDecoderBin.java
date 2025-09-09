public class ServerResponseDecoderBin //implements Decoder, RequestBinConst 
{
    // private String encoding;  // Character encoding

    // public ServerResponseDecoderBin() {
    //   encoding = DEFAULT_ENCODING;
    // }

    // public ServerResponseDecoderBin(String encoding) {
    //   this.encoding = encoding;
    // }

    // public Response decode(InputStream wire) throws IOException { //possibly return a map<Request, byte[]>
    //   DataInputStream src = new DataInputStream(wire);
    //   byte length        = src.readByte();
    //   short requestID   = src.readShort();
    //   byte statusCode       = src.readByte();
    //   int  result  = src.readInt();

    //   Response response = new Response();
    //   response.length = length;
    //   response.requestID = requestID;
    //   response.statusCode = statusCode;
    //   response.result = result;

    //   return response;
    // }

    // public Response decode(DatagramPacket p) throws IOException {
    //   ByteArrayInputStream payload =
    //     new ByteArrayInputStream(p.getData(), p.getOffset(), p.getLength());
    //   return decode(payload);
    // }

}
