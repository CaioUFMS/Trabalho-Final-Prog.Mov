package com.example.lojaterere;

import androidx.appcompat.app.AppCompatActivity;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import java.text.DecimalFormat;

public class FinalizarCompra extends AppCompatActivity {

    private Carrinho carrinho;
    private TextView txtPrecoCompra;
    private TextView txtTroco;
    private EditText edtValorPago;
    private double valorCompra;
    private double valorPago;
    private double valorTroco;
    private String loggedUser;
    private DBHelper helper=new DBHelper(this);

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_finalizar_compra);
        txtPrecoCompra = findViewById(R.id.txtPrecoCompra);
        txtTroco = findViewById(R.id.txtTroco);
        edtValorPago = findViewById(R.id.edtValorPago);

        Bundle args = getIntent().getExtras();
        loggedUser = args.getString("ch_usuario");

        Intent it = getIntent();
        carrinho = (Carrinho) it.getSerializableExtra("carrinho");
        valorCompra = carrinho.getValorVenda();

        txtPrecoCompra.setText(Double.toString(valorCompra));
    }

    public void calcularTroco(View v){
        if(!edtValorPago.getText().toString().equals("")){
            valorPago = Double.parseDouble(edtValorPago.getText().toString());

            valorTroco = valorCompra - valorPago;

            if (valorTroco > 0){
                txtTroco.setText("Quantia insuficente");
            }else if(valorTroco == 0 ){
                txtTroco.setText("O valor exato foi pago");
            }else{
                valorTroco = (valorTroco * (-1));
                DecimalFormat df = new DecimalFormat("#.##");
                String troco = "Troco: R$" + df.format(valorTroco);
                txtTroco.setText(troco);
            }
        }else{
            Toast toast=Toast.makeText(FinalizarCompra.this,
                    "Insira um valor",Toast.LENGTH_SHORT);
            toast.show();
        }
    }

    public void finalizarVenda(View v){

        for(int i = 0; i < carrinho.getProdutos().size(); i++){
            int idDesconto = carrinho.getProdutos().get(i).getId();
            int qtdDesconto = carrinho.getProdutos().get(i).getQuantidadeProduto();
            helper.descontaEstoque(idDesconto, qtdDesconto);
        }

        helper.atualizarVendas(loggedUser);

        final Intent itFinish = new Intent();
        setResult(Activity.RESULT_OK, itFinish);
        finish();
    }

    @Override
    public void onBackPressed() {
        setResult(Activity.RESULT_CANCELED);
        super.onBackPressed();
    }
}