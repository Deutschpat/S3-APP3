package com.company;

import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.OutputStream;
import java.math.BigInteger;
import java.nio.ByteBuffer;
import java.sql.Timestamp;
import java.util.zip.CRC32;
import java.util.zip.Checksum;
import java.lang.Long;
import java.net.*;

public class LiaisonClient {
    String test = "test";
    byte[] bytes = test.getBytes();
    private static OutputStream BackLog;
    private static File log;

    public LiaisonClient(){};


    public void nextpaquetS(String paquet) throws IOException {

        //Sert a avoir la longueur en string du paquet
       // String longueur = BigInteger.valueOf(paquet.length()).toString();             //Changer la ligne
        backlog("Reception du packet.");

        //Sert à mettre le paquet original avec sa longueur pour creer un en-tete
       // String EnTete = cat(longueur, paquet);

        //Sert à faire la fonction du CRC32, return un long
        long EnTeteReussiOuPas = getCRC32Checksum(paquet.getBytes());
        //Transform le long precedent en String
        String bytes = String.valueOf(EnTeteReussiOuPas);

        //On met la reponse du CRC32 avec l'en-tete deja fait
        String EnTete2 = cat(paquet,bytes);

        //On l'envoie à qui?
    }



    public static long getCRC32Checksum(byte[] bytes) {
        Checksum crc32 = new CRC32();
        crc32.update(bytes, 0, bytes.length);
        return crc32.getValue();
    }

    private synchronized static void backlog(String msg) throws IOException {
        log = new File("liaisonDeDonnees.log");
        BackLog = new FileOutputStream(log,true);

        BackLog.write(("[" +
                new Timestamp(System.currentTimeMillis()).toString().substring(11,21) +
                "] [Serveur] : ").getBytes());
        BackLog.write(msg.trim().getBytes());
        BackLog.write(10);
   }


    String cat(String a, String b) {
        a += b;
        return a;
    }




//  private synchronized static void backlog(String msg) throws IOException {
//      // Crée un time stamp assez clean
//       osLog.write(("[" + new Timestamp(System.currentTimeMillis()).toString().substring(11,21) + "] [Serveur] : ").getBytes());
//       osLog.write(msg.trim().getBytes());
//       // Change de ligne
//       osLog.write(10);
//   }
}
