package com.dhpokemon.pokedexdigitalhouse.fragments;


import android.os.Bundle;

import androidx.fragment.app.Fragment;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;

import com.dhpokemon.pokedexdigitalhouse.R;
import com.dhpokemon.pokedexdigitalhouse.model.pokemon.Pokemon;
import com.squareup.picasso.Picasso;

/**
 * A simple {@link Fragment} subclass.
 */
public class DetailFragment extends Fragment {
    private ImageView imageViewDetail;

    public DetailFragment() {
        // Required empty public constructor
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment

        View view = inflater.inflate(R.layout.fragment_detail, container, false);
        imageViewDetail = view.findViewById(R.id.imageViewDetail);

        if (getArguments() != null) {
            Pokemon pokemon = getArguments().getParcelable("POKEMON");

            if (pokemon != null) {

                Picasso
                        .get()
                        .load("https://pokeres.bastionbot.org/images/pokemon/"+pokemon.getId()+".png")
                        .placeholder(R.drawable.defaultpokemon)
                        .error(R.drawable.defaultpokemon)
                        .into(imageViewDetail);
            }
        }
        return view;
    }

}
