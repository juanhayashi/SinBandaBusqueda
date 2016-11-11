package com.tokenautocomplete;

import java.io.Serializable;


public class Data implements Serializable{
    private String llave;
    private String valor;

    public Data(String n, String e) {
        llave = n;
        valor = e;
    }

    public String getLlave() { return llave; }
    public String getValor() { return valor; }

    @Override
    public String toString() { return valor; }
}
