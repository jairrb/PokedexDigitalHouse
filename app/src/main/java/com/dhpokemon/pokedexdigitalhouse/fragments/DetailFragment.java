package com.dhpokemon.pokedexdigitalhouse.fragments;


import android.content.Intent;
import android.graphics.Color;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.ProgressBar;
import android.widget.TextView;

import androidx.appcompat.widget.ShareActionProvider;
import androidx.fragment.app.Fragment;
import androidx.lifecycle.ViewModelProviders;

import com.dhpokemon.pokedexdigitalhouse.R;
import com.dhpokemon.pokedexdigitalhouse.model.pokemon.Pokemon;
import com.dhpokemon.pokedexdigitalhouse.model.species.Specie;
import com.dhpokemon.pokedexdigitalhouse.viewmodel.SpeciesViewModel;
import com.google.android.material.snackbar.Snackbar;
import com.squareup.picasso.Picasso;

/**
 * A simple {@link Fragment} subclass.
 */
public class DetailFragment extends Fragment {
    private SpeciesViewModel speciesViewModel;
    private LinearLayout linearLayoutDetail;
    private ImageView imageViewFavorite;
    private ImageView imageViewShare;
    private ImageView imageViewDetail;
    private ProgressBar progressBarDetail;
    private TextView textViewName;
    private TextView textViewEggGroup;
    private TextView textViewGeneration;
    private TextView textViewGrowth;
    private TextView textViewHabitat;
    private TextView textViewShape;


    public DetailFragment() {
        // Required empty public constructor
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment

        View view = inflater.inflate(R.layout.fragment_detail, container, false);
        initViews(view);

        if (getArguments() != null) {
            Pokemon pokemon = getArguments().getParcelable("POKEMON");

            if (pokemon != null) {
                //Inicializa ViewModel
                speciesViewModel = ViewModelProviders.of(this).get(SpeciesViewModel.class);
                speciesViewModel.getSpecie(pokemon.getId().intValue());

                //Observable
                speciesViewModel.getSpecieLiveData()
                        .observe(this, specie -> refreshViews(specie));

                //Observable Loading
                speciesViewModel.getLoadingLiveData()
                        .observe(this, isLoading -> {
                            if (isLoading) {
                                progressBarDetail.setVisibility(View.VISIBLE);
                            } else {
                                progressBarDetail.setVisibility(View.GONE);
                            }
                        });

                //Observable Error
                speciesViewModel.getErrorLiveData()
                        .observe(this, throwable -> {
                            Snackbar.make(imageViewDetail, throwable.getMessage(), Snackbar.LENGTH_LONG).show();
                        });

                Picasso
                        .get()
                        .load("https://pokeres.bastionbot.org/images/pokemon/"+pokemon.getId()+".png")
                        .placeholder(R.drawable.defaultpokemon)
                        .error(R.drawable.defaultpokemon)
                        .into(imageViewDetail);


                //compartilhamento do Pokemon
                imageViewShare.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        compartilharPokemon(pokemon.getId()+".png");
                    }
                });

            }
        }
        return view;
    }

    private void initViews(View view) {
        linearLayoutDetail = view.findViewById(R.id.linearLayoutDetail);
        imageViewFavorite = view.findViewById(R.id.imageViewFavorite);
        imageViewShare = view.findViewById(R.id.imageViewShare);
        imageViewDetail = view.findViewById(R.id.imageViewDetail);
        progressBarDetail = view.findViewById(R.id.progressBarDetail);
        textViewName = view.findViewById(R.id.textViewName);
        textViewEggGroup = view.findViewById(R.id.textViewEggGroup);
        textViewGeneration = view.findViewById(R.id.textViewGeneration);
        textViewGrowth = view.findViewById(R.id.textViewGrowth);
        textViewHabitat = view.findViewById(R.id.textViewHabitat);
        textViewShape = view.findViewById(R.id.textViewShape);
    }

    private void refreshViews(Specie specie) {
        if (specie != null) {
            textViewName.setText(specie.getName());
            textViewEggGroup.setText(specie.toStringEggGroups());
            textViewGeneration.setText(specie.getGeneration().getName());
            textViewGrowth.setText(specie.getGrowthRate().getName());
            textViewHabitat.setText(specie.getHabitat().getName());
            textViewShape.setText(specie.getShape().getName());


            switch(specie.getColor().getName()){
                case "blue":
                    linearLayoutDetail.setBackgroundColor(getResources().getColor(R.color.pokemonBlue));
                    break;
                case "brown":
                    linearLayoutDetail.setBackgroundColor(getResources().getColor(R.color.pokemonBrown));
                    break;
                case "gray":
                    linearLayoutDetail.setBackgroundColor(getResources().getColor(R.color.pokemonGray));
                    break;
                case "green":
                    linearLayoutDetail.setBackgroundColor(getResources().getColor(R.color.pokemonGreen));
                    break;
                case "pink":
                    linearLayoutDetail.setBackgroundColor(getResources().getColor(R.color.pokemonPink));
                    break;
                case "purple":
                    linearLayoutDetail.setBackgroundColor(getResources().getColor(R.color.pokemonPurple));
                    break;
                case "red":
                    linearLayoutDetail.setBackgroundColor(getResources().getColor(R.color.pokemonRed));
                    break;
                case "white":
                    linearLayoutDetail.setBackgroundColor(getResources().getColor(R.color.pokemonWhite));
                    break;
                case "yellow":
                    linearLayoutDetail.setBackgroundColor(getResources().getColor(R.color.pokemonYellow));
                    break;
                default:
                    linearLayoutDetail.setBackgroundColor(getResources().getColor(R.color.pokemonDefault));
            }
        }
    }

    private void compartilharPokemon(String pokemon) {

        imageViewShare.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                //ShareActionProvider mActionProvider = null;
                String textoCompartilhado;

                textoCompartilhado = "\"https://pokeres.bastionbot.org/images/pokemon/"+ pokemon + "\\" + "\n" +
                        "\nName:        " + textViewName.getText().toString() + "\n" +
                        "\nEgg Group:   " + textViewEggGroup.getText().toString() + "\n" +
                        "\nGeneration:  " + textViewGeneration.getText().toString() + "\n" +
                        "\nGrowth:      " + textViewGrowth.getText().toString() + "\n" +
                        "\nHabitat:     " + textViewHabitat.getText().toString() + "\n" +
                        "\nShape:       " + textViewShape.getText().toString()
                ;


                //Acao de envio na intencao de chamar outra Actitivity
                Intent intentCompartilhar = new Intent(Intent.ACTION_SEND);

                //Envia texto no compartilhamento
                intentCompartilhar.putExtra(Intent.EXTRA_SUBJECT,"Take a look at my Pokemon !");
                intentCompartilhar.putExtra(Intent.EXTRA_TEXT, textoCompartilhado);
                //mActionProvider.setShareIntent(intentCompartilhar);

                //tipo de compartilhamento
                intentCompartilhar.setType("text/plain");
                //intentCompartilhar.addFlags(Intent.FLAG_ACTIVITY_RESET_TASK_IF_NEEDED);

                //Mostra os aplicativos disponiveis para compartilhamento de dados
                Intent intentChooser = Intent.createChooser(
                        intentCompartilhar, "Share via:");

                //Start na Activity de compartilhamento
                startActivity(intentChooser);
            }
        });
    }

}