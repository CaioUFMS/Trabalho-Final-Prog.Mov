package com.example.lojaterere;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.view.View;
import android.widget.EditText;
import android.widget.Toast;

import java.math.BigInteger;

public class Cadastro extends AppCompatActivity {
    private DBHelper helper=new DBHelper(this);
    private EditText edtNome;
    private EditText edtUsuario;
    private EditText edtSenha;
    private EditText edtConfSenha;
    private EditText edtCod;
    public static final int CODIGO_CADASTRO = 0000;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_cadastro);
        edtNome=findViewById(R.id.edtNome);
        edtSenha=findViewById(R.id.edtSenha);
        edtConfSenha=findViewById(R.id.edtConfSenha);
        edtCod=findViewById(R.id.edtCod);
        edtUsuario=findViewById(R.id.edtUsuario);
    }
    public void cadastrar(View view){
        String nome=edtNome.getText().toString();
        String usr=edtUsuario.getText().toString();
        String senha=edtSenha.getText().toString();
        String confSenha=edtConfSenha.getText().toString();
        String codigoCadastro=edtCod.getText().toString();

        if(!nome.equals("") && !usr.equals("") && !senha.equals("") &&
                !confSenha.equals("") && !codigoCadastro.equals("")){
            if(!helper.buscarUsuario(usr)){
                if(!senha.equals(confSenha)){
                    Toast toast=Toast.makeText(Cadastro.this, "Senha difere da confirmação de senha!",Toast.LENGTH_SHORT);
                    toast.show();
                    edtSenha.setText("");
                    edtConfSenha.setText("");
                }else{
                    if(Integer.parseInt(codigoCadastro) == CODIGO_CADASTRO){
                        String senhaComHash;
                        byte[] inputSenha = senha.getBytes();

                        BigInteger md5Data = null;
                        try {
                            md5Data = new BigInteger(1, MD5.encryptMD5(inputSenha));
                        } catch (Exception e){
                            e.printStackTrace();
                        }

                        senhaComHash = md5Data.toString();

                        Funcionario c = new Funcionario();
                        c.setNome(nome);
                        c.setUsuario(usr);
                        c.setSenha(senhaComHash);
                        helper.inserirFuncionario(c);
                        Toast toast=Toast.makeText(Cadastro.this,
                                "Cadastro realizado com sucesso!",Toast.LENGTH_SHORT);
                        toast.show();
                        limpar();
                    }else{
                        Toast toast=Toast.makeText(Cadastro.this, "Código de Cadastro Inválido",Toast.LENGTH_SHORT);
                        toast.show();
                        edtCod.setText("");
                    }
                }
            }else {
                Toast toast = Toast.makeText(Cadastro.this, "Usuário já existe", Toast.LENGTH_SHORT);
                toast.show();
                edtUsuario.setText("");
            }
        }else{
            Toast toast = Toast.makeText(Cadastro.this, "Preencha todos os campos", Toast.LENGTH_SHORT);
            toast.show();
            limpar();
        }
    }

    private void limpar() {
        edtNome.setText("");
        edtSenha.setText("");
        edtConfSenha.setText("");
        edtCod.setText("");
        edtUsuario.setText("");
    }

    public void cancelar(View view) {
        finish();
    }
}