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
        String address;

    /**
     * Creation d'une couche physique avec un socket pouvant etre d'envoi ou de reception
     * @param socket l'etat du socket true c'est envoie et false c'est reception
     */
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

    /**
     * Liaison a la couche de liaison.
     * @param liaison
     */
    public void connexionVersLiaison(LiaisonServeurClient liaison)
        {
            this.liaisonServeurClient = liaison;
        }

    /**
     * Il s'agit de la facon d'envoyer la requete vers le serveur
     * @param paquet les paquets d'informations a transmettre
     * @param addresse l'address ou transmettre
     */
    public void EnvoiServeur(String paquet, String addresse){
        try{
            //System.out.println(paquet);
            this.address = addresse;
            //Obtenir l'adresse pour l'envoi
            InetAddress address = InetAddress.getByName(addresse);
            //Transformer en bits pour preparer l'envoi
            byte[] buf =  paquet.getBytes();
            //Lancement du paquet
            DatagramPacket packet = new DatagramPacket(buf, buf.length, address, 25500);
            monSocket.send(packet);
            QuoteServerThread.log("Envoyer au serveur");


        }catch (Exception e){
                e.printStackTrace();
        }

    }
    /**
     * Fonction de reception de message non-fonctionnel
     */
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
