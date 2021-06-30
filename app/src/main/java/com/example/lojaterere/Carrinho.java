package com.example.lojaterere;

import java.io.Serializable;
import java.text.DecimalFormat;
import java.util.ArrayList;

public class Carrinho implements Serializable {

    private int idCarrinho;
    private int quantidadeProdutos;
    private double valorVenda = 0;
    private int formaPagamento;
    private ArrayList<ProdutoCarrinho> produtosCarrinho;

    public Carrinho() {
        produtosCarrinho = new ArrayList<ProdutoCarrinho>();
    }

    public void calcularValorTotal() {
        this.setValorVenda(0);
        for(int i = 0; i < produtosCarrinho.size(); i++){
            this.setValorVenda(this.getValorVenda() + produtosCarrinho.get(i).getPreco());
        }
        DecimalFormat df = new DecimalFormat("#.##");
        this.setValorVenda(Double.valueOf(df.format(this.getValorVenda())));
    }

    public void addProdutos(Produto produto, int qtd) {
        if(this.getProdutos().size() == 0) {
            ProdutoCarrinho produtoCarrinho = new ProdutoCarrinho(produto.getNomeProduto(), produto.getId(), produto.getPreco(), qtd);
            this.getProdutos().add(produtoCarrinho);
        }else {
            boolean achou = false;
            for(int i = 0; i < this.getProdutos().size(); i++) {
                if(produto.getId() == produtosCarrinho.get(i).getId()) {
                    produtosCarrinho.get(i).setQuantidadeProduto(produtosCarrinho.get(i).getQuantidadeProduto() + qtd);
                    achou = true;
                    break;
                }
            }
            if(!achou) {
                ProdutoCarrinho produtoCarrinho = new ProdutoCarrinho(produto.getNomeProduto(), produto.getId(), produto.getPreco(), qtd);
                produtosCarrinho.add(produtoCarrinho);
            }

        }
    }

    public void alteraQuantidade(int pos, int novaQtd){
        this.getProdutos().get(pos).setQuantidadeProduto(novaQtd);
    }

    public void deletaProduto(int pos){
        this.getProdutos().remove(pos);
    }

    public double troco(double valorEmDinheiro) {
        return Math.round(valorEmDinheiro - this.getValorVenda());
    }

    public int getQuantidadeProdutos() {
        return this.getProdutos().size();
    }

    public double getValorVenda() {
        return valorVenda;
    }

    public void setValorVenda(double valorVenda) {
        this.valorVenda = valorVenda;
    }

    public ArrayList<ProdutoCarrinho> getProdutos() {
        return produtosCarrinho;
    }
}