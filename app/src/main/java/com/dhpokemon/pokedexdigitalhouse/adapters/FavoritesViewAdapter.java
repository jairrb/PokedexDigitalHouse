package com.dhpokemon.pokedexdigitalhouse.adapters;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.dhpokemon.pokedexdigitalhouse.R;
import com.dhpokemon.pokedexdigitalhouse.interfaces.FavoriteItemClick;
import com.dhpokemon.pokedexdigitalhouse.model.pokemon.Pokemon;
import com.squareup.picasso.Picasso;

import java.util.List;

public class FavoritesViewAdapter extends RecyclerView.Adapter<FavoritesViewAdapter.ViewHolder> {

    private List<Pokemon> pokemons;
    private FavoriteItemClick favoriteItemClick;


    public FavoritesViewAdapter(List<Pokemon> results, FavoriteItemClick favoriteItemClick) {
        this.pokemons = results;
        this.favoriteItemClick = favoriteItemClick;
    }

    @NonNull
    @Override
    public FavoritesViewAdapter.ViewHolder onCreateViewHolder(@NonNull ViewGroup viewGroup, int viewType) {
        View view = LayoutInflater
                .from(viewGroup.getContext())
                .inflate(R.layout.recyclerview_pokemon_item, viewGroup, false);

        return new ViewHolder(view);

    }

    @Override
    public void onBindViewHolder(@NonNull FavoritesViewAdapter.ViewHolder viewHolder, int position) {
        Pokemon pokemon = pokemons.get(position);
        viewHolder.bind(pokemon);



    }

    //método que atualiza a lista do adapter
    public void update(List<Pokemon> pokemons) {
        this.pokemons = pokemons;
        notifyDataSetChanged();
    }

    public void removeItem(Pokemon pokemon){
        pokemons.remove(pokemon);
        notifyDataSetChanged();
    }

    @Override
    public int getItemCount() {
        return pokemons.size();
    }

    public class ViewHolder extends RecyclerView.ViewHolder {
        private ImageView imageViewPokemon;
        private TextView textViewName;


        public ViewHolder(@NonNull View itemView) {
            super(itemView);

            textViewName = itemView.findViewById(R.id.textViewName);
            imageViewPokemon = itemView.findViewById(R.id.imageViewPokemon);

        }

        public void bind(Pokemon pokemon) {

            textViewName.setText(pokemon.getName());

            Picasso.get().load("https://pokeres.bastionbot.org/images/pokemon/" + pokemon.getId() + ".png").into(imageViewPokemon);

        }
    }
}
