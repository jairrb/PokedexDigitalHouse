package com.dhpokemon.pokedexdigitalhouse.view;

import android.content.Intent;
import android.os.Bundle;
import android.util.Patterns;
import android.view.View;
import android.view.WindowManager;
import android.widget.Button;
import android.widget.ProgressBar;

import androidx.appcompat.app.AppCompatActivity;
import androidx.lifecycle.ViewModelProviders;

import com.dhpokemon.pokedexdigitalhouse.R;
import com.dhpokemon.pokedexdigitalhouse.viewmodel.RegisterViewModel;
import com.google.android.material.snackbar.Snackbar;
import com.google.android.material.textfield.TextInputLayout;

public class RegisterActivity extends AppCompatActivity {
    private TextInputLayout textInputRegisterEmail;
    private TextInputLayout textInputRegisterPassword;
    private Button btnRegister;
    private ProgressBar progressBarRegister;
    private RegisterViewModel registerViewModel;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_register);
        this.getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN, WindowManager.LayoutParams.FLAG_FULLSCREEN);

        initViews();

        btnRegister.setOnClickListener(v -> {
            String email = textInputRegisterEmail.getEditText().getText().toString();
            String password = textInputRegisterPassword.getEditText().getText().toString();

            // Se email e senha são validos tentamos o registro no firebase
            if (validate(email, password)) {
                registerViewModel.register(email, password);
            }
        });

        //Se registrou com sucesso vamos direcionar para tela  HOME
        registerViewModel.getIsLogged().observe(this, isLogged -> {
            if (isLogged) {
                startActivity(new Intent(getApplicationContext(), HomeActivity.class));
                finish();
            }
        });

        // Se deu algum erro mostramos na tela
        registerViewModel.getLiveDataError().observe(this, throwable -> {
            String error = throwable.getMessage();
            Snackbar.make(btnRegister, error, Snackbar.LENGTH_LONG).show();
        });


        // Mostramos o loading para feeed back ao usuário enquanto carega o login
        registerViewModel.getIsLoading().observe(this, loading -> {
            if (loading) {
                progressBarRegister.setVisibility(View.VISIBLE);
            } else {
                progressBarRegister.setVisibility(View.GONE);
            }
        });
    }

    private void initViews() {
        btnRegister = findViewById(R.id.btnRegister);
        textInputRegisterEmail = findViewById(R.id.textinputRegisterEmail);
        textInputRegisterPassword = findViewById(R.id.textinputRegisterPassword);
        progressBarRegister = findViewById(R.id.progressBarRegister);
        registerViewModel = ViewModelProviders.of(this).get(RegisterViewModel.class);
        progressBarRegister.setVisibility(View.GONE);
    }

    // Essa validação pode ficar na view em vez do viewmodel, pois ela trata os elementos da tela
    private boolean validate(String email, String password) {
        if (email.isEmpty()) {
            textInputRegisterEmail.setError("E-mail cannot be empty.");
            textInputRegisterEmail.requestFocus();
            return false;
        }

        if (!Patterns.EMAIL_ADDRESS.matcher(email).matches()) {
            textInputRegisterEmail.setError("Invalid e-mail.");
            textInputRegisterEmail.requestFocus();
            return false;
        }

        if (password.isEmpty()) {
            textInputRegisterPassword.setError("Password cannot be empty.");
            textInputRegisterPassword.requestFocus();
            return false;
        }

        if (password.length() < 6) {
            textInputRegisterPassword.setError("Passwords MUST be at least 6 characters long.");
            textInputRegisterPassword.requestFocus();
            return false;
        }

        return true;
    }

}
