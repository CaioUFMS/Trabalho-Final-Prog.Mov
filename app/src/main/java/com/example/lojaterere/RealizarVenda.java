package com.example.lojaterere;

import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.FragmentManager;

import android.app.Activity;
import android.content.Intent;
import android.database.Cursor;
import android.os.Bundle;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;

import java.util.ArrayList;
import java.util.List;

public class RealizarVenda extends AppCompatActivity implements EditQtdDialogListener {

    private static final int REQUEST_CODE_CARRINHO = 0001;
    private final int RESULT_DEVOLVE_CARRINHO = 1111;
    private ListView lvProdutosVenda;
    private TextView txtEmptyLvVenda;
    private ArrayList<String> listItem;
    private ArrayList<Integer> listPosItem = new ArrayList<>();
    private ArrayAdapter adapter;
    private Carrinho carrinho = new Carrinho();
    private DBHelper helper=new DBHelper(this);
    private String prdName;
    private String loggedUser;
    private double prdPrice;
    private int qtdCarrinho;
    private int prdQtd;
    private int prdId;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_realizar_venda);

        lvProdutosVenda = findViewById(R.id.lvProdutosVenda);
        txtEmptyLvVenda = findViewById(R.id.txtEmptyLvVenda);

        Bundle args = getIntent().getExtras();
        loggedUser = args.getString("ch_usuario");

        listItem = new ArrayList<>();

        verProdutos();
    }

    public void showEditDialogue(String arg_nome, String arg_preco, String arg_qtd){
        FragmentManager fm = getSupportFragmentManager();

        AdicionaProdutoCarrinho adicionaProdutoCarrinho = AdicionaProdutoCarrinho.newInstance(arg_nome, arg_preco, arg_qtd);
        adicionaProdutoCarrinho.show(fm,"fragment_adiciona_produto");
    }

    @Override
    public void onFinishEditDialog(String inputText) {
        if(!inputText.equals("")){
            if(!(Integer.parseInt(inputText) == 0)) {
                qtdCarrinho = Integer.parseInt(inputText);
                Toast.makeText(this, qtdCarrinho + " unidades de " + prdName + " adicionadas", Toast.LENGTH_SHORT).show();
                carrinho.addProdutos(new Produto(prdName, prdPrice, prdQtd, prdId), qtdCarrinho);
            }else{
                Toast.makeText(this, "Insira uma quantidade diferente de zero!", Toast.LENGTH_SHORT).show();
            }
        }else{
            Toast.makeText(this, "Insira um valor", Toast.LENGTH_SHORT).show();
        }
    }


    @Override
    public void onResume() {
        super.onResume();
        listPosItem.clear();
        verProdutos();
    }

    public void visualizarCarrinho(View v){
        Intent it = new Intent(this, VisualizarCarrinho.class);
        it.putExtra("carrinho", carrinho);
        it.putExtra("ch_usuario", loggedUser);
        startActivityForResult(it, REQUEST_CODE_CARRINHO);
    }

    @Override
    public void onActivityResult(int requestCode, int resultCode, Intent data) {

        if(requestCode == REQUEST_CODE_CARRINHO){
            if(resultCode == Activity.RESULT_OK){
                finish();
            }else {
                this.carrinho = (Carrinho) data.getSerializableExtra("carrinho");
            }
        }

        super.onActivityResult(requestCode, resultCode, data);
    }

    private void verProdutos(){
        Cursor cursor = helper.verProdutos();
        List<String> values = new ArrayList<String>();

        if(cursor != null && cursor.moveToFirst()){
            do{
                if(!(cursor.getInt(3) == 0)){
                    int posItem = cursor.getInt(0);
                    String prodNome = cursor.getString(1);
                    String prodPreco = Double.toString(cursor.getDouble(2));
                    String prodQtd = Integer.toString(cursor.getInt(3));

                    listPosItem.add(posItem);
                    values.add(prodNome + " - Valor: R$" + prodPreco + " - Em estoque: " + prodQtd);
                }
            }while(cursor.moveToNext());

            ArrayAdapter<String> adapter = new ArrayAdapter<String>(this, android.R.layout.simple_list_item_1, values);
            lvProdutosVenda.setAdapter(adapter);
            lvProdutosVenda.setOnItemClickListener(new AdapterView.OnItemClickListener() {
                @Override
                public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                    prdId = listPosItem.get(position);
                    Produto editavel = helper.buscarProduto(prdId);
                    prdName = editavel.getNomeProduto();
                    prdPrice = editavel.getPreco();
                    prdQtd = editavel.getQuantidadeEstoque();


                    showEditDialogue(prdName, Double.toString(prdPrice), Integer.toString(prdQtd));
                }
            });
            cursor.close();
        }else{
            txtEmptyLvVenda.setVisibility(View.VISIBLE);
        }
    }
}