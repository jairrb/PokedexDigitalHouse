package com.dhpokemon.pokedexdigitalhouse.fragments;


import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.ProgressBar;
import android.widget.TextView;

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
    private ImageView imageViewDetail;
    private SpeciesViewModel speciesViewModel;
    private ProgressBar progressBarDetail;
    private TextView textViewColor;
    private TextView textViewName;

    public DetailFragment() {
        // Required empty public constructor
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment

        View view = inflater.inflate(R.layout.fragment_detail, container, false);
        imageViewDetail = view.findViewById(R.id.imageViewDetail);
        progressBarDetail = view.findViewById(R.id.progressBarDetail);
        textViewColor = view.findViewById(R.id.textViewColor);
        textViewName = view.findViewById(R.id.textViewName);

        if (getArguments() != null) {
            Pokemon pokemon = getArguments().getParcelable("POKEMON");

            if (pokemon != null) {
                Picasso
                        .get()
                        .load("https://pokeres.bastionbot.org/images/pokemon/"+pokemon.getId()+".png")
                        .placeholder(R.drawable.defaultpokemon)
                        .error(R.drawable.defaultpokemon)
                        .into(imageViewDetail);

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
            }
        }
        return view;
    }

    private void refreshViews(Specie specie) {
        if (specie != null) {
            textViewColor.setText(specie.getColor().getName());
        }
    }
}
