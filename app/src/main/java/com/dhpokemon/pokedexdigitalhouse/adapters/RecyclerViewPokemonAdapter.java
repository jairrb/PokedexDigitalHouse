package com.dhpokemon.pokedexdigitalhouse.adapters;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.dhpokemon.pokedexdigitalhouse.R;
import com.dhpokemon.pokedexdigitalhouse.interfaces.RecyclerViewClickListener;
import com.dhpokemon.pokedexdigitalhouse.model.pokemon.Pokemon;
import com.squareup.picasso.Picasso;

import java.util.List;

public class RecyclerViewPokemonAdapter extends RecyclerView.Adapter<RecyclerViewPokemonAdapter.ViewHolder> {
    private List<Pokemon> pokemons;
    private RecyclerViewClickListener listener;

    public RecyclerViewPokemonAdapter(List<Pokemon> pokemons, RecyclerViewClickListener listener) {
        this.pokemons = pokemons;
        this.listener = listener;
    }


    @NonNull
    @Override
    public RecyclerViewPokemonAdapter.ViewHolder onCreateViewHolder(@NonNull ViewGroup viewGroup, int viewType) {
        View view = LayoutInflater
                .from(viewGroup.getContext())
                .inflate(R.layout.recyclerview_pokemon_item, viewGroup, false);

        return new ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull RecyclerViewPokemonAdapter.ViewHolder viewHolder, int position) {
        final Pokemon pokemon = pokemons.get(position);
        viewHolder.bind(pokemon);
        viewHolder.itemView.setOnClickListener(v -> listener.onItemClick(pokemon));
    }

    @Override
    public int getItemCount() {
        return pokemons.size();
    }


    public class ViewHolder extends RecyclerView.ViewHolder {

        private ImageView imageViewPokemon;
        private TextView textViewName;
        private TextView textViewId;

        public ViewHolder(@NonNull View itemView) {
            super(itemView);
            textViewName = itemView.findViewById(R.id.textViewName);
            textViewId = itemView.findViewById(R.id.textViewId);
            imageViewPokemon = itemView.findViewById(R.id.imageViewPokemon);

        }

        public void bind(Pokemon pokemon) {
            if (pokemon.getId() != null) {
                textViewName.setText(pokemon.getName());
                textViewId.setText("#" + pokemon.getId().toString());

                Picasso
                        .get()
                        .load("https://pokeres.bastionbot.org/images/pokemon/" + pokemon.getId() + ".png") //.load("https://raw.githubusercontent.com/PokeAPI/sprites/master/sprites/pokemon/"+pokemon.getId()+".png")
                        .error(R.drawable.defaultpokemon)
                        .resize(150, 150)
                        .centerCrop()
                        .placeholder(R.drawable.defaultpokemon)
                        .into(imageViewPokemon);
            }
        }
    }

    public void update(List<Pokemon> pokemons) {

        if (pokemons.size() == 0) {
            this.pokemons = pokemons;
        } else {
            this.pokemons.addAll(pokemons);
            notifyDataSetChanged();
        }
    }
}