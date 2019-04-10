package com.example.geoefficace.produto.Activity;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v7.app.AppCompatActivity;

import com.example.geoefficace.produto.Fragment.ListaProduto;
import com.example.geoefficace.produto.R;

/**
 * Created by geoefficace on 04/04/2019.
 */

public class MainActivity extends AppCompatActivity {

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.main);

        ListaProduto listaProduto = new ListaProduto();
        Fragment fragment = listaProduto;

        FragmentManager fragmentManager = getSupportFragmentManager();
        fragmentManager.beginTransaction()
                .replace(R.id.container, fragment).commit();

    }

}
