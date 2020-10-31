package l2;

import java.io.*;
import java.net.InetAddress;
import java.net.Socket;
import java.net.UnknownHostException;

public class Client {
    private final String host;
    private final int serverPort;
    private Socket socket;
    private InputStream inStream;
    private OutputStream outStream;
    private InputStreamReader inReader;
    private OutputStreamWriter outWriter;
    private BufferedReader bufferedReader;
    private BufferedWriter bufferedWriter;

    private Client(String host, int serverPort) {
        this.host = host;
        this.serverPort = serverPort;
    }

    public static void run() {
//        Client client = new Client("gaia.cs.umass.edu", 80);
        Client client = new Client("localhost", 2137);

        InetAddress serverAddress = client.resolveServerAddress();
        client.createSocketConnection(serverAddress);

        HTTPRequestLine httpRequestLine = new HTTPRequestLine("GET", "/wireshark-labs/HTTP-wireshark-file1.html");
        HTTPHeadersMap requestHeaders = new HTTPHeadersMap();

        HTTPResponse response = client.sendRequest(httpRequestLine, requestHeaders);

        System.out.println(response.getStatusLine());
        System.out.println(response.getHeaders());
        System.out.println(response.getBody());

        client.closeConnection();
    }

    private static void log(String message) {
        System.out.println("[C]: " + message);
    }

    public HTTPResponse sendRequest(HTTPRequestLine requestLine, HTTPHeadersMap headers) {
        try {
            headers.addHeader(HTTPHeader.fromNameAndValue("Host", this.host));

            log("HTTP request sending: \n" + requestLine + "\n" + headers);

            this.bufferedWriter.write(requestLine.toString());
            this.bufferedWriter.write(headers.toString());
            this.bufferedWriter.write("\r\n");

            this.bufferedWriter.flush();

            log("HTTP response receiving");
            String responseFirstLine = this.bufferedReader.readLine();
            HTTPResponseStatusLine responseStatusLine = HTTPResponseStatusLine.fromResponseFirstLine(responseFirstLine);

            HTTPHeadersMap responseHeaders = new HTTPHeadersMap();
            String headerLine = this.bufferedReader.readLine();

            while (!headerLine.isEmpty()) {
                responseHeaders.addHeader(HTTPHeader.fromResponseLine(headerLine));
                headerLine = this.bufferedReader.readLine();
            }

            int contentLength = Integer.parseInt(responseHeaders.getHeader("Content-Length").getValue());
            char[] content = new char[contentLength];
            this.bufferedReader.read(content, 0, contentLength);

            return new HTTPResponse(responseStatusLine, responseHeaders, new String(content));
        } catch (IOException e) {
            System.out.println(e.getMessage());
            return null;
        }
    }

    private InetAddress resolveServerAddress() {
        try {
            log("Server name resolving (DNS)");

            return InetAddress.getByName(this.host);
        } catch (UnknownHostException e) {
            System.out.println(e.getMessage());

            return null;
        }
    }

    private void createSocketConnection(InetAddress serverAddress) {
        try {
            log("TCP connection creating - socket opening");

            this.socket = new Socket(serverAddress, this.serverPort);

            log("TCP streams collecting");

            this.inStream = this.socket.getInputStream();
            this.outStream = this.socket.getOutputStream();
            this.inReader = new InputStreamReader(inStream);
            this.outWriter = new OutputStreamWriter(outStream);
            this.bufferedReader = new BufferedReader(inReader);
            this.bufferedWriter = new BufferedWriter(outWriter);
        } catch (IOException e) {
            System.out.println(e.getMessage());
        }
    }

    private void closeConnection() {
        try {
            log("TCP connection closing - socket closing");

            this.inStream.close();
            this.outStream.close();
            this.socket.close();
        } catch (IOException e) {
            System.out.println(e.getMessage());
        }
    }
}


