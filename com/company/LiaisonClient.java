package com.company;

import java.io.IOException;
import java.io.OutputStream;
import java.math.BigInteger;
import java.nio.ByteBuffer;
import java.sql.Timestamp;
import java.util.zip.CRC32;
import java.util.zip.Checksum;
import java.net.*;

public class LiaisonClient {
    String test = "test";
    byte[] bytes = test.getBytes();
    private static OutputStream osBackLog;


    public void nextpaquet(byte[] paquet){

        //Sert a avoir la longueur en bytes du paquet
        byte[] longueur = BigInteger.valueOf(paquet.length).toByteArray();             //À changer la ligne

        //Sert à mettre le paquet original avec sa longueur pour creer un en-tete
        byte[] EnTete = new byte[longueur.length + paquet.length];
        System.arraycopy(longueur, 0, EnTete, 0, longueur.length);
        System.arraycopy(paquet, 0, EnTete, longueur.length, paquet.length);

        //Sert à faire la fonction du CRC32, return un long
        long EnTeteReussiOuPas = getCRC32Checksum(EnTete);
        //Transform le long precedent en bytes
        byte[] bytes = ByteBuffer.allocate(Long.SIZE / Byte.SIZE).putLong(EnTeteReussiOuPas).array();

        //On met la reponse du CRC32 avec l'en-tete deja fait
        byte[] EnTete2 = new byte[EnTete.length + bytes.length];
        System.arraycopy(EnTete, 0, EnTete2, 0, EnTete.length);
        System.arraycopy(bytes, 0, EnTete2, EnTete.length, bytes.length);

    }

    public static long getCRC32Checksum(byte[] bytes) {
        Checksum crc32 = new CRC32();
        crc32.update(bytes, 0, bytes.length);
        return crc32.getValue();
    }

    private synchronized static void backlog(String msg) throws IOException {
        osBackLog.write(("[" +
                new Timestamp(System.currentTimeMillis()).toString().substring(11,21) +
                "] [Serveur] : ").getBytes());
        osBackLog.write(msg.trim().getBytes());
        osBackLog.write(10);
   }







//  private synchronized static void backlog(String msg) throws IOException {
//      // Crée un time stamp assez clean
//       osLog.write(("[" + new Timestamp(System.currentTimeMillis()).toString().substring(11,21) + "] [Serveur] : ").getBytes());
//       osLog.write(msg.trim().getBytes());
//       // Change de ligne
//       osLog.write(10);
//   }
}
