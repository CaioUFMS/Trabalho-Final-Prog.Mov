package com.example.lojaterere;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.TextView;

public class SelecaoFuncao extends AppCompatActivity {

    private TextView txtNome;
    private TextView txtVendas;
    private String loggedUser;
    private DBHelper helper=new DBHelper(this);

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_selecao_funcao);
        txtNome = findViewById(R.id.txtNome);
        txtVendas = findViewById(R.id.txtVendas);

        Bundle args = getIntent().getExtras();
        loggedUser = args.getString("ch_usuario");
        String vendas = String.valueOf(helper.buscarVendas(loggedUser));

        txtNome.setText("Bem vindo, " + loggedUser + ", o que deseja fazer?");
        txtVendas.setText("Você já fez: " + vendas + " vendas!");
    }

    @Override
    public void onResume() {
        String vendasResume = String.valueOf(helper.buscarVendas(loggedUser));
        txtVendas.setText("Você já fez: " + vendasResume + " venda(s)!");

        super.onResume();
    }

    public void realizarVenda(View view) {
        Intent it=new Intent(this, RealizarVenda.class);
        it.putExtra("ch_usuario", loggedUser);
        startActivity(it);
    }

    public void manejarEstoque(View view) {
        Intent it=new Intent(this, ManejoEstoque.class);
        startActivity(it);
    }

    public void adicionarProduto(View view) {
        Intent it=new Intent(this, CadastroProduto.class);
        startActivity(it);
    }
}