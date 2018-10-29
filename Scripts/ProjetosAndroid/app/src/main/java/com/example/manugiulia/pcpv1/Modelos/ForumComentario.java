package com.example.manugiulia.pcpv1.Modelos;

public class ForumComentario {

    private String Autor,Data,Texto;

    public ForumComentario(){}

    public ForumComentario(String autor, String data, String texto) {
        Autor = autor;
        Data = data;
        Texto = texto;
    }

    public String getAutor() {
        return Autor;
    }

    public void setAutor(String autor) {
        Autor = autor;
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
