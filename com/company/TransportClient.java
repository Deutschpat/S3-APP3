package com.company;

import java.io.File;
import java.io.IOException;
import java.util.*;


public class TransportClient {

    byte[] EnTete = null;

    public void creationEntete(String filename)
    {
        EnTete = new byte[filename.getBytes().length];


    }

    //Sert à faire la fonction du CRC32, return un long
    long EnTeteReussiOuPas = getCRC32Checksum(EnTete);
    //Transform le long precedent en bytes
    byte[] bytes = ByteBuffer.allocate(Long.SIZE / Byte.SIZE).putLong(EnTeteReussiOuPas).array();

    //On met la reponse du CRC32 avec l'en-tete deja fait
    byte[] EnTete2 = new byte[EnTete.length + bytes.length];
        System.arraycopy(EnTete, 0, EnTete2, 0, EnTete.length);
        System.arraycopy(bytes, 0, EnTete2, EnTete.length, bytes.length);





    File f;

    String nonRepertoire = null;

    public void transport(String records, File fichier) throws IOException {
        LiaisonClient l = new LiaisonClient();
        byte[] bytes = records.getBytes();
        byte[][] hey = divideArray(bytes,200);

        //1.
        fichier.getName();

        //2.
        String hey2 = EnTeteLisible(hey);

        l.nextpaquetS(hey2);
    }

    public static byte[][] divideArray(byte[] source, int chunksize) {


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
        return ret;
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
    public String EnTeteLisible(byte[][] paquet){
        String enTete = cat("Are you not entertained?!?",paquet);
                return enTete;
    }

    public String cat(String a, byte[][] b) {
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
