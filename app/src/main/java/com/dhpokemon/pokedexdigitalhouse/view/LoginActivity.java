package com.dhpokemon.pokedexdigitalhouse.view;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.view.WindowManager;
import android.widget.Button;
import android.widget.ProgressBar;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.lifecycle.ViewModelProviders;

import com.dhpokemon.pokedexdigitalhouse.R;
import com.dhpokemon.pokedexdigitalhouse.viewmodel.LoginViewModel;
import com.google.android.gms.auth.api.signin.GoogleSignIn;
import com.google.android.gms.auth.api.signin.GoogleSignInAccount;
import com.google.android.gms.auth.api.signin.GoogleSignInClient;
import com.google.android.gms.auth.api.signin.GoogleSignInOptions;
import com.google.android.gms.common.ConnectionResult;
import com.google.android.gms.common.api.ApiException;
import com.google.android.gms.common.api.GoogleApiClient;
import com.google.android.gms.tasks.Task;
import com.google.android.material.snackbar.Snackbar;
import com.google.android.material.textfield.TextInputLayout;
import com.google.firebase.auth.AuthCredential;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.auth.GoogleAuthProvider;

public class LoginActivity extends AppCompatActivity implements GoogleApiClient.OnConnectionFailedListener {
    private Button btnLogin;
    private Button btnGoogle;
    private TextInputLayout textInputLayoutEmail;
    private TextInputLayout textInputLayoutPassword;
    private TextView textViewRegister;
    private ProgressBar progressBarLogin;

    private LoginViewModel loginViewModel;


    private GoogleSignInClient googleSignInClient;
    private FirebaseAuth firebaseAuth;

    private FirebaseAuth.AuthStateListener authStateListener;

    private static final int RESULT_GOOGLE = 200;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);
        this.getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN, WindowManager.LayoutParams.FLAG_FULLSCREEN);

        firebaseAuth = FirebaseAuth.getInstance();

        initViews();

        //Vai para tela de registro de usuário
        textViewRegister.setOnClickListener(v -> startActivity(new Intent(LoginActivity.this, RegisterActivity.class)));

        btnLogin.setOnClickListener(v -> {
            String email = textInputLayoutEmail.getEditText().getText().toString();
            String password = textInputLayoutPassword.getEditText().getText().toString();

            if (!email.isEmpty() && !password.isEmpty()) {
                loginViewModel.loginWithEmail(email, password);
            }
        });

        //Se logou com sucesso vamos direcionar para tela  HOME
        loginViewModel.getIsLogged().observe(this, isLogged -> {
            if (isLogged) {
                startActivity(new Intent(getApplicationContext(), HomeActivity.class));
                finish();
            }
        });

        // Se deu algum erro mostramos na tela
        loginViewModel.getLiveDataError().observe(this, throwable -> {
            String error = throwable.getMessage();
            Snackbar.make(btnLogin, error, Snackbar.LENGTH_LONG).show();
        });

        // Mostramos o loading para feeed back ao usuário enquanto carega o login
        loginViewModel.getIsLoading().observe(this, loading -> {
            if (loading) {
                progressBarLogin.setVisibility(View.VISIBLE);
            } else {
                progressBarLogin.setVisibility(View.GONE);
            }
        });


        authStateListener = firebaseAuth -> {
            FirebaseUser user = firebaseAuth.getCurrentUser();
            if (user != null) {
                startActivity(new Intent(getApplicationContext(), HomeActivity.class));
                finish();
            }
        };


        btnGoogle.setOnClickListener(v -> {
            try {
                //Google Sign In
                GoogleSignInOptions options = new GoogleSignInOptions
                        .Builder(GoogleSignInOptions.DEFAULT_SIGN_IN)
                        .requestIdToken(getString(R.string.default_web_client_id))
                        .requestEmail()
                        .build();

                googleSignInClient = GoogleSignIn.getClient(this, options);
                signIn();
            } catch (Exception e) {
                e.printStackTrace();
            }
        });
    }

    private void initViews() {
        btnLogin = findViewById(R.id.btnLogin);
        btnGoogle = findViewById(R.id.btnGoogle);
        textViewRegister = findViewById(R.id.textViewRegister);
        textInputLayoutEmail = findViewById(R.id.textInputLayoutEmail);
        textInputLayoutPassword = findViewById(R.id.textInputLayoutPassword);
        progressBarLogin = findViewById(R.id.progressBarLogin);
        progressBarLogin.setVisibility(View.GONE);

        loginViewModel = ViewModelProviders.of(this).get(LoginViewModel.class);

    }

    private void signIn() {
        Intent intent = googleSignInClient.getSignInIntent();
        startActivityForResult(intent, RESULT_GOOGLE);
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
        super.onActivityResult(requestCode, resultCode, data);

        if (requestCode == RESULT_GOOGLE) {
            // The Task returned from this call is always completed, no need to attach a listener.
            Task<GoogleSignInAccount> task = GoogleSignIn.getSignedInAccountFromIntent(data);

            try {

                GoogleSignInAccount account = task.getResult(ApiException.class);
                authWithGoogle(account);

            } catch (ApiException e) {

                e.printStackTrace();

            }
        }
    }

    private void authWithGoogle(GoogleSignInAccount account) {
        AuthCredential credential = GoogleAuthProvider.getCredential(account.getIdToken(), null);

        firebaseAuth.signInWithCredential(credential)
                .addOnCompleteListener(this, task -> {
                    if (task.isSuccessful()) {
                        goToHome();
                    } else {
                        Toast.makeText(getApplicationContext(), "Auth Error", Toast.LENGTH_SHORT).show();
                    }
                });
    }

    private void goToHome() {
        // Sign in success, update UI with the signed-in user's information
        startActivity(new Intent(getApplicationContext(), HomeActivity.class));
        finish();
    }

    @Override
    public void onConnectionFailed(@NonNull ConnectionResult connectionResult) {
        Toast.makeText(getApplicationContext(), "Authentication Error", Toast.LENGTH_SHORT).show();
    }


    @Override
    protected void onStart() {
        super.onStart();
        firebaseAuth.addAuthStateListener(authStateListener);
    }

    @Override
    protected void onStop() {
        super.onStop();
        if (authStateListener != null) {
            firebaseAuth.removeAuthStateListener(authStateListener);
        }
    }
}