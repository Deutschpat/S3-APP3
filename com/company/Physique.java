package com.company;

import java.io.IOException;
import java.net.DatagramPacket;
import java.net.DatagramSocket;
import java.net.InetAddress;
import java.net.SocketException;

public class Physique {

        private LiaisonClient liaison;
        private  DatagramSocket monSocket;


        public Physique(boolean socket){
            if(socket)
            {
                try {
                    monSocket = new DatagramSocket(25500);
                } catch (SocketException e) {
                    e.printStackTrace();
                }
            }
            else
            {
                try {
                    monSocket = new DatagramSocket();
                } catch (SocketException e) {
                    e.printStackTrace();
                }
            }

        }


        public void lierCoucheLiaison(LiaisonClient liaison){
            this.liaison = liaison;
        }

        public void EnvoiServeur(String paquet, String adresse){
            try{
                InetAddress address = InetAddress.getByName(adresse);
                //Transformation en bytes
                byte[] buf =  paquet.getBytes();

                // Envoi du paquet
                DatagramPacket packet = new DatagramPacket(buf, buf.length, address, 25500);
                monSocket.send(packet);

                //receptionMessage();

            }catch (Exception e){
                e.printStackTrace();

            }

        }

        public void receptionMessage(){
            byte[] buf = new byte[256];
            DatagramPacket packetReception = new DatagramPacket(buf, buf.length);
            try {
                monSocket.receive(packetReception);
            } catch (IOException e) {
                e.printStackTrace();

            }

            liaison.receptionPaquet(new String(packetReception.getData(), 0 , packetReception.getLength()));

        }


}
