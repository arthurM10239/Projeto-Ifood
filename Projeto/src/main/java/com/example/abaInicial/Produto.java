package com.example.abaInicial;

import javafx.scene.image.ImageView;

public class Produto {
    private String nome;
    private String descricao;
    private double preco;
    private ImageView imagemPath;

    public Produto(String nome, String descricao, double preco, ImageView imagemPath) {
        this.nome = nome;
        this.descricao = descricao;
        this.preco = preco;
        this.imagemPath = imagemPath;
    }
    public Produto(String nome, String descricao, double preco) {
        this.nome = nome;
        this.descricao = descricao;
        this.preco = preco;
    }
    public String getNome() {
        return nome;
    }
    public void setNome(String nome) {
        this.nome = nome;
    }
    public String getDescricao() {
        return "Descrição: " + descricao;
    }
    public void setDescricao(String descricao) {
        this.descricao = descricao;
    }
    public double getPreco() {
        return preco;
    }
    public void setPreco(double preco) {
        this.preco = preco;
    }
    public ImageView getImagemPath() {
        return imagemPath;
    }
    public void setImagemPath(ImageView imagemPath) {
        this.imagemPath = imagemPath;
    }
    
}
