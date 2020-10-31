package l2;

import java.util.HashMap;

public class HTTPHeadersMap {
    private HashMap<String, HTTPHeader> headers = new HashMap<>();

    void addHeader(HTTPHeader httpHeader) {
        this.headers.put(httpHeader.getName(), httpHeader);
    }

    public String toString() {
        StringBuilder string = new StringBuilder();

        for (HTTPHeader header : this.headers.values()) {
            string.append(header);
        }

        return string.toString();
    }

    HTTPHeader getHeader(String name) {
        return this.headers.get(name);
    }
}