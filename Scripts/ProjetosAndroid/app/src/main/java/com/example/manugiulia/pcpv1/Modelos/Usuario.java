package com.example.manugiulia.pcpv1.Modelos;

public class Usuario {
    private String Nome,Apelido,DataDeNasc,Email,Senha,Imagem;

    public Usuario(String nome, String apelido, String dataDeNasc, String email, String senha, String imagem) {
        Nome = nome;
        Apelido = apelido;
        DataDeNasc = dataDeNasc;
        Email = email;
        Senha = senha;
        Imagem = imagem;
    }

    public Usuario(){}

    public String getImagem() {
        return Imagem;
    }

    public void setImagem(String imagem) {
        Imagem = imagem;
    }

    public String getNome() {
        return Nome;
    }

    public void setNome(String nome) {
        Nome = nome;
    }

    public String getApelido() {
        return Apelido;
    }

    public void setApelido(String apelido) {
        Apelido = apelido;
    }

    public String getDataDeNasc() {
        return DataDeNasc;
    }

    public void setDataDeNasc(String dataDeNasc) {
        DataDeNasc = dataDeNasc;
    }

    public String getEmail() {
        return Email;
    }

    public void setEmail(String email) {
        Email = email;
    }

    public String getSenha() {
        return Senha;
    }

    public void setSenha(String senha) {
        Senha = senha;
    }
}
