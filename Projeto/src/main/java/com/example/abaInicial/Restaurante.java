package com.example.abaInicial;

public class Restaurante {
    private int id;
    private String nome;
    private String tipoCulinaria;
    private int numAvaliacoes;
    private double totalAvaliacoes;
    private double mediaAvaliacao;

    public Restaurante(int id, String nome, String tipoCulinaria, int numAvaliacoes, double totalAvaliacoes, double mediaAvaliacao) {
        this.id = id;
        this.nome = nome;
        this.tipoCulinaria = tipoCulinaria;
        this.numAvaliacoes = numAvaliacoes;
        this.totalAvaliacoes = totalAvaliacoes;
        this.mediaAvaliacao = mediaAvaliacao;
    }
    public Restaurante(){
        
    }    

    public int getId() {
        return id;
    }
    public void setId(int id) {
        this.id = id;
    }
    public String getNome() {
        return nome;
    }
    public void setNome(String nome) {
        this.nome = nome;
    }
    public String getTipoCulinaria() {
        return tipoCulinaria;
    }
    public void setTipoCulinaria(String tipoCulinaria) {
        this.tipoCulinaria = tipoCulinaria;
    }
    public int getNumAvaliacoes() {
        return numAvaliacoes;
    }
    public void setNumAvaliacoes(int numAvaliacoes) {
        this.numAvaliacoes = numAvaliacoes;
    }
    public double getTotalAvaliacoes() {
        return totalAvaliacoes;
    }
    public void setTotalAvaliacoes(double totalAvaliacoes) {
        this.totalAvaliacoes = totalAvaliacoes;
    }
    public double getMediaAvaliacao() {
        return mediaAvaliacao;
    }
    public void setMediaAvaliacao(double mediaAvaliacao) {
        this.mediaAvaliacao = mediaAvaliacao;
    }
    
}
