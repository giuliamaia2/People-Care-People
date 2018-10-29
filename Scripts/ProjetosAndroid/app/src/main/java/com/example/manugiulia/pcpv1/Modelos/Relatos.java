package com.example.manugiulia.pcpv1.Modelos;

public class Relatos {
    private String Autor, Data, Hora, Texto, Imagem;

    public Relatos(){}

    public Relatos(String autor, String data, String hora, String texto, String imagem) {
        Autor = autor;
        Data = data;
        Hora = hora;
        Texto = texto;
        Imagem = imagem;
    }

    public Relatos(String data, String hora, String texto, String imagem) {
        Data = data;
        Hora = hora;
        Texto = texto;
        Imagem = imagem;
    }

    public String getAutor() {
        return Autor;
    }

    public void setAutor(String autor) {
        Autor = autor;
    }

    public String getImagem() {
        return Imagem;
    }

    public void setImagem(String imagem) {
        Imagem = imagem;
    }

    public String getData() {
        return Data;
    }

    public void setData(String data) {
        Data = data;
    }

    public String getHora() {
        return Hora;
    }

    public void setHora(String hora) {
        Hora = hora;
    }

    public String getTexto() {
        return Texto;
    }

    public void setTexto(String texto) {
        Texto = texto;
    }
}
