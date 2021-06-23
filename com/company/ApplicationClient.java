package com.company;

import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;


public class ApplicationClient {

    private TransportClient transportClient = new TransportClient();
    private String nomFichier;
    private String[] args;


    public ApplicationClient(String nomFichier,String[] args) throws IOException {
        this.nomFichier = nomFichier;
        this.args = args;
        envoieVersTransportClient(nomFichier,args);
    }

    /**
     * Elle permet la connection de la couche d'application à la couche de transport. Par contre je sais pas si elle est vraiment utile.
     * @param transportClient
     */
    public void ConnectionVersTransport(TransportClient transportClient)
    {
        this.transportClient = transportClient;
    }

    /**
     * Elle permet d'envoyer à la couche de transport du client le fichier lu sous la forme de String
     * @param nomfichier il s'agit du nom du fichier à lire dans notre cas le one-liners.txt
     * @param args
     */
    public void envoieVersTransportClient(String nomfichier, String[] args) throws IOException {
        String envoie = readFile(nomfichier,args);
        QuoteServerThread.log("Fichier lu");
        ConnectionVersTransport(new TransportClient());
        QuoteServerThread.log("Connection etabli");
        transportClient.EntetePaquet(envoie,nomfichier,args);
        QuoteServerThread.log("Envoie vers TransportClient");
    }

    /**
     * Elle permet de lire le fichier one-liners.txt et de pouvoir le transformer en String et permettre l'envoi.
     * @param filename Il s'agit du nom du fichier. Dans notre cas le one-liners.txt
     * @param args
     * @return Elle retourne un String des phrases lues
     */
    public String readFile(String filename, String[] args) throws IOException {
        if (args.length != 1) {
            System.out.println("Usage: java QuoteClient <hostname>");
            return "Erreur";
        }

        StringBuilder records = new StringBuilder();
        try
        {
            BufferedReader reader = new BufferedReader(new FileReader(filename));
            String line;
            while ((line = reader.readLine()) != null)
            {
                records.append(line).toString();
            }
            reader.close();

            return records.toString();
        }
        catch (Exception e)
        {
            System.err.format("Exception occurred trying to read '%s'.", filename);
            e.printStackTrace();
            return "Erreur";
        }


    }

    public String getNomFichier() {
        return nomFichier;
    }
    public String[] getArgs() {
        return args;
    }


}
