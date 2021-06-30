package com.example.lojaterere;

import androidx.appcompat.app.AppCompatActivity;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

public class EditarProdutoCarrinho extends AppCompatActivity {
    private Carrinho carrinho;
    private int editPos;
    private TextView txtNomeItemEdit;
    private TextView txtPrecoUnitItemEdit;
    private TextView txtPrecoTotalItemEdit;
    private EditText edtQtdProdItemEdit;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_editar_produto_carrinho);

        txtNomeItemEdit = findViewById(R.id.txtNomeItemEdit);
        txtPrecoUnitItemEdit = findViewById(R.id.txtPrecoUnitItemEdit);
        txtPrecoTotalItemEdit = findViewById(R.id.txtPrecoTotalItemEdit);
        edtQtdProdItemEdit = findViewById(R.id.edtQtdProdItemEdit);

        Intent it = getIntent();
        carrinho = (Carrinho) it.getSerializableExtra("carrinho");
        editPos = it.getIntExtra("edit_pos", 2000000000);

        txtNomeItemEdit.setText(carrinho.getProdutos().get(editPos).getNomeProduto());
        txtPrecoUnitItemEdit.setText(Double.toString(carrinho.getProdutos().get(editPos).getPrecoUnitario()));
        txtPrecoTotalItemEdit.setText(Double.toString(carrinho.getProdutos().get(editPos).getPreco()));
        edtQtdProdItemEdit.setText(String.valueOf(carrinho.getProdutos().get(editPos).getQuantidadeProduto()));
    }

    public void cancelaItemEdit(View v){
        final Intent data = new Intent();
        data.putExtra("carrinho", this.carrinho);
        setResult(Activity.RESULT_OK, data);
        finish();
    }

    public void deletaItemEdit(View v){
        carrinho.deletaProduto(editPos);
        final Intent data = new Intent();
        data.putExtra("carrinho", this.carrinho);
        setResult(Activity.RESULT_OK, data);
        finish();
    }

    public void atualizaItemEdit(View v){
        int novoValor = Integer.parseInt(edtQtdProdItemEdit.getText().toString());

        if (novoValor == carrinho.getProdutos().get(editPos).getQuantidadeProduto()){
            Toast toast=Toast.makeText(EditarProdutoCarrinho.this,
                    "Quantidade não alterada!",Toast.LENGTH_SHORT);
            toast.show();
        }else{
            carrinho.getProdutos().get(editPos).setQuantidadeProduto(novoValor);
            Toast toast=Toast.makeText(EditarProdutoCarrinho.this,
                    "Quantidade alterada com sucesso!",Toast.LENGTH_SHORT);
            toast.show();
            txtPrecoTotalItemEdit.setText(Double.toString(carrinho.getProdutos().get(editPos).getPreco()));
        }
    }

    @Override
    public void onBackPressed(){
        final Intent data = new Intent();
        data.putExtra("carrinho", this.carrinho);
        setResult(Activity.RESULT_OK, data);
        finish();
    }
}