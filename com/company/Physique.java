package com.company;

import java.io.IOException;
import java.net.DatagramPacket;
import java.net.DatagramSocket;
import java.net.InetAddress;
import java.net.SocketException;

public class Physique {

        private LiaisonServeurClient liaisonServeurClient;
        private  DatagramSocket monSocket;
        private boolean socket = true;
        private String address;


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


        public void lierCoucheLiaison(LiaisonServeurClient liaison){
            this.liaisonServeurClient = liaison;
        }

        public void EnvoiServeur(String paquet, String addresse){
            try{
                System.out.println(paquet);
                address = addresse;

                //Obtenir l'adresse pour l'envoi
                InetAddress address = InetAddress.getByName(addresse);
                //Transformer en bits pour preparer l'envoi
                byte[] buf =  paquet.getBytes();
                //Lancement du paquet
                DatagramPacket packet = new DatagramPacket(buf, buf.length, address, 25500);
                monSocket.send(packet);




                //receptionMessage();

            }catch (Exception e){
                e.printStackTrace();

            }

        }

        /*public void receptionMessage(){
            byte[] buf = new byte[256];
            DatagramPacket packetReception = new DatagramPacket(buf, buf.length);
            try {

                monSocket.receive(packetReception);


            } catch (IOException e) {
                e.printStackTrace();

            }


            liaisonServeurClient.receptionPaquet(new String(packetReception.getData(), 0 , packetReception.getLength()));

        }

*/
}
