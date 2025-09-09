import java.util.ArrayList;
import java.util.List;


public interface RequestBinConst {
    public static final String DEFAULT_ENCODING = "ISO-8859-1";
    public static final int MAX_WIRE_LENGTH  = 1024; // Max length on the" wire"
    public static final List<String> validOperations = new ArrayList<String>() {{
        add("-");
        add("+");
        add("&");
        add("|");
        add("*");
        add("/");       
    }};
    public static final String[] validOpNames = {
        "subtraction",
        "addition",
        "and",
        "or",
        "multiplication",
        "division"
    };
}
