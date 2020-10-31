package l2;

public class HTTPResponse {
    private HTTPResponseStatusLine statusLine;
    private HTTPHeadersMap headers;
    private String body;

    public HTTPResponse(HTTPResponseStatusLine statusLine, HTTPHeadersMap headers, String body) {
        this.statusLine = statusLine;
        this.headers = headers;
        this.body = body;
    }

    public HTTPResponseStatusLine getStatusLine() {
        return statusLine;
    }

    public HTTPHeadersMap getHeaders() {
        return headers;
    }

    public String getBody() {
        return body;
    }
}
