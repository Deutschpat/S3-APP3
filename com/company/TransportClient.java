package com.company;

import java.io.File;
import java.io.IOException;
import java.nio.ByteBuffer;
import java.nio.CharBuffer;
import java.util.*;
import java.lang.*;

import static java.util.Arrays.copyOfRange;


public class TransportClient<EnTete, EnTete2> {

    byte[] EnTete = null;
    //LiaisonClient lc = new LiaisonClient();

/*    public void creationEntete(String filename)
    {
        EnTete = new byte[filename.getBytes().length];


    }*/

/*    //Sert à faire la fonction du CRC32, return un long
    long EnTeteReussiOuPas = lc.getCRC32Checksum(EnTete);
    //Transform le long precedent en bytes
    byte[] bytes = ByteBuffer.allocate(Long.SIZE / Byte.SIZE).putLong(EnTeteReussiOuPas).array();*/

/*    //On met la reponse du CRC32 avec l'en-tete deja fait
    byte[] EnTete2 = new byte[EnTete.length + bytes.length];
    System.arraycopy(EnTete, 0, EnTete2, 0, EnTete.length);
    System.arraycopy(bytes, 0, EnTete2, EnTete.length, bytes.length);*/





    File f;

    String nonRepertoire = null;

    public void transport(String records, String fichier) throws IOException {
        LiaisonClient l = new LiaisonClient();
        byte[] bytes = records.getBytes();
        String b = bytes.toString();
        //String trame = divideArray(bytes,200);
        String[] mfeb =MessageFragmenteEnBytes(records);

/*        //1.
        File fi = new File(fichier);
        String name = fi.getName();
        String fichierAvecNom = cat(name, fichier);


        //2.
        String heyString = trame.toString();
        String trame2 = EnTeteLisible(heyString);
        String FichierNomEntete = cat(trame2, fichierAvecNom);

        l.nextpaquetS(FichierNomEntete);*/
    }

    public int CalculerNombrePackets(byte[] bytes){
        int longeurMessage = bytes.length;
        int nbPackets = (int) Math.ceil(longeurMessage/200.0);
        return nbPackets;
    }
    public String[] MessageFragmenteEnBytes(String message){
        int cnp = CalculerNombrePackets(message.getBytes());
        String List[] = new String[cnp];
        int debut = 0;
        //int fin = 199;
        for(int i=0; i==cnp-1; i++){
            if(i==cnp-1){
                debut = (i)*200;
                List[i] = new String(message.getBytes(), debut, message.getBytes().length%200);
                System.out.println(debut);
                System.out.println(i);
                System.out.println(List[i]);
            }
            else{
                debut = (i)*200;
                List[i] = new String(message.getBytes(), debut, 200 );
                System.out.println(debut);
                System.out.println(i);
                System.out.println(List[i]);
            }

        }

        //List[cnp].trim();
        return List;
    }

    public static String divideArray(byte[] source, int chunksize) {
        byte[][] ret = new byte[(int) Math.ceil(source.length / (double) chunksize)][chunksize];

        int start = 0;
        int parts = 0;

        for (int i = 0; i < ret.length; i++) {
            if (start + chunksize > source.length) {
                System.arraycopy(source, start, ret[i], 0, source.length - start);
            } else {
                System.arraycopy(source, start, ret[i], 0, chunksize);
            }
            start += chunksize;
            parts++;
        }
        return ret.toString();
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

    //2.
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

/*    //3.
    public PriorityQueue<String> OrdonnerPaquet(File paquet, int nbPaquets){
        PriorityQueue<String> pQueue = new PriorityQueue<String>();
        int numerotation=0;
        for(int i = 0; i<nbPaquets; i++){
            numerotation+=1;
            pQueue.add(cat2(numerotation, paquet));
        }
        return pQueue;
    }*/


}
