package com.company;

import java.net.DatagramPacket;
import java.net.DatagramSocket;
import java.net.InetAddress;
import java.util.zip.CRC32;
import java.util.zip.Checksum;

public class LiaisonServeur {



    /**
     * Ajoute le checkSum dans le paquet Sortant
     * @param paqEntre
     * @return
     */
    public String populerPaq(String paqEntre)
    {
        String paqSortie = paqEntre + checkSum(paqEntre.getBytes()) ;
        return paqSortie;
    }

    /**
     * Envoie à la couche transport
     * @param paqEntre
     * @return
     */
    public boolean recuPaq(String paqEntre){
        String CRC32 = paqEntre.substring((paqEntre.length()-10), paqEntre.length());

        String donnes = paqEntre.substring(0, paqEntre.length()-10);
        if(CRCcomparaison(donnes,CRC32)){
            return true;
        }
        return false;
    }


    /**
     * Envoi le paquet au serveur
     * @param paqSortie
     */
    public void envoyerPaquetServeur(String paqSortie, String adresseString){

        try{
            DatagramSocket socket = new DatagramSocket();
            InetAddress address = InetAddress.getByName(adresseString);

            //Ajoute l'entête au paquet
            paqSortie = populerPaq(paqSortie);

            //Transformation en bytes
            byte[] buf =  paqSortie.getBytes();

            // Envoi du paquet
            DatagramPacket packet = new DatagramPacket(buf, buf.length, address, 25500);
            socket.send(packet);
            socket.close();
        }catch (Exception e){
            e.printStackTrace();
        }
    }



    /**
     * Calcule le checkSum
     * @param bytes
     * @return
     */
    public long checkSum(byte[] bytes) {
        Checksum checksum = new CRC32();
        checksum.update(bytes, 0, bytes.length);
        return checksum.getValue();
    }

    public Boolean CRCcomparaison(String data, String clientCRC32) {
        if (clientCRC32.equals(String.valueOf(checkSum(data.getBytes())))) {
            return true;
        }
        return false;
    }


}
