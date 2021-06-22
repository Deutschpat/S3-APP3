package com.company;

import java.lang.*;

public class TransportClient{

    private LiaisonClient liaisonClient;

    /**
     *
     * @param liaisonClient
     * @return
     */
    public void ConnectionVersLiaison(LiaisonClient liaisonClient) {
        this.liaisonClient = liaisonClient;
    }

    /**
     *
     * @param message
     * @param nomfichier
     * @param adresse
     * @return
     */
    public void envoieVersLiaisonClient(String message, String nomfichier, String adresse)
    {
        String[] envoyerPaquet = EntetePaquet(message,nomfichier);
        for(int i = 0; i < envoyerPaquet.length; i++) {
            liaisonClient.envoieVersLiaisonServeur(envoyerPaquet[i],adresse);
        }
    }

    /**
     * Elle permet de calculer le nombre de paquet nécéssaire pour un message d'une certaine taille en bytes(200).
     * @param bytes La taille du message(String) en bytes.
     * @return Elle retourne le nombre de packet nécéssaire pour envoyer le message
     */
    public int CalculerNombrePackets(byte[] bytes){
        int longeurMessage = bytes.length;
        int nbPackets = (int) Math.ceil(longeurMessage/200.0);
        return nbPackets;
    }

    /**
     * Elle sert à séparer le message d'une certaine taille en bytes en plusieurs paquets de 200 bytes.
     * @param message Le message a fragmenter en paquets
     * @return Le message fragmenter en une liste de string.
     */
    public String[] MessageFragmenteEnBytes(String message){
        int cnp = CalculerNombrePackets(message.getBytes());
        int debut = 0;
        String List[] = new String[cnp];

        for(int i=0; i<=cnp-1; i++){
            if(i==cnp-1){
                debut = (i)*200;
                List[i] = new String(message.getBytes(), debut, message.getBytes().length%200);
                //System.out.println(debut);
                //System.out.println(i);
                //System.out.println(List[i]);
            }
            else{
                debut = (i)*200;
                List[i] = new String(message.getBytes(), debut, 200 );
                //System.out.println(debut);
                //System.out.println(i);
                //System.out.println(List[i]);
            }

        }
        return List;
    }

    /**
     *
     * @param message
     * @param nomFichier
     * @return
     */
    public String[] EntetePaquet(String message, String nomFichier) {


        //Calculer le nombre de paquets nécéssaires
        int cnp = CalculerNombrePackets(message.getBytes());

        //Calculer la taille du message avant d'y rajouter l'en-tête
        String messageAvantEntete[] = MessageFragmenteEnBytes(message);

        //Espace pour stocker tous les paquets à envoyer avec l'entête
        String messageFragmentListe[] = new String[cnp];

        //Espace pour stocker le nombre de paquets pour transmettre un fichier
        String nombreDePaquet = "";
        //Attribution de 4 bytes d'espace pour afficher le nombre de paquet nécéssaire pour le message
        if(4 - String.valueOf(cnp).length() != 0){              //TODO Faire une fonction pour ça
            String nombreDeDigits = "%0" + (4) + "d";
            nombreDePaquet = String.format(nombreDeDigits, cnp);

        }else{
            nombreDePaquet = String.valueOf(cnp);

        }


        String enteteLongueurFichier = "";
        //Remplissage de zéros pour la longueur du fichier
        if(3 - String.valueOf(nomFichier.getBytes().length).length() != 0) {
            String nombreDeDigitsFichier = "%0" + (3) + "d";
            enteteLongueurFichier = String.format(nombreDeDigitsFichier, nomFichier.getBytes().length);
        }else{
            enteteLongueurFichier = String.valueOf(nomFichier.getBytes().length);
        }
        //Paquet avec le nom dyu fichier seulement
        messageFragmentListe[0] = "0001" + nombreDePaquet + enteteLongueurFichier + "0" + nomFichier;


        for (int i = 1; i < cnp; i++) {
            //Remplissage de zéros pour le paquet actuel
            String enteteNombrePaquetFragment = "0000";

            if(4 - String.valueOf(i).length() != 0){
                String nombreDeDigitsFragmentActuel = "%0" + (4) + "d";
                enteteNombrePaquetFragment = String.format(nombreDeDigitsFragmentActuel, i +1);
            }
            else{
                enteteNombrePaquetFragment = String.valueOf(i);
            }

            //Remplissage de zéros pour la longueur du dernier paquet
            String enteteLongueurFichierFragment;
            if (cnp - 1 == i){
                if(3 - String.valueOf(messageAvantEntete[i - 1].getBytes().length).length() != 0) {
                    String nombreDeDigitsFragmentTaille = "%0" + (3) + "d";
                    System.out.println(nombreDeDigitsFragmentTaille);
                    System.out.println(nomFichier.getBytes().length);

                    enteteLongueurFichierFragment = String.format(nombreDeDigitsFragmentTaille, messageAvantEntete[i - 1].getBytes().length);
                }
                else{
                    enteteLongueurFichierFragment = String.valueOf(messageAvantEntete[i - 1].getBytes().length);
                }
            }
            else{
                enteteLongueurFichierFragment = "200";
            }

            messageFragmentListe[i] = enteteNombrePaquetFragment + nombreDePaquet + enteteLongueurFichierFragment + "0" + messageAvantEntete[i-1];
        }


        return messageFragmentListe;
    }

    //IMPORTANT  //Preparation de transmission vers le serveur
    //            /*DatagramSocket socket = new DatagramSocket();
    //            byte[] buf = records.toString().getBytes();
    //            InetAddress address = InetAddress.getByName(args[0]);
    //            DatagramPacket packet = new DatagramPacket(buf, buf.length, address, 25500);
    //            socket.send(packet);*/

}

















    //1.
/*    public String nomFichier(File fichier){
        if(fichier[0][0]){
            f = new File(fichier.getName());
            f.toString().trim();
            return String.valueOf(f);
        }
        return String.valueOf(f);
    }*/

   /* //2.
    public String EnTeteLisible(String paquet){
        String enTete = cat("Are you not entertained?!?",paquet);
                return enTete;
    }

    public String cat(String a, String b) {
        a += b.toString();
        return a;
    }

    public String cat2(int a, File b){
        return String.valueOf(a) + "A" + b.toString(); //La lettre "A" sera utilisé pour séparer la numérotation du reste du paquet
    }

*/

