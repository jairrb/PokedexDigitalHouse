package com.dhpokemon.pokedexdigitalhouse.view;

import android.os.Bundle;

import com.dhpokemon.pokedexdigitalhouse.model.pokemon.Pokemon;
import com.google.android.material.floatingactionbutton.FloatingActionButton;
import com.google.android.material.snackbar.Snackbar;

import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;

import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

import com.dhpokemon.pokedexdigitalhouse.R;
import com.squareup.picasso.Picasso;

public class DetalheActivity extends AppCompatActivity {

    private ImageView imageDetalhe;
    private TextView stats;
    private TextView nomePokemon;
    private Pokemon pokemonResult;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_detalhe);
        initViews();

        pokemonResult=getIntent().getParcelableExtra("pokemon");

        String transitionName = getIntent().getStringExtra("transitionName");
        imageDetalhe.setTransitionName(transitionName);

        nomePokemon.setText(pokemonResult.getName());




    }

    private void initViews() {
        imageDetalhe=findViewById(R.id.imagePokemoDetalhe);
        stats=findViewById(R.id.textStats);
        nomePokemon=findViewById(R.id.pokemonName);
    }

}
