package com.company;

import java.io.File;
import java.io.IOException;
import java.util.*;


public class TransportClient {
    File f;


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
