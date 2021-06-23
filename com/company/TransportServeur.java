package com.company;

import java.util.concurrent.CountDownLatch;

public class TransportServeur {

    private int lastPaquet;
    private int nbrdemande;
    private LiaisonServeurClient liaisonServeurClient;
    private String paquetList[];

    public TransportServeur()
    {
      lastPaquet = 0;
      nbrdemande = 1;
    }

    public void ConnectionVersLiaison(LiaisonServeurClient liaisonClient)
    {
      this.liaisonServeurClient = liaisonServeurClient;
    }

    public void endConnection()
    {
        liaisonServeurClient.endConnection();
    }

    public void demandeRenvoie(String paquetManquant)
    {
        String askAgain = paquetManquant.substring(0,11) + "1" + "Renvoyer le paquet";

        liaisonServeurClient.answerClient(askAgain);
    }

    public void accuserReception(String paquetAccepted)
    {
        if(paquetList[Integer.parseInt(paquetAccepted.substring(0,4))-1] == null)
            paquetList[Integer.parseInt(paquetAccepted.substring(0,4)) - 1] = paquetAccepted;
            String ack = paquetAccepted.substring(0,11) + "0" + "Package received";
            liaisonServeurClient.answerClient(ack);
    }

    public void TraitementPaquetEnvoyer(String paquet)
    {

        int actualPaquet = Integer.parseInt(paquet.substring(0,4));
        if((actualPaquet - lastPaquet) == 1)
        {

            //System.out.println(actualPaquet);
            accuserReception(paquet);
            lastPaquet = actualPaquet;
        }
        else
        {
           demandeRenvoie(paquet);
        }
    }

    public void TraitementPaquetRenvoyer(String paquet)
    {
        int actualPaquet = Integer.parseInt(paquet.substring(0,4));
        if(actualPaquet == lastPaquet + 1)
        {
            accuserReception(paquet);
            lastPaquet++;
        }
        else
        {
            demandeRenvoie(paquet);
            if(nbrdemande != 3)
            {
                nbrdemande++;

            }
            else endConnection();
        }

    }

    public void getLiaisonCLient(String paquet)
    {
        System.out.println(paquet);
        if(nbrdemande==1)
        {
            paquetList = new String[Integer.parseInt(paquet.substring(4,8))];
            TraitementPaquetEnvoyer(paquet);
        }
        else
        {
            TraitementPaquetRenvoyer(paquet);
        }
    }




}
