package l2;

import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class HTTPHeader {
    private String name;
    private String value;

    private HTTPHeader(String name, String value) {
        this.name = name;
        this.value = value;
    }

    public static HTTPHeader fromResponseLine(String line) {
        Pattern pattern = Pattern.compile("^(.+): (.+)");
        Matcher matcher = pattern.matcher(line);
        matcher.find();

        return new HTTPHeader(matcher.group(1), matcher.group(2));
    }

    public static HTTPHeader fromNameAndValue(String name, String value) {
        return new HTTPHeader(name, value);
    }

    public String getName() {
        return name;
    }

    public String getValue() {
        return value;
    }

    public String toString() {
        return this.name + ": " + this.value + "\r\n";
    }
}
