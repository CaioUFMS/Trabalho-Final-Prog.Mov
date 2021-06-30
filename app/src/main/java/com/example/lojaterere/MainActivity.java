package com.example.lojaterere;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.EditText;
import android.widget.Toast;

import java.math.BigInteger;

public class MainActivity extends AppCompatActivity {
    private DBHelper helper=new DBHelper(this);
    private EditText edtUsuario;
    private EditText edtSenha;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        edtUsuario=findViewById(R.id.edtUsuario);
        edtSenha=findViewById(R.id.edtSenha);
    }
    public void conectar(View view) {
        String usr=edtUsuario.getText().toString();
        String senha=edtSenha.getText().toString();
        String pass=helper.buscarSenha(usr);
        String senhaComHash;
        byte[] inputSenha = senha.getBytes();

        BigInteger md5Data = null;
        try {
            md5Data = new BigInteger(1, MD5.encryptMD5(inputSenha));
        } catch (Exception e){
            e.printStackTrace();
        }
        senhaComHash = md5Data.toString();

        if(senhaComHash.equals(pass)){
            Intent it=new Intent(this, SelecaoFuncao.class);
            it.putExtra("ch_usuario",usr);
            startActivity(it);
        }else{
            Toast toast=Toast.makeText(MainActivity.this,"Usuário ou senha não encontrado",
                    Toast.LENGTH_LONG);
            toast.show();
        }
    }
    public void cadastrar(View view) {
        Intent it=new Intent(this, Cadastro.class);
        startActivity(it);
    }
}