package com.example.lojaterere;

import java.io.Serializable;
import java.text.DecimalFormat;

public class ProdutoCarrinho implements Serializable {

    private String nomeProduto;
    private int id;
    private double preco;
    private double precoUnitario;
    private int quantidadeProduto = 1;

    public ProdutoCarrinho(String nomeProduto, int id, double preco) {
        this.setNomeProduto(nomeProduto);
        this.setId(id);
        this.setPreco(preco);
        this.setPrecoUnitario(preco);
    }

    public ProdutoCarrinho(String nomeProduto, int id, double preco, int qtd) {
        DecimalFormat df = new DecimalFormat("#.##");
        this.setNomeProduto(nomeProduto);
        this.setId(id);
        this.setPreco(Double.valueOf(df.format(preco*qtd)));
        this.setPrecoUnitario(preco);
        this.setQuantidadeProduto(qtd);
    }

    public String toString(){
        return this.getNomeProduto() + " - Preço unitário: " + this.getPrecoUnitario() + " - Quantidade no Carrinho: " +
                this.getQuantidadeProduto() + " - Preço total: " + this.getPreco();
    }


    public double getPrecoUnitario() {
        return precoUnitario;
    }

    public void setPrecoUnitario(double precouUnitario) {
        this.precoUnitario = precouUnitario;
    }

    public int getId() {
        return id;
    }

    public void setId(int codigoProduto) {
        this.id = codigoProduto;
    }

    public double getPreco() {
        return preco;
    }

    public void setPreco(double preco) {
        this.preco = preco;
    }

    public int getQuantidadeProduto() {
        return quantidadeProduto;
    }

    public void setQuantidadeProduto(int quantidadeProduto) {
        this.quantidadeProduto = quantidadeProduto;
        this.setPreco(0);
        DecimalFormat df = new DecimalFormat("#.##");
        this.setPreco(Double.valueOf(df.format(quantidadeProduto*precoUnitario)));
    }

    public String getNomeProduto() {
        return nomeProduto;
    }

    public void setNomeProduto(String nomeProduto) {
        this.nomeProduto = nomeProduto;
    }
}
