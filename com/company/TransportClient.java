package com.company;

import java.io.File;
import java.util.*;


public class TransportClient {
    File f;


    //1.
    public String nomFichier(File fichier, int numPaquet){
        if(numPaquet==1){
            f = new File(fichier.getName());
            f.toString().trim();
            return String.valueOf(f);
        }
        return String.valueOf(f);
    }

    //2.
    public String EnTeteLisible(String paquet){
        String enTete = cat("Are you not entertained?!?",paquet);
                return enTete;
    }

    public String cat(String a, String b) {
        a += b;
        return a;
    }

    public String cat2(int a, File b){
        return String.valueOf(a) + "A" + b.toString(); //La lettre "A" sera utilisé pour séparer la numérotation du reste du paquet
    }

    //3.
    public PriorityQueue<String> OrdonnerPaquet(File paquet, int nbPaquets){
        PriorityQueue<String> pQueue = new PriorityQueue<String>();
        int numerotation=0;
        for(int i = 0; i<nbPaquets; i++){
            numerotation+=1;
            pQueue.add(cat2(numerotation, paquet));
        }
        return pQueue;
    }


}
