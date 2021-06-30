package com.example.lojaterere;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.EditText;
import android.widget.Toast;

public class EdicaoProduto extends AppCompatActivity {

    private int updateId;
    private EditText edtNomeProdutoEdicao;
    private EditText edtPrecoEdicao;
    private EditText edtQtdEdicao;
    private DBHelper helper=new DBHelper(this);

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_edicao_produto);

        edtNomeProdutoEdicao = findViewById(R.id.edtNomeProdutoEdicao);
        edtPrecoEdicao = findViewById(R.id.edtPrecoEdicao);
        edtQtdEdicao = findViewById(R.id.edtQtdEdicao);

        Intent it = getIntent();
        Bundle bundle = it.getExtras();
        updateId = bundle.getInt("idEdicao");

        Produto editavel = helper.buscarProduto(updateId);

        edtNomeProdutoEdicao.setText(editavel.getNomeProduto());
        edtPrecoEdicao.setText(Double.toString(editavel.getPreco()));
        edtQtdEdicao.setText(Integer.toString(editavel.getQuantidadeEstoque()));
    }

    public void editarProduto(View view){
        String nome = edtNomeProdutoEdicao.getText().toString();
        double preco = Double.parseDouble(edtPrecoEdicao.getText().toString());
        int qtd = Integer.parseInt(edtQtdEdicao.getText().toString());
        int id = (updateId);

        Produto p = new Produto(nome, preco, qtd, id);

        helper.atualizarProduto(p);
        Toast toast=Toast.makeText(EdicaoProduto.this,
                "Produto atualizado com sucesso!",Toast.LENGTH_SHORT);
        toast.show();
        finish();
    }

    public void deletar(View view){
        helper.deletarProduto(updateId);
        Toast toast=Toast.makeText(EdicaoProduto.this,
                "Produto removido com sucesso!",Toast.LENGTH_SHORT);
        toast.show();
        finish();
    }

    public void cancelar(View view) {
        finish();
    }
}