package com.company;


import java.io.*;
import java.net.*;
import java.security.*;
import java.util.*;
import java.security.cert.CertPath;
import java.sql.Timestamp;

public class QuoteServerThread extends Thread {

    protected DatagramSocket socket = null;
    protected BufferedReader in = null;
    protected boolean moreQuotes = true;
    private static File log;
    private static FileOutputStream osLog;

    public QuoteServerThread() throws IOException {
        this("QuoteServerThread");
    }



    public QuoteServerThread(String name) throws IOException {
        super(name);
        socket = new DatagramSocket(25500);

        log = new File("C:\\Users\\Telep\\Documents\\S3\\APP3\\src\\com\\company\\S3-APP3\\company\\liaisonDeDonnees.log");
        osLog = new FileOutputStream(log,true);
    }

    public void run() {


            try {
                byte[] dataReceived = new byte[256];

                // receive request
                DatagramPacket packet = new DatagramPacket(dataReceived, dataReceived.length);
                socket.receive(packet);

                //Adapter le packet en String
                String received = new String(packet.getData(), 0, packet.getLength());
                System.out.println(received);
                // figure out response
                String dString = null;
                if (in == null)
                    dString = new Date().toString();
                else
                    //dString = getNextQuote();

                dataReceived = dString.getBytes();

                // send the response to the client at "address" and "port"
                InetAddress address = packet.getAddress();
                int port = packet.getPort();
                packet = new DatagramPacket(dataReceived, dataReceived.length, address, port);
                socket.send(packet);
            } catch (IOException e) {
                e.printStackTrace();
                moreQuotes = false;
            }

        socket.close();
    }

/*    protected String getNextQuote() {
        String returnValue = null;
        try {
            if ((returnValue = in.readLine()) == null) {
                in.close();
                moreQuotes = false;
                returnValue = "No more quotes. Goodbye.";
            }
        } catch (IOException e) {
            returnValue = "IOException occurred in server.";
        }
        return returnValue;
    }*/
public synchronized static void log(String msg) throws IOException {

    File fold=new File("C:\\Users\\Telep\\Documents\\S3\\APP3\\src\\com\\company\\S3-APP3\\company\\liaisonDeDonnees2.log");
    fold.delete();
    File fnew=new File("C:\\Users\\Telep\\Documents\\S3\\APP3\\src\\com\\company\\S3-APP3\\company\\liaisonDeDonnees2.log");

    FileOutputStream oslog = new FileOutputStream("C:\\Users\\Telep\\Documents\\S3\\APP3\\src\\com\\company\\S3-APP3\\company\\liaisonDeDonnees.log");
    oslog.write(("[" + new Timestamp(System.currentTimeMillis()).toString().substring(11,21) + "] [Serveur] : ").getBytes());
    oslog.write(msg.trim().getBytes());
    oslog.write(10);
}
}



