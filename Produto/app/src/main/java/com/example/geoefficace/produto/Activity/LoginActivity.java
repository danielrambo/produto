package com.example.geoefficace.produto.Activity;

import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;
import android.util.Patterns;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.basgeekball.awesomevalidation.AwesomeValidation;
import com.basgeekball.awesomevalidation.ValidationStyle;
import com.example.geoefficace.produto.DataStorage.Manager;
import com.example.geoefficace.produto.DataStorage.UsuarioDataStorage;
import com.example.geoefficace.produto.Entity.Usuario;
import com.example.geoefficace.produto.R;

/**
 * Created by geoefficace on 03/04/2019.
 */

public class LoginActivity extends AppCompatActivity {

    private EditText email;
    private EditText password;
    private Button confirm;
    private AwesomeValidation awesomeValidation;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.login);

        this.email    = getWindow().getDecorView().findViewById(R.id.email);
        this.password = getWindow().getDecorView().findViewById(R.id.password);
        this.confirm  = getWindow().getDecorView().findViewById(R.id.button_confirmar);
        this.validateForm();

        this.confirm.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                if(!awesomeValidation.validate())
                    return;

                Usuario element = new Usuario();
                element.setEmail(email.getText().toString());
                element.setPassword(password.getText().toString());

                new Manager(LoginActivity.this).deleteDatabase();
                if(authentic(element) == false) {
                    Toast.makeText(LoginActivity.this, "E-mail ou senha inválidos!", Toast.LENGTH_LONG).show();
                    return;
                }

                Intent intent = new Intent(LoginActivity.this, MainActivity.class);
                startActivity(intent);
                finish();



            }
        });

    }

    private void validateForm(){

        this.awesomeValidation = new AwesomeValidation(ValidationStyle.BASIC);
        awesomeValidation.addValidation(this.email, Patterns.EMAIL_ADDRESS, getString(R.string.error_email));
        awesomeValidation.addValidation(this.password, "^\\s*(?:\\S\\s*){1,}$", getString(R.string.required));

    }

    private boolean authentic(Usuario element){
        if(new UsuarioDataStorage(LoginActivity.this).findByEmailAndPassword(element.getEmail(), element.getPassword()) == null)
            return false;

        return true;
    }
}
