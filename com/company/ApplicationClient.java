package com.company;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileReader;
import java.io.IOException;
import java.net.DatagramPacket;
import java.net.DatagramSocket;
import java.net.InetAddress;
import java.net.SocketException;
import java.util.ArrayList;
import java.util.List;


public class ApplicationClient {

    public void readFile(String filename, String[] args)
    {
        if (args.length != 1) {
            System.out.println("Usage: java QuoteClient <hostname>");
            return;
        }
        StringBuilder records = new StringBuilder();
        try
        {
            BufferedReader reader = new BufferedReader(new FileReader(filename));
            String line;
            while ((line = reader.readLine()) != null)
            {
                records.append(line).toString();
                //System.out.println("lol");
            }
            System.out.println(records);
            reader.close();


            TransportClient transportClient = new TransportClient();
            transportClient.MessageFragmenteEnBytes(records.toString());

            //Preparation de transmission vers le serveur
            DatagramSocket socket = new DatagramSocket();
            byte[] buf = records.toString().getBytes();
            InetAddress address = InetAddress.getByName(args[0]);
            DatagramPacket packet = new DatagramPacket(buf, buf.length, address, 25500);
            socket.send(packet);

            return;
        }
        catch (Exception e)
        {
            System.err.format("Exception occurred trying to read '%s'.", filename);
            e.printStackTrace();
            return;
        }
    }

}
