package l3;

import java.net.DatagramPacket;
import java.net.DatagramSocket;
import java.net.InetAddress;

public class Client {
    public static void run() throws Exception {
        String dnsName = "8.8.8.8";
        int dnsPort = 53;

        byte[] data = {0x08, 0x54, 0x01, 0x00, 0x00, 0x01, 0x00, 0x00, 0x00, 0x00, 0x00, 0x00, 0x03, 0x77, 0x77, 0x77, 0x02, 0x77, 0x70, 0x02, 0x70, 0x6c, 0x00, 0x00, 0x01, 0x00, 0x01};

        log("Datagram socket opening");

        DatagramSocket socket = new DatagramSocket();

        log("Resolving DNS");
        InetAddress address = InetAddress.getByName(dnsName);
        log("DNS resolved");

        log("Creating packet");
        DatagramPacket packet = new DatagramPacket(data, data.length, address, dnsPort);
        log("Packet created");

        log("Sending packet");
        socket.send(packet);
        log("Packet sent");

        DatagramPacket receivedPacket = new DatagramPacket(new byte[2000], 2000);

        log("Receiving packet");
        socket.receive(receivedPacket);
        log("Packet received");

        log("From:" + receivedPacket.getAddress().toString() + ":" + receivedPacket.getPort());

        byte[] receivedData = receivedPacket.getData();
        int dataSize = receivedPacket.getLength();

        String stringData = new String(receivedData, 0, dataSize);

        log("Data: " + stringData);

        for (int i = 0; i < dataSize; i++) {
            // ostatnie 4 liczby to adres IP
            // 256 - 44 = 212 bo bajty są<-127;127>
            System.out.print(receivedData[i] + " ");
        }

        System.out.println();

        socket.close();
    }

    private static void log(String message) {
        System.out.println("[C]: " + message);
    }
}
