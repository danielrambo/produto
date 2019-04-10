package com.example.geoefficace.produto.Activity;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.Toast;

import com.basgeekball.awesomevalidation.AwesomeValidation;
import com.basgeekball.awesomevalidation.ValidationStyle;
import com.example.geoefficace.produto.DataStorage.ProdutoDataStorage;
import com.example.geoefficace.produto.Entity.Categoria;
import com.example.geoefficace.produto.Entity.Produto;
import com.example.geoefficace.produto.Entity.Subcategoria;
import com.example.geoefficace.produto.R;
import com.google.android.gms.analytics.ecommerce.Product;
import com.isapanah.awesomespinner.AwesomeSpinner;

import java.util.UUID;

import br.com.concrete.canarinho.watcher.ValorMonetarioWatcher;

/**
 * Created by geoefficace on 04/04/2019.
 */

public class ProductActivity extends AppCompatActivity {

    private Toolbar toolbar;
    private EditText name;
    private AwesomeSpinner category;
    private AwesomeSpinner subcategory;
    private EditText description;
    private EditText price;
    private EditText amount;

    private ArrayAdapter<String> adapter_category;
    private ArrayAdapter<String> adapter_subcategory;
    private Categoria categoria;
    private Subcategoria subcategoria;

    private AwesomeValidation awesomeValidation;
    private String id;
    private Produto product;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.product);

        this.toolbar     = getWindow().getDecorView().findViewById(R.id.toolbar);
        this.name        = getWindow().getDecorView().findViewById(R.id.name);
        this.category    = getWindow().getDecorView().findViewById(R.id.category);
        this.subcategory = getWindow().getDecorView().findViewById(R.id.subcategory);
        this.description = getWindow().getDecorView().findViewById(R.id.description);
        this.price       = getWindow().getDecorView().findViewById(R.id.price);
        this.amount      = getWindow().getDecorView().findViewById(R.id.amount);
        this.validateForm();

        setSupportActionBar(this.toolbar);
        ImageView toolbar_icon = getWindow().getDecorView().findViewById(R.id.toolbar_icon);
        toolbar_icon.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                finish();
            }
        });

        ValorMonetarioWatcher valorMonetarioWatcher = new ValorMonetarioWatcher.Builder()
                .comSimboloReal()
                .comMantemZerosAoLimpar()
                .build();
        this.price.addTextChangedListener(valorMonetarioWatcher);

        this.id = getIntent().getStringExtra("id");
        this.loadSelect();

        if(this.id != null)
            this.loadForm();



    }

    private void loadSelect(){

        this.categoria = new Categoria();
        this.adapter_category = new ArrayAdapter<String>(
                this,
                android.R.layout.simple_dropdown_item_1line,
                this.categoria.getCategoriasString(this.categoria.getList())
        );
        this.category.setAdapter(this.adapter_category);
        this.category.setOnSpinnerItemClickListener(new AwesomeSpinner.onSpinnerItemClickListener<String>() {
            @Override
            public void onItemSelected(int i, String s) {

                for(int x = 0; x < categoria.getList().size(); x++){
                    if(categoria.getList().get(x).getName().equals(s)){
                        if(product == null)
                            product = new Produto();

                        product.setCategoria(categoria.getList().get(x).getId());
                        break;
                    }
                }

            }
        });

        this.subcategoria = new Subcategoria();
        this.adapter_subcategory = new ArrayAdapter<String>(
                this,
                android.R.layout.simple_dropdown_item_1line,
                this.subcategoria.getSubcategoriasString(this.subcategoria.getList())
        );
        this.subcategory.setAdapter(this.adapter_subcategory);
        this.subcategory.setOnSpinnerItemClickListener(new AwesomeSpinner.onSpinnerItemClickListener<String>() {
            @Override
            public void onItemSelected(int i, String s) {

                for(int x = 0; x < subcategoria.getList().size(); x++){
                    if(subcategoria.getList().get(x).getName().equals(s)){
                        if(product == null)
                            product = new Produto();

                        product.setSubcategoria(subcategoria.getList().get(x).getId());
                        break;
                    }
                }

            }
        });

    }

    private void loadForm(){

        this.product = new ProdutoDataStorage(this).findById(id);
        if(this.product == null)
            return;

        this.name.setText(this.product.getNome());
        this.description.setText(this.product.getDescricao());
        this.price.setText(this.product.getPreco());
        this.amount.setText(String.valueOf(this.product.getQuantidade()));

        for(int x = 0; x < categoria.getList().size(); x++){
            if(categoria.getList().get(x).getId().equals(this.product.getCategoria())){
                category.setSelection(x);
                break;
            }
        }

        for(int x = 0; x < subcategoria.getList().size(); x++){
            if(subcategoria.getList().get(x).getId().equals(this.product.getSubcategoria())){
                subcategory.setSelection(x);
                break;
            }
        }

    }

    private void validateForm(){

        this.awesomeValidation = new AwesomeValidation(ValidationStyle.BASIC);
        awesomeValidation.addValidation(this.name, "^\\s*(?:\\S\\s*){1,}$", getString(R.string.required));
        awesomeValidation.addValidation(this.description, "^\\s*(?:\\S\\s*){1,}$", getString(R.string.required));
        awesomeValidation.addValidation(this.price, "^\\s*(?:\\S\\s*){1,}$", getString(R.string.required));
        awesomeValidation.addValidation(this.amount, "^\\s*(?:\\S\\s*){1,}$", getString(R.string.required));

    }

    private void save(){

        if(!awesomeValidation.validate())
            return;

        if(this.product == null)
            this.product = new Produto();

        if(price.getText().toString().equals("R$ 0,00")){
            Toast.makeText(this, "O preço não pode ser 0", Toast.LENGTH_LONG).show();
            return;
        }

        if(Integer.parseInt(amount.getText().toString()) == 0){
            Toast.makeText(this, "A quantidade não pode ser 0", Toast.LENGTH_LONG).show();
            return;
        }

        if(this.product.getCategoria() == null){
            Toast.makeText(this, "Nenhuma categoria selecionada", Toast.LENGTH_LONG).show();
            return;
        }

        if(this.product.getSubcategoria() == null){
            Toast.makeText(this, "Nenhuma subcategoria selecionada", Toast.LENGTH_LONG).show();
            return;
        }

        if(this.id != null)
            this.product.setId(id);
        else
            this.product.setId(UUID.randomUUID().toString());

        this.product.setNome(name.getText().toString());
        this.product.setDescricao(description.getText().toString());
        this.product.setPreco(price.getText().toString());
        this.product.setQuantidade(Integer.parseInt(amount.getText().toString()));

        new ProdutoDataStorage(this).saveOrUpdate(this.product);
        Toast.makeText(this, "Produto registrado com sucesso!", Toast.LENGTH_LONG).show();
        this.finish();

    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {

        MenuInflater inflater = getMenuInflater();
        inflater.inflate(R.menu.menuform, menu);

        return super.onCreateOptionsMenu(menu);
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {

        switch (item.getItemId()){

            case R.id.save:
                this.save();

                return true;

            case android.R.id.home:
                //quando clicar na seta de voltar
                return true;

            default:
                return super.onOptionsItemSelected(item);
        }

    }

}
