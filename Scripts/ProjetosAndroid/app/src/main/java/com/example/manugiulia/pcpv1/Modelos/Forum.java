package com.example.manugiulia.pcpv1.Modelos;

public class Forum {
    private String Autor, Data,Texto, Titulo;
    public Forum(){}

    public Forum(String autor, String data, String texto, String titulo) {
        Autor = autor;
        Data = data;
        Texto = texto;
        Titulo = titulo;
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

    public String getTitulo() {
        return Titulo;
    }

    public void setTitulo(String titulo) {
        Titulo = titulo;
    }
}
