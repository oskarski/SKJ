package l2;

import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class HTTPResponseStatusLine {
    private String protocolVersion;
    private int status;
    private String statusName;

    private HTTPResponseStatusLine(String protocolVersion, int status, String statusName) {
        this.protocolVersion = protocolVersion;
        this.status = status;
        this.statusName = statusName;
    }

    public static HTTPResponseStatusLine fromResponseFirstLine(String responseFirstLine) {
        Pattern pattern = Pattern.compile("^(.+) (\\d{3}) (\\w+)");
        Matcher matcher = pattern.matcher(responseFirstLine);
        matcher.find();

        return new HTTPResponseStatusLine(matcher.group(1), Integer.parseInt(matcher.group(2)), matcher.group(3));
    }

    @Override
    public String toString() {
        return this.protocolVersion + " " + this.status + " " + this.statusName;
    }
}
