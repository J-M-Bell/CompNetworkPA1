import java.util.Map;

public interface RequestBinConst {
    public static final String DEFAULT_ENCODING = "ISO-8859-1";
    public static final int SINGLE_FLAG = 1 << 0; // Least significant bit
    public static final int RICH_FLAG   = 1 << 1; // weight 2^1 
    public static final int FEMALE_FLAG = 1 << 2; // weight 2^2
    public static final int MAX_LASTNAME_LEN = 255; // Max length lastname
    public static final int MAX_WIRE_LENGTH  = 1024; // Max length on the" wire"
    public static final Map<String, Byte> opCodes = Map.of(
        "-", (byte)0,
        "+", (byte)1,
        "&", (byte)2,
        "|", (byte)3,
        "*", (byte)4,
        "/", (byte)5
    );
    public static final Map<String, String> opNames = Map.of(
        "-", "subtraction",
        "+", "addition",
        "&", "and",
        "|", "or",
        "*", "multiplication",
        "/", "division"
    );
}
