package com.example.lojaterere;

import android.app.Dialog;
import android.os.Bundle;

import androidx.annotation.Nullable;
import androidx.fragment.app.DialogFragment;

import android.view.KeyEvent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.Window;
import android.view.WindowManager;
import android.view.inputmethod.EditorInfo;
import android.widget.EditText;
import android.widget.TextView;

public class AdicionaProdutoCarrinho extends DialogFragment implements TextView.OnEditorActionListener {

    private EditText edtQtdCarrinho;
    private TextView txtProdNome;
    private TextView txtProdPreco;
    private TextView txtProdQtd;

    public AdicionaProdutoCarrinho() {
        // Required empty public constructor
    }

    public static AdicionaProdutoCarrinho newInstance(String nomeProd, String precoProd, String qtdProd) {
        AdicionaProdutoCarrinho fragment = new AdicionaProdutoCarrinho();
        Bundle args = new Bundle();
        args.putString("arg_nome", nomeProd);
        args.putString("arg_preco", precoProd);
        args.putString("arg_qtd", qtdProd);
        fragment.setArguments(args);
        return fragment;
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.fragment_adiciona_produto_carrinho, container, false);
    }

    @Override
    public Dialog onCreateDialog(Bundle savedInstanceState) {
        Dialog dialog = super.onCreateDialog(savedInstanceState);
        // request a window without the title
        dialog.getWindow().requestFeature(Window.FEATURE_NO_TITLE);
        return dialog;
    }

    @Override
    public void onViewCreated(View view, @Nullable Bundle savedInstanceState){
        super.onViewCreated(view, savedInstanceState);

        edtQtdCarrinho = (EditText) view.findViewById(R.id.edtQtdCarrinho);
        txtProdNome = (TextView) view.findViewById(R.id.txtProdNome);
        txtProdPreco = (TextView) view.findViewById(R.id.txtProdPreco);
        txtProdQtd = (TextView) view.findViewById(R.id.txtProdQtd);

        txtProdNome.setText(getArguments().getString("arg_nome"));
        txtProdPreco.setText("R$: " + getArguments().getString("arg_preco"));
        txtProdQtd.setText("Em estoque: " + getArguments().getString("arg_qtd"));

        edtQtdCarrinho.requestFocus();
        getDialog().getWindow().setSoftInputMode(
                WindowManager.LayoutParams.SOFT_INPUT_STATE_VISIBLE);

        edtQtdCarrinho.setOnEditorActionListener(this);
    }

    @Override
    public boolean onEditorAction(TextView v, int actionId, KeyEvent event) {
        if (EditorInfo.IME_ACTION_DONE == actionId) {
            // Return input text back to activity through the implemented listener
            EditQtdDialogListener listener = (EditQtdDialogListener) getActivity();
            listener.onFinishEditDialog(edtQtdCarrinho.getText().toString());
            // Close the dialog and return back to the parent activity
            dismiss();
            return true;
        }
        return false;
    }
}