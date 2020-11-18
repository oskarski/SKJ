package l2;

import java.io.*;
import java.net.InetAddress;
import java.net.Socket;

public class TcpClient {
    public static void main(String[] args) throws Exception {
        String host = "172.21.48.194";
        int port = 10000;
        String index = "20506";
        String key = "151810";

        InetAddress serverAddress = InetAddress.getByName(host);

        Socket socket = new Socket(serverAddress, port);
        InputStream inStream = socket.getInputStream();
        OutputStream outStream = socket.getOutputStream();
        InputStreamReader inReader = new InputStreamReader(inStream);
        OutputStreamWriter outWriter = new OutputStreamWriter(outStream);
        BufferedReader bufferedReader = new BufferedReader(inReader);
        BufferedWriter bufferedWriter = new BufferedWriter(outWriter);


        bufferedWriter.write(index);
        bufferedWriter.write("\r\n");
        bufferedWriter.write(key);
        bufferedWriter.write("\r\n");

        bufferedWriter.flush();


        String flag = bufferedReader.readLine();

        System.out.println("Flag = " + flag);

        inStream.close();
        outStream.close();
        socket.close();
    }
}