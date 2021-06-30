package com.example.lojaterere;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.view.View;
import android.widget.EditText;
import android.widget.Toast;

public class CadastroProduto extends AppCompatActivity {

    private DBHelper helper=new DBHelper(this);
    private EditText edtNomeProduto;
    private EditText edtPreco;
    private EditText edtQtd;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_cadastro_produto);

        edtNomeProduto=findViewById(R.id.edtNomeProduto);
        edtPreco=findViewById(R.id.edtPreco);
        edtQtd=findViewById(R.id.edtQtd);
    }

    public void adicionarProduto(View v){
        String nome=edtNomeProduto.getText().toString();

        if(!nome.equals("") && !edtPreco.getText().toString().equals("") && !edtQtd.getText().toString().equals("")){
            double preco=Double.parseDouble(edtPreco.getText().toString());
            int qtd=Integer.parseInt(edtQtd.getText().toString());

            Produto p = new Produto(nome, preco, qtd);

            helper.inserirProduto(p);
            Toast toast=Toast.makeText(CadastroProduto.this,
                    "Produto adicionado com sucesso!",Toast.LENGTH_SHORT);
            toast.show();
            limpar();
        }else{
            Toast toast = Toast.makeText(CadastroProduto.this, "Preencha todos os campos", Toast.LENGTH_SHORT);
            toast.show();
            limpar();
        }
    }

    private void limpar() {
        edtNomeProduto.setText("");
        edtPreco.setText("");
        edtQtd.setText("");
    }

    public void cancelar(View view) {
        finish();
    }
}