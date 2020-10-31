package l2;

public class HTTPRequestLine {
    private String method;
    private String pathname;
    private String protocolVersion;

    public HTTPRequestLine(String method, String pathname, String protocolVersion) {
        this.method = method;
        this.pathname = pathname;
        this.protocolVersion = protocolVersion;
    }

    public HTTPRequestLine(String method, String pathname) {
        this(method, pathname, "HTTP/1.1");
    }

    @Override
    public String toString() {
        return this.method + " " + this.pathname + " " + this.protocolVersion + "\r\n";
    }
}
