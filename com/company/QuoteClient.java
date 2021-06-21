package com.company;





import java.io.*;
import java.net.*;
import java.util.*;

public class QuoteClient {
    public static void main(String[] args) throws IOException {
        // get a datagram socket
        DatagramSocket socket = new DatagramSocket();
        // send request
        ApplicationClient applicationClient = new ApplicationClient();
        System.out.println("lol1");
        applicationClient.readFile("C:\\Users\\Telep\\Documents\\S3\\APP3\\src\\com\\company\\S3-APP3\\company\\one-liners.txt", args);

/*        // get response
        packet = new DatagramPacket(buf, buf.length);
        socket.receive(packet);

        // display response
        String received = new String(packet.getData(), 0, packet.getLength());
        System.out.println("Quote of the Moment: " + received);*/

        socket.close();
/*        //tests
        TransportClient tc = new TransportClient();
        byte[] b = new byte[6000];
        int test1 = tc.CalculerNombrePackets(b);
        System.out.println(test1);*/
    }

}