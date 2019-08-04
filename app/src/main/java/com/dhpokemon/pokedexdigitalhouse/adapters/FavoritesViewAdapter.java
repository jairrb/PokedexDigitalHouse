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
        View view = LayoutInflater.from(viewGroup.getContext()).inflate(R.layout.fragment_detail, viewGroup, false);
        return new ViewHolder(view);

    }

    @Override
    public void onBindViewHolder(@NonNull FavoritesViewAdapter.ViewHolder viewHolder, int position) {
        Pokemon pokemon = pokemons.get(position);
        viewHolder.bind(pokemon);

        viewHolder.imageViewFavorite.setOnClickListener(v -> favoriteItemClick.removeFavoriteClickListener(pokemon));



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
        private LinearLayout linearLayoutDetail;
        private ImageView imageViewDetail;
        private ImageView imageViewFavorite;
        private TextView textViewName;
        private TextView textViewEggGroup;
        private TextView textViewGeneration;
        private TextView textViewGrowth;
        private TextView textViewHabitat;
        private TextView textViewShape;


        public ViewHolder(@NonNull View itemView) {
            super(itemView);

            linearLayoutDetail = itemView.findViewById(R.id.linearLayoutDetail);
            imageViewDetail = itemView.findViewById(R.id.imageViewDetail);
            imageViewFavorite = itemView.findViewById(R.id.imageViewFavorite);
            textViewName = itemView.findViewById(R.id.textViewName);
            textViewEggGroup = itemView.findViewById(R.id.textViewEggGroup);
            textViewGeneration = itemView.findViewById(R.id.textViewGeneration);
            textViewGrowth = itemView.findViewById(R.id.textViewGrowth);
            textViewHabitat = itemView.findViewById(R.id.textViewHabitat);
            textViewShape = itemView.findViewById(R.id.textViewShape);


        }

        public void bind(Pokemon pokemon) {

            textViewName.setText(pokemon.getName());

        }
    }
}
