package com.company;

import java.net.DatagramPacket;
import java.net.DatagramSocket;
import java.net.InetAddress;
import java.net.SocketException;
import java.util.zip.CRC32;
import java.util.zip.Checksum;


public class LiaisonClient {


    private String envoyerPaquet;
    private String answer;
    private TransportClient transportClient;
    private TransportServeur transportServeur;
    private Physique physique;
    private boolean connexionState;

    public LiaisonClient() {
        connexionState = true;
    }

    public void ConnectionVersTransportClient(TransportClient transportClient) {
        this.transportClient = transportClient;
    }

    public void ConnectionVersPhysique(Physique physique)
    {
        this.physique = physique;
    }

    public void ConnectionVersTransportServeur(TransportServeur transportServeur) {
        this.transportServeur = transportServeur;
    }


    public String remplirPaquet(String recuPaquet) {
        envoyerPaquet = recuPaquet + getCRC32Checksum(recuPaquet.getBytes());
        System.out.println(envoyerPaquet);
        return envoyerPaquet;
    }

    public void receptionPaquet(String recuPaquet) {
        String crc32 = recuPaquet.substring((recuPaquet.length() - 10), recuPaquet.length());
        String message = recuPaquet.substring(0, recuPaquet.length() - 10);
        if (CRCComparaison(message, crc32)) {
            transportServeur.getLiaisonCLient(recuPaquet);

        } else {
            transportServeur.demandeRenvoie(recuPaquet);
        }
    }

    public void reponseATransport(String message) {
        message.substring(0, message.length() - 10);

    }

    public void envoieVersLiaisonServeur(String message) throws SocketException {
        envoyerPaquet = remplirPaquet(message);
        System.out.println(envoyerPaquet);



       // physique.EnvoiServeur(envoyerPaquet, adresse);
        //TODO Il manque le log
    }

    public void answerClient(String paquet) {
        answer = paquet;
    }

    public String getAnswerClient() {
        if (connexionState) {
            return answer;
        }
        return null;
    }


    public static long getCRC32Checksum(byte[] bytes) {
        Checksum crc32 = new CRC32();
        crc32.update(bytes, 0, bytes.length);
        return crc32.getValue();
    }

    public boolean CRCComparaison(String donneesRecu, String crcRecu) {
        if (crcRecu.equals(getCRC32Checksum(donneesRecu.getBytes()))) {
            return true;
        }
        return false;
    }

    public void endConnection() {
        connexionState = false;
    }

    public boolean getConnectionState() {
        return connexionState;
    }
}