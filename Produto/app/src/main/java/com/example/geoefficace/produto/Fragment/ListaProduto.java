package com.example.geoefficace.produto.Fragment;

import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.design.widget.FloatingActionButton;
import android.support.v4.app.Fragment;
import android.support.v7.app.AlertDialog;
import android.text.Editable;
import android.text.TextWatcher;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.EditText;
import android.widget.ListView;
import android.widget.Toast;

import com.example.geoefficace.produto.Activity.ProductActivity;
import com.example.geoefficace.produto.DataStorage.IAsyncCallback;
import com.example.geoefficace.produto.DataStorage.ProdutoDataStorage;
import com.example.geoefficace.produto.Entity.Produto;
import com.example.geoefficace.produto.Helper.Alert;
import com.example.geoefficace.produto.R;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by geoefficace on 03/04/2019.
 */

public class ListaProduto extends Fragment implements IListWithMenu<Produto>{

    private ListView listView;
    private FloatingActionButton add;
    private EditText find;

    private List<Produto> list_produto;
    private List<Produto> filter;

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.collection_sample, container, false);

        this.listView  = view.findViewById(R.id.list_view_collection);
        this.add       = view.findViewById(R.id.button_add);
        this.find      = view.findViewById(R.id.find);

        this.find.addTextChangedListener(new TextWatcher() {

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {}

            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {}

            @Override
            public void afterTextChanged(Editable s) {

                final String word   = s.toString().toUpperCase();
                filter = new ArrayList<>();

                if(word.equals("")){
                    filter = list_produto;
                } else {

                    for(Produto p : list_produto){
                        if(p.getNome().toUpperCase().indexOf(word) != -1){
                            filter.add(p);
                        }
                    }

                }

                loadItens(filter);

            }
        });


        this.add.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                Intent intent = new Intent(getActivity(), ProductActivity.class);
                startActivity(intent);

            }
        });

        return view;

    }

    @Override
    public void onResume() {
        super.onResume();

        this.list_produto = new ProdutoDataStorage(getContext()).getAll(null, null);
        this.filter = this.list_produto;
        this.find.setText("");

        this.loadItens(this.filter);
    }

    public void loadItens(List<Produto> list) {


        ListSampleWithMenuAdapter adapterVisita = new ListSampleWithMenuAdapter(list, getContext(), this);
        this.listView.setAdapter(adapterVisita);

        registerForContextMenu(this.listView);

    }

    @Override
    public void toView(Produto element) {

    }

    @Override
    public void edit(Produto element) {

        Intent intent = new Intent(getContext(), ProductActivity.class);
        intent.putExtra("id", element.getId());
        startActivity(intent);

    }

    @Override
    public void remove(Produto element) {

        Alert alert = new Alert(getContext());
        alert.show("Atenção!", "Tem certeza que deseja deletar esse produto?", new IAsyncCallback<AlertDialog>() {
            @Override
            public void receive(Exception ex, AlertDialog data) {

                if (data != null) {

                    new ProdutoDataStorage(getContext()).remove(element);
                    Toast.makeText(getContext(), "Produto removido com sucesso!", Toast.LENGTH_LONG).show();

                    data.dismiss();

                    list_produto = new ProdutoDataStorage(getContext()).getAll(null, null);
                    filter       = list_produto;
                    loadItens(filter);
                }
            }

        });

    }
}
