package com.example.abaInicial;

import java.util.ArrayList;

public class SacolaDeProdutos {
    private ArrayList<Produto> produtosNaSacola;
    private double valorTotal;
    private int quantidadeProdutos;

    public SacolaDeProdutos() {
        this.produtosNaSacola = new ArrayList<>();
    }

    public void addProduto(Produto produto) {
        if(produto != null){
            produtosNaSacola.add(produto);
        }
    }
    public void removerProduto(Produto produto) {
        produtosNaSacola.remove(produto);
    }
    
    public Produto getProdutoIndex(int index) {
        return produtosNaSacola.get(index);
    }

    public ArrayList<Produto> getProdutosNaSacola() {
        return produtosNaSacola;
    }


    public double getValorTotal() {
        return valorTotal;
    }
    public void setValorTotal(double valorTotal) {
        this.valorTotal = valorTotal;
    }
    public int getQuantidadeProdutos() {
        return quantidadeProdutos;
    }
    public void setQuantidadeProdutos(int quantidadeProdutos) {
        this.quantidadeProdutos = quantidadeProdutos;
    }

    
}
