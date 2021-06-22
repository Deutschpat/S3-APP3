package com.company;

import java.io.IOException;
import java.net.SocketException;
import java.util.zip.CRC32;
import java.util.zip.Checksum;


public class LiaisonServeurClient {


    private String envoyerPaquet;
    private String answer;
    private TransportClient transportClient;
    private TransportServeur transportServeur = new TransportServeur();
    private Physique physique = new Physique(false);
    private boolean connexionState;

    public LiaisonServeurClient() {
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

    public void receptionPaquet(String recuPaquet) {
        String crc32 = recuPaquet.substring((recuPaquet.length() - 10), recuPaquet.length());
        System.out.println(crc32);
        String message = recuPaquet.substring(0, recuPaquet.length() - 10);
        System.out.println(message);
        if (CRCComparaison(message, crc32)) {
            System.out.println("LOL");
            transportServeur.getLiaisonCLient(recuPaquet);

        } else {
            //transportServeur.demandeRenvoie(recuPaquet);
        }
    }

    /**
     * Elle permet de rajouter le checksum a la fin de chaque paquet
     * @param recuPaquet Il s'agit de chaque paquet
     * @return Le paquet avec le checksum en plus
     */
    public String remplirPaquet(String recuPaquet) {
        envoyerPaquet = recuPaquet + getCRC32Checksum(recuPaquet.getBytes());
        return envoyerPaquet;
    }

    /**
     * Elle permet d'envoyer les paquets vers la couche liaison du serveur via la couche physique
     * @param message Il s'agit des messages avec leur entete respective
     * @param address L'adress de l'ordinateur
     * @param nbpaquet Le nombre de paquet
     * @throws SocketException
     */
    public void envoieVersLiaisonServeur(String message,String[] address,int nbpaquet) throws IOException {
        envoyerPaquet = remplirPaquet(message);
        //System.out.println(envoyerPaquet);
        physique.EnvoiServeur(envoyerPaquet,address[0]);
        QuoteServerThread.log("This is a test");

            //TODO Envoie ack et Confirmation ack


        //TODO Il manque le log
    }

    /**
     * Obtenir le checksum de chaque paquet
     * @param bytes
     * @return
     */
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

    public void answerClient(String paquet) {
        answer = paquet;
    }


    public String getAnswerClient() {
        if (connexionState) {
            return answer;
        }
        return null;
    }

    public void endConnection() {
        connexionState = false;
    }

    public boolean getConnectionState() {
        return connexionState;
    }
}