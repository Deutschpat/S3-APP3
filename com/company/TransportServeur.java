package com.company;

public class TransportServeur {

    public String accuseReception(){
        String header = "paquet recu";
        byte[] b = new byte[20];
        String bs = b.toString();
        return header += bs;
    }
}
