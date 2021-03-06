package com.company;

import java.io.IOException;
import java.lang.*;
import java.net.SocketException;

public class TransportClient {

    private LiaisonClient liaisonClient;
    private String[] paquetEntete;

    /**
     * @param liaisonClient
     * @return
     */
    public void ConnectionVersLiaison(LiaisonClient liaisonClient) {
        this.liaisonClient = new LiaisonClient();
    }

    /**
     * @param message
     * @param nomfichier
     * @param
     * @return
     */
    public void envoieVersLiaisonClient(String[] message, String nomfichier, int nbpaquet,String[] address) throws IOException {
        LiaisonClient li = new LiaisonClient();
        for(int i = 0; i < message.length;i++)
        {
            //System.out.println(i);
            //System.out.println(message[i]);
            li.envoieVersLiaisonServeur(message[i], address,nbpaquet);

        }

    }


    /**
     * Elle permet de calculer le nombre de paquet nécéssaire pour un message d'une certaine taille en bytes(200).
     *
     * @param bytes La taille du message(String) en bytes.
     * @return Elle retourne le nombre de packet nécéssaire pour envoyer le message
     */
    public int CalculerNombrePackets(byte[] bytes) {
        int longeurMessage = bytes.length;
        int nbPackets = (int) Math.ceil(longeurMessage / 200.0);
        return nbPackets;
    }

    /**
     * Elle sert à séparer le message d'une certaine taille en bytes en plusieurs paquets de 200 bytes.
     *
     * @param message Le message a fragmenter en paquets
     * @return Le message fragmenter en une liste de string.
     */
    public String[] MessageFragmenteEnBytes(String message) {
        int cnp = CalculerNombrePackets(message.getBytes());
        int debut = 0;
        String List[] = new String[cnp];

        for (int i = 0; i <= cnp - 1; i++) {
            if (i == cnp - 1) {
                debut = (i) * 200;
                List[i] = new String(message.getBytes(), debut, message.getBytes().length % 200);
                //System.out.println(debut);
                //System.out.println(i);
                //System.out.println(List[i]);
            } else {
                debut = (i) * 200;
                List[i] = new String(message.getBytes(), debut, 200);
                //System.out.println(debut);
                //System.out.println(i);
                //System.out.println(List[i]);
            }

        }
        return List;
    }

    /**
     * @param message Il s'agit du message a separer en paquet et creer des entete pour ceux-ci
     * @param nomFichier Le nomFichier correspond a one-liners.txt
     * @return Il va retourner des paquets a envoyer avec l'informations importants et l'en-tete
     */
    public String[] EntetePaquet(String message, String nomFichier,String[] address) throws IOException {


        //Calculer le nombre de paquets nécéssaires
        int cnp = CalculerNombrePackets(message.getBytes());

        //Garder le message a ajouter
        String messageFragmenenter[] = MessageFragmenteEnBytes(message);

        //Espace pour stocker tous les paquets à envoyer avec l'entête
        String messageFragmentListe[] = new String[cnp];

        ///Espace pour stocker le nombre de paquets pour transmettre un fichier
        String nombreDePaquet = "";
        //Attribution de 4 bytes d'espace pour afficher le nombre de paquet nécéssaire pour le message
        if (4 - String.valueOf(cnp).length() != 0) {              //TODO Faire une fonction pour ça
            String espaceEnteteNombre = "%0" + (4) + "d";
            nombreDePaquet = String.format(espaceEnteteNombre, cnp);
        } else {
            nombreDePaquet = String.valueOf(cnp);
        }

        ///Espace pour stocker la longueur pour transmettre un fichier
        String LongueurFichier = "";
        //Attribution de 4 bytes d'espace pour afficher
        if (3 - String.valueOf(nomFichier.getBytes().length).length() != 0) {
            String espaceEnteteLongueur = "%0" + (3) + "d";
            LongueurFichier = String.format(espaceEnteteLongueur, 200);
        }
        else
        {
            LongueurFichier = String.valueOf(nomFichier.getBytes().length);
        }

        //Completer l'entete du premier paquet avec le nom du fichier
        messageFragmentListe[0] = "0001" + nombreDePaquet + LongueurFichier + "$" + messageFragmenenter[0];

        ///Espace pour stocker et attribuer le numero actuel des paquets pour transmettre un fichier
        for (int i = 1; i < cnp; i++) {
            String numeroDuPaquet = "0000";
            if (4 - String.valueOf(i).length() != 0) {
                String espaceEnteteNumero = "%0" + (4) + "d";
                numeroDuPaquet = String.format(espaceEnteteNumero, i + 1);
                //System.out.println(numeroDuPaquet);
            }
            else
            {
                numeroDuPaquet = String.valueOf(i);
            }

            //La longueur du dernier paquet
            String espaceEnteteLongueurDernier;
            /*System.out.println(i);
            System.out.println(cnp-1);*/
            if (cnp - 1 == i) {

                if (3 - String.valueOf(messageFragmenenter[i].getBytes().length).length() != 0)
                {
                    String espaceEnteteTaille = "%0" + (3) + "d";
                    //System.out.println(messageFragmenenter[i].getBytes().length);
                    //System.out.println(i);

                    espaceEnteteLongueurDernier = String.format(espaceEnteteTaille, messageFragmenenter[i].getBytes().length);
                    //System.out.println(espaceEnteteLongueurDernier);
                }
                else
                {
                    espaceEnteteLongueurDernier = String.valueOf(messageFragmenenter[i - 1].getBytes().length);

                }
            }
            else
            {
                espaceEnteteLongueurDernier = "200";
            }

            messageFragmentListe[i] = numeroDuPaquet + nombreDePaquet + espaceEnteteLongueurDernier + "$" + messageFragmenenter[i];
            //System.out.println(messageFragmentListe[i]);
        }

        //System.out.printf(messageFragmentListe[0]);
        envoieVersLiaisonClient(messageFragmentListe,nomFichier,cnp,address);
        return messageFragmentListe;
    }


}