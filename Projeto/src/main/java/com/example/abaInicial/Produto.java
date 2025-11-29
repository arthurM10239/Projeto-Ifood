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
        if (this.descricao.length() > 10) {
            for (int i = 0,cont = 0; i < this.descricao.length(); i++) {
                if(this.descricao.charAt(i) == ' '){
                    cont++;
                }
                else if (i > 15 || cont >= 2) {
                    return this.descricao.substring(0, i) + "\n" + this.descricao.substring(i);
                }

            }
        }
        return descricao;
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
