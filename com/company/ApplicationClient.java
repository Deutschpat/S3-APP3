package com.company;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileReader;
import java.io.IOException;
import java.net.DatagramPacket;
import java.net.DatagramSocket;
import java.net.SocketException;
import java.util.ArrayList;
import java.util.List;


public class ApplicationClient {

    public void reception(DatagramPacket packet) throws IOException {
        byte[] buf = new byte[256];
        DatagramSocket socket = new DatagramSocket();
        TransportClient t = new TransportClient();

        packet = new DatagramPacket(buf, buf.length);
        socket.receive(packet);
        String pack = packet.toString();
        readFile(pack);
        File f = new File(pack);
        t.transport(pack, f);
    }
    /**
     * Open and read a file, and return the lines in the file as a list
     * of Strings.
     * (Demonstrates Java FileReader, BufferedReader, and Java5.)
     */
    private String readFile(String filename)
    {
        String records = new String();
        try
        {
            BufferedReader reader = new BufferedReader(new FileReader(filename));
            String line;
            while ((line = reader.readLine()) != null)
            {
                records.concat(line);
            }
            reader.close();
            return records;
        }
        catch (Exception e)
        {
            System.err.format("Exception occurred trying to read '%s'.", filename);
            e.printStackTrace();
            return null;
        }
    }

    public String nomFile(File filename){
        return filename.getName();
    }
}
