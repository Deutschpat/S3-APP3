package com.company;

import java.io.*;
import java.net.*;

public class QuoteClient {
    public static void main(String[] args) throws IOException {

        // get a datagram socket
        // send request
        ApplicationClient applicationClient = new ApplicationClient();
        applicationClient.envoieVersTransportClient("C:\\Users\\Telep\\Documents\\S3\\APP3\\src\\com\\company\\S3-APP3\\company\\one-liners.txt", args);


        DatagramSocket socket = new DatagramSocket();
        byte[] buf = new byte[256];
        InetAddress address = InetAddress.getByName(args[0]);
        DatagramPacket packet = new DatagramPacket(buf,buf.length,address,25500);
/*        // get response
        packet = new DatagramPacket(buf, buf.length);
        socket.receive(packet);

        // display response
        String received = new String(packet.getData(), 0, packet.getLength());
        System.out.println("Quote of the Moment: " + received);*/

/*        //tests
        TransportClient tc = new TransportClient();
        byte[] b = new byte[6000];
        int test1 = tc.CalculerNombrePackets(b);
        System.out.println(test1);*/



    }

}