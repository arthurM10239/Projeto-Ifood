package com.example.abaInicial;

import java.util.ArrayList;

public class SacolaDeProdutos {
    private ArrayList<Produto> produtosNaSacola;
    private double valorTotal;
    private int quantidadeProdutos;

    public SacolaDeProdutos() {
        this.produtosNaSacola = new ArrayList<>();
        valorTotal = 0.0;
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
    public int getTamanhoSacola() {
        return produtosNaSacola.size();
    }
    public int getQuantProduto(String nomeProduto) {
        int count = 0;
        for (Produto produto : produtosNaSacola) {
            if (produto.getNome().equals(nomeProduto)) {
                count++;
            }
        }
        return count;

    }
    public boolean isEmpty() {
        return produtosNaSacola.isEmpty();
    }

    public double getValorTotal() {
        for (Produto produto : produtosNaSacola) {
            valorTotal += produto.getPreco();
        }
        return valorTotal;
    }
    public void setQuantidadeProdutos(int quantidadeProdutos) {
        this.quantidadeProdutos = quantidadeProdutos;
    }

    
}
