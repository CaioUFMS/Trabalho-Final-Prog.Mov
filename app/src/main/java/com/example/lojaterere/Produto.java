package com.example.lojaterere;

public class Produto {

    private int id;
    private String nomeProduto;
    private double preco;
    private int quantidadeEstoque;

    public Produto(String nomeProduto, double Preco, int quantidadeEstoque) {
        this.setNomeProduto(nomeProduto);
        this.setPreco(Preco);
        this.setQuantidadeEstoque(quantidadeEstoque);
    }

    public Produto(String nomeProduto, double Preco, int quantidadeEstoque, int id) {
        this.setNomeProduto(nomeProduto);
        this.setPreco(Preco);
        this.setQuantidadeEstoque(quantidadeEstoque);
        this.setId(id);
    }

    public void listarProduto() {
        System.out.println("[" + this.getId() + "]" + " - " + this.getNomeProduto());
    }

    public void listarProdutoEstoque() {
        System.out.println("[" + this.getId() + "]" + " | " + this.getQuantidadeEstoque() + " | " + this.getNomeProduto());
    }

    public int getId() {
        return this.id;
    }

    public void setId(int codigoProduto) {
        this.id = codigoProduto;
    }

    public String getNomeProduto() {
        return nomeProduto;
    }

    public void setNomeProduto(String nomeProduto) {
        this.nomeProduto = nomeProduto;
    }

    public double getPreco() {
        return preco;
    }

    public void setPreco(double preco) {
        this.preco = preco;
    }

    public int getQuantidadeEstoque() {
        return quantidadeEstoque;
    }

    public void setQuantidadeEstoque(int quantidadeEstoque) {
        this.quantidadeEstoque = quantidadeEstoque;
    }

    @Override
    public String toString() {
        return nomeProduto + ", R$ " + preco + ", Unidades Disponíveis = " + quantidadeEstoque;
    }
}