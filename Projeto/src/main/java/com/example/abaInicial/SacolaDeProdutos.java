package com.example.abaInicial;

import java.text.NumberFormat;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Locale;
import java.util.Map;

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
            valorTotal += produto.getPreco();
        }
    }
    public void removerProduto(Produto produto) {
        if(produto != null){
            produtosNaSacola.remove(produto);
            valorTotal -= produto.getPreco();
        }
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
        String valorFormatadoComoString = String.format(Locale.US, "%.2f", valorTotal);
        return Double.parseDouble(valorFormatadoComoString);
    }
    public void setQuantidadeProdutos(int quantidadeProdutos) {
        this.quantidadeProdutos = quantidadeProdutos;
    }

    public List<Produto> getProdutosUnicosParaExibir() {
        // Retorna uma lista de Produtos (sem repetição)
        return new ArrayList<>(getProdutosUnicosPorNome().values());
    }

    public Map<String, Produto> getProdutosUnicosPorNome() {
        // Mapeia o nome do produto ao objeto Produto, agrupando-os
        Map<String, Produto> unicos = new HashMap<>();
        for (Produto p : produtosNaSacola) { 
            unicos.put(p.getNome(), p); // Assume que o nome é único o suficiente para agrupamento
        }
        return unicos;
    }

    
}
