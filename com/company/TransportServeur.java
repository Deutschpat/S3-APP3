package com.company;

public class TransportServeur {

    private int lastPaquet;
    private int nbrdemande;
    private LiaisonClient liaisonClient;
    private String paquetList[];

    public TransportServeur()
    {
      lastPaquet = 0;
      nbrdemande = 1;
    }

    public void ConnectionVersLiaison(LiaisonClient liaisonClient)
    {
      this.liaisonClient = liaisonClient;
    }

    public void endConnection()
    {
        liaisonClient.endConnection();
    }

    public void demandeRenvoie(String paquetManquant)
    {
        String askAgain = paquetManquant.substring(0,11) + "1" + "Renvoyer le paquet";
        //TODO Demander pour compr/hension
        liaisonClient.answerClient(askAgain);
    }

    public void accuserReception(String paquetAccepted)
    {
        if(paquetList[Integer.parseInt(paquetAccepted.substring(0,4))-1] == null)
            paquetList[Integer.parseInt(paquetAccepted.substring(0,4)) - 1] = paquetAccepted;
            String ack = paquetAccepted.substring(0,11) + "0" + "Package received";
            liaisonClient.answerClient(ack);
    }

    public void TraitementPaquetEnvoyer(String paquet)
    {
        int actualPaquet = Integer.parseInt(paquet.substring(0,4));
        if((actualPaquet - lastPaquet) == 1)
        {
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
        paquetList = new String[Integer.parseInt(paquet.substring(4,8))];
        if(Integer.parseInt(paquet.substring(11,12)) == 0)
        {
            TraitementPaquetEnvoyer(paquet);
        }
        else
        {
            TraitementPaquetRenvoyer(paquet);
        }
    }




}
