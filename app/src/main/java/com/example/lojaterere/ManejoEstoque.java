package com.example.lojaterere;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.database.Cursor;
import android.os.Bundle;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;

import java.lang.reflect.Array;
import java.util.ArrayList;
import java.util.List;

public class ManejoEstoque extends AppCompatActivity {

    private ListView lvProdutos;
    private ArrayList<String> listItem;
    private ArrayAdapter adapter;
    private ArrayList<Integer> listPosItem = new ArrayList<>();
    private DBHelper helper=new DBHelper(this);
    private TextView txtEmptyLv;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_manejo_estoque);
        lvProdutos = findViewById(R.id.lvProdutos);
        txtEmptyLv = findViewById(R.id.txtEmptyLv);

        listItem = new ArrayList<>();

        verProdutos();
    }

    @Override
    public void onResume() {
        super.onResume();
        listPosItem.clear();
        verProdutos();
    }

    private void verProdutos(){
        Cursor cursor = helper.verProdutos();
        List<String> values = new ArrayList<String>();

        if(cursor != null && cursor.moveToFirst()){
            do{
                int posItem = cursor.getInt(0);
                String prodNome = cursor.getString(1);
                String prodPreco = Double.toString(cursor.getDouble(2));
                String prodQtd = Integer.toString(cursor.getInt(3));

                listPosItem.add(Integer.valueOf(posItem));
                values.add(prodNome + " - Valor: R$" + prodPreco + " - Em estoque: " + prodQtd);

            }while(cursor.moveToNext());

            ArrayAdapter<String> adapter = new ArrayAdapter<String>(this, android.R.layout.simple_list_item_1, values);
            lvProdutos.setAdapter(adapter);
            lvProdutos.setOnItemClickListener(new AdapterView.OnItemClickListener() {
                @Override
                public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                    Intent it=new Intent(ManejoEstoque.this, EdicaoProduto.class);
                    it.putExtra("idEdicao", listPosItem.get(position));
                    startActivity(it);
                }
            });
            cursor.close();
        }else{
            txtEmptyLv.setVisibility(View.VISIBLE);
        }
    }
}