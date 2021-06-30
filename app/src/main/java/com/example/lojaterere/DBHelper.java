package com.example.lojaterere;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;
import android.widget.SimpleCursorAdapter;

import androidx.annotation.Nullable;

public class DBHelper extends SQLiteOpenHelper {
    private static final int DATABASE_VERSION = 1;
    private static final String DATABASE_NAME="ervaterere.db";
    Context context;

    //NOME DAS TABELAS
    private static final String TABLE_FUNCIONARIOS="funcionario";
    private static final String TABLE_PRODUTOS="produto";
    //COLUNAS DA TABELA FUNCIONARIOS
    private static final String KEY_FUNCIONARIO_ID="id";
    private static final String KEY_FUNCIONARIO_NAME="nome";
    private static final String KEY_FUNCIONARIO_USER="usuario";
    private static final String KEY_FUNCIONARIO_PASS="senha";
    private static final String KEY_FUNCIONARIO_VENDAS="vendas";
    //COLUNAS DA TABELA PRODUTOS
    private static final String KEY_PRODUTO_ID="idProduto";
    private static final String KEY_PRODUTO_NAME="nomeProduto";
    private static final String KEY_PRODUTO_PRICE="precoProduto";
    private static final String KEY_PRODUTO_QTD="quantidadeProduto";

    private static final String TABLE_CREATE = "create table " + TABLE_FUNCIONARIOS +
            "("+ KEY_FUNCIONARIO_ID + " integer primary key autoincrement, " +  KEY_FUNCIONARIO_NAME + " text not null, " +
            KEY_FUNCIONARIO_USER + " text not null, " + KEY_FUNCIONARIO_PASS + " text not null, " +
            KEY_FUNCIONARIO_VENDAS + " integer not null);";

    private static final String TABLE_CREATE_PRODUTOS = "create table " + TABLE_PRODUTOS +
            "(" + KEY_PRODUTO_ID + " integer primary key autoincrement, " + KEY_PRODUTO_NAME + " text not null, " +
            KEY_PRODUTO_PRICE + " double not null, " + KEY_PRODUTO_QTD + " integer not null);";

    SQLiteDatabase db;


    public DBHelper(@Nullable Context context) {
        super(context, DATABASE_NAME, null, DATABASE_VERSION);
        this.context = context;
    }
    @Override
    public void onCreate(SQLiteDatabase db) {
        db.execSQL(TABLE_CREATE);
        db.execSQL(TABLE_CREATE_PRODUTOS);
        this.db=db;
    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
        if (oldVersion != newVersion) {
            db.execSQL("DROP TABLE IF EXISTS " + TABLE_FUNCIONARIOS);
            db.execSQL("DROP TABLE IF EXISTS " + TABLE_PRODUTOS);
            onCreate(db);
        }
    }

    public void inserirFuncionario(Funcionario c){

        db=this.getWritableDatabase();

        ContentValues values=new ContentValues();
        values.put(KEY_FUNCIONARIO_NAME, c.getNome());
        values.put(KEY_FUNCIONARIO_USER, c.getUsuario());
        values.put(KEY_FUNCIONARIO_PASS, c.getSenha());
        values.put(KEY_FUNCIONARIO_VENDAS, 0);
        db.insert(TABLE_FUNCIONARIOS,null,values);
        db.close();
    }

    public void inserirProduto(Produto p){

        db=this.getWritableDatabase();

        ContentValues values=new ContentValues();
        values.put(KEY_PRODUTO_NAME, p.getNomeProduto());
        values.put(KEY_PRODUTO_PRICE, p.getPreco());
        values.put(KEY_PRODUTO_QTD, p.getQuantidadeEstoque());
        db.insert(TABLE_PRODUTOS,null,values);
        db.close();
    }

    public String buscarSenha(String usr){

        db=this.getReadableDatabase();

        String query = "select usuario, senha from "+TABLE_FUNCIONARIOS;
        Cursor cursor=db.rawQuery(query,null);
        String a,b;
        b="não encontrado";
        if(cursor.moveToFirst()){
            do{
                a=cursor.getString(0);
                if(a.equals(usr)){
                    b=cursor.getString(1);
                    break;
                }
            }while(cursor.moveToNext());
        }
        return b;
    }

    public boolean buscarUsuario(String usr){

        db=this.getReadableDatabase();

        String query = "select usuario from "+TABLE_FUNCIONARIOS;
        Cursor cursor=db.rawQuery(query,null);
        String a;
        boolean exists = false;
        if(cursor.moveToFirst()){
            do{
                a=cursor.getString(0);
                if(a.equals(usr)){
                    exists = true;
                    break;
                }
            }while(cursor.moveToNext());
        }
        return exists;
    }

    public Produto buscarProduto(int searchId){

        db = this.getReadableDatabase();

        String query = "select * from " + TABLE_PRODUTOS +
                " where idProduto = " + searchId;

        Cursor cursor = db.rawQuery(query, null);
        cursor.moveToFirst();
        Produto retorno = new Produto(cursor.getString(1),
                cursor.getDouble(2),
                cursor.getInt(3));

        return retorno;
    }

    public int buscarVendas(String usr){

        db = this.getReadableDatabase();

        String query = "select usuario, vendas from " + TABLE_FUNCIONARIOS;
        Cursor cursor=db.rawQuery(query,null);
        String a;
        int retorno = -20;

        if(cursor.moveToFirst()){
            do{
                a=cursor.getString(0);
                if(a.equals(usr)){
                    retorno = cursor.getInt(1);
                    break;
                }
            }while(cursor.moveToNext());
        }
        return retorno;
    }

    public void atualizarVendas(String usr){

        db=this.getWritableDatabase();

        String query = "select usuario, vendas, id from " + TABLE_FUNCIONARIOS;
        Cursor cursor = db.rawQuery(query,null);
        String a;
        int vendasAntigas = -1;
        int idBusca = -1;

        if(cursor.moveToFirst()){
            do{
                a=cursor.getString(0);
                if(a.equals(usr)){
                    vendasAntigas = cursor.getInt(1);
                    idBusca = cursor.getInt(2);
                    break;
                }
            }while(cursor.moveToNext());
        }

        ContentValues cv = new ContentValues();
        int vendasNovas = vendasAntigas + 1;
        cv.put(KEY_FUNCIONARIO_VENDAS, vendasNovas);

        db.update(TABLE_FUNCIONARIOS, cv, KEY_FUNCIONARIO_ID + " = " + idBusca, null);
    }

    public void atualizarProduto(Produto produto){

        db=this.getWritableDatabase();

        ContentValues cv = new ContentValues();
        cv.put(KEY_PRODUTO_NAME, produto.getNomeProduto());
        cv.put(KEY_PRODUTO_PRICE, produto.getPreco());
        cv.put(KEY_PRODUTO_QTD, produto.getQuantidadeEstoque());

        db.update(TABLE_PRODUTOS, cv, KEY_PRODUTO_ID + " = " + produto.getId(), null);
    }

    public void descontaEstoque(int idDesconto, int qtdDesconto){

        db=this.getWritableDatabase();

        String query = "select " + KEY_PRODUTO_QTD + " from " + TABLE_PRODUTOS +
                " where idProduto = " + idDesconto;

        Cursor cursor = db.rawQuery(query, null);
        cursor.moveToFirst();
        int novaQtd = (cursor.getInt(0) - qtdDesconto);

        ContentValues cv = new ContentValues();
        cv.put(KEY_PRODUTO_QTD, novaQtd);

        db.update(TABLE_PRODUTOS, cv, KEY_PRODUTO_ID + " = " + idDesconto, null);
    }

    public Cursor verProdutos(){
        db = this.getReadableDatabase();
        String query = "Select * from " + TABLE_PRODUTOS;
        Cursor cursor = db.rawQuery(query, null);

        if(cursor != null){
            cursor.moveToFirst();
        }

        return cursor;
    }

    public void deletarProduto(int deleteId){
        db=this.getWritableDatabase();

        db.delete(TABLE_PRODUTOS, KEY_PRODUTO_ID + "=" + deleteId, null);
    }


}