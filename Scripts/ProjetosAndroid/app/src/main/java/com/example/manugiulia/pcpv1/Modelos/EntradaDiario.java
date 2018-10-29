package com.example.manugiulia.pcpv1.Modelos;

public class EntradaDiario {
    private String Data,Texto;

    public EntradaDiario(){}

    public EntradaDiario(String data, String texto) {
        Data = data;
        Texto = texto;
    }

    public String getData() {
        return Data;
    }

    public void setData(String data) {
        Data = data;
    }

    public String getTexto() {
        return Texto;
    }

    public void setTexto(String texto) {
        Texto = texto;
    }
}
