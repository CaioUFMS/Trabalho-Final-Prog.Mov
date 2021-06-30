package com.example.lojaterere;

import androidx.appcompat.app.AppCompatActivity;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.ListView;
import android.widget.TextView;

import java.util.ArrayList;

public class VisualizarCarrinho extends AppCompatActivity {

    private TextView txtEmptyLvCarrinho;
    private Carrinho carrinho;
    private TextView txtValorCompra;
    private Button btnFinalizar;
    private ListView lvProdutosCarrinho;
    private ArrayList<String> listaProdutos = new ArrayList<>();
    private ArrayAdapter<String> arrayAdapter;
    private int resumed;
    private String loggedUser;
    private static final int REQUEST_CODE_FINALIZARCOMPRA = 0002;
    private static final int REQUEST_CODE_EDITARPRODUTO = 0003;
    private final int RESULT_DEVOLVE_CARRINHO = 1111;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_visualizar_carrinho);
        txtValorCompra = findViewById(R.id.txtValorCompra);
        lvProdutosCarrinho = findViewById(R.id.lvProdutosCarrinho);
        txtEmptyLvCarrinho = findViewById(R.id.txtEmptyLvCarrinho);
        btnFinalizar = findViewById(R.id.btnFinalizar);

        Bundle args = getIntent().getExtras();
        loggedUser = args.getString("ch_usuario");
        Intent it = getIntent();
        carrinho = (Carrinho) it.getSerializableExtra("carrinho");

        resumed = 0;
        verCarrinho();
    }

    @Override
    protected void onResume() {
        super.onResume();
    }

    public void verCarrinho(){
        if(carrinho.getProdutos().size() > 0){

            clearIfResumed();
            for(int i = 0; i < carrinho.getProdutos().size(); i++){
                listaProdutos.add(carrinho.getProdutos().get(i).toString());
            }

            arrayAdapter = new ArrayAdapter<String>(this,
                    android.R.layout.simple_list_item_1, listaProdutos);


            lvProdutosCarrinho.setAdapter(arrayAdapter);

            carrinho.calcularValorTotal();
            txtValorCompra.setText(Double.toString(carrinho.getValorVenda()));

            lvProdutosCarrinho.setOnItemClickListener(new AdapterView.OnItemClickListener() {
                @Override
                public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                    Intent it = new Intent(getApplicationContext(), EditarProdutoCarrinho.class);
                    it.putExtra("carrinho", carrinho);
                    it.putExtra("edit_pos", position);
                    startActivityForResult(it, REQUEST_CODE_EDITARPRODUTO);
                }
            });
        }else{
            clearIfResumed();
            txtEmptyLvCarrinho.setVisibility(View.VISIBLE);
            btnFinalizar.setVisibility(View.GONE);
            txtValorCompra.setText("Não há itens");
        }
    }

    public void clearIfResumed(){
        if(resumed == 1){
            listaProdutos.clear();
            arrayAdapter.notifyDataSetChanged();
        }
    }

    @Override
    public void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);

        if(requestCode == REQUEST_CODE_EDITARPRODUTO){
            this.carrinho = (Carrinho) data.getSerializableExtra("carrinho");
            resumed = 1;
            verCarrinho();
        }else if (requestCode == REQUEST_CODE_FINALIZARCOMPRA){
            if(resultCode == Activity.RESULT_OK){
                final Intent itFinish = new Intent();
                setResult(Activity.RESULT_OK, itFinish);
                finish();
            }
        }
    }

    @Override
    public void onBackPressed() {
        final Intent data = new Intent();
        data.putExtra("carrinho", this.carrinho);
        setResult(RESULT_DEVOLVE_CARRINHO, data);
        super.onBackPressed();
    }

    public void finalizarCompra(View view){
        Intent it = new Intent(this, FinalizarCompra.class);
        it.putExtra("ch_usuario", loggedUser);
        it.putExtra("carrinho", carrinho);
        startActivityForResult(it, REQUEST_CODE_FINALIZARCOMPRA);
    }
}