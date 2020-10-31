package l2;

import java.io.*;
import java.net.ServerSocket;
import java.net.Socket;

public class Server {
    public static void run() {
        try {
            log("Start");

            int serverPort = 2137;

            log("Server socket opening");
            ServerSocket serverSocket = new ServerSocket(serverPort);
            log("Server socket opened");

            log("Server listening for incoming connections (accepting)");
            Socket client = serverSocket.accept();
            log("Accepted connection from: " + client.getInetAddress() + ":" + client.getPort());

            log("TCP streams collecting");

            InputStream inStream = client.getInputStream();
            OutputStream outStream = client.getOutputStream();
            InputStreamReader inReader = new InputStreamReader(inStream);
            OutputStreamWriter outWriter = new OutputStreamWriter(outStream);
            BufferedReader br = new BufferedReader(inReader);
            BufferedWriter bw = new BufferedWriter(outWriter);

            log("Waiting for request from client");
            String request = br.readLine();
            log("Request from client received: " + request);

            HTTPResponseStatusLine statusLine = HTTPResponseStatusLine.fromProtocolAndStatus("HTTP/1.1", 200, "OK");
            HTTPHeadersMap headers = new HTTPHeadersMap();

            String body = "<html>It works!</html>";

            headers.addHeader(HTTPHeader.fromNameAndValue("Content-Length", body.getBytes().length + ""));
            headers.addHeader(HTTPHeader.fromNameAndValue("Content-Type", "text/html; charset=UTF-8"));

            HTTPResponse response = new HTTPResponse(statusLine, headers, body);

            log("Sending response: \n" + response);

            bw.write(response.toString());
            bw.flush();
            log("Response sent");

            log("Server socket closing");
            inStream.close();
            outStream.close();
            client.close();
            serverSocket.close();
            log("Server socket closed");

            log("End");
        } catch (Exception e) {
            System.out.println(e.getMessage());
        }
    }

    public static void log(String message) {
        System.out.println("[S]: " + message);
    }
}
