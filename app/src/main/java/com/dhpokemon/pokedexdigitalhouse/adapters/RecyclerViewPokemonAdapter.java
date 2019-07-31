package com.dhpokemon.pokedexdigitalhouse.adapters;

import android.app.Activity;
import android.content.Intent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.core.app.ActivityOptionsCompat;
import androidx.recyclerview.widget.RecyclerView;

import com.dhpokemon.pokedexdigitalhouse.model.info.InfoResponse;
import com.dhpokemon.pokedexdigitalhouse.model.pokemon.Pokemon;
import com.dhpokemon.pokedexdigitalhouse.R;
import com.dhpokemon.pokedexdigitalhouse.view.DetalheActivity;
import com.squareup.picasso.Picasso;

import java.util.List;

public class RecyclerViewPokemonAdapter extends RecyclerView.Adapter<RecyclerViewPokemonAdapter.ViewHolder> {
    private List<Pokemon> pokemons;
    private List<InfoResponse> infoResponses;

    public RecyclerViewPokemonAdapter(List<Pokemon> pokemons) {
        this.pokemons = pokemons;
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
         Pokemon result = pokemons.get(position);
        viewHolder.bind(result);
        viewHolder.itemView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {


                String transitionName = "image" + position;
                Intent intent = new Intent(viewHolder.itemView.getContext(), DetalheActivity.class);
                intent.putExtra("pokemon",true);
                intent.putExtra("transitionName", transitionName);

                viewHolder.imageViewPokemon.setTransitionName(transitionName);



                ActivityOptionsCompat options = ActivityOptionsCompat.
                        makeSceneTransitionAnimation((Activity) viewHolder.itemView.getContext(),
                                viewHolder.imageViewPokemon, transitionName);

                viewHolder.itemView.getContext().startActivity(intent, options.toBundle());

            }
        });
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

            Picasso
                    .get()
                    //.load("https://raw.githubusercontent.com/PokeAPI/sprites/master/sprites/pokemon/"+pokemon.getId()+".png")
                    .load("https://pokeres.bastionbot.org/images/pokemon/"+pokemon.getId()+".png")
                    .placeholder(R.drawable.defaultpokemon)
                    .error(R.drawable.defaultpokemon)
                    .into(imageViewPokemon);
        }
    }

    public void update(List<Pokemon> pokemons) {
        if (pokemons != null) {
            this.pokemons = pokemons;
            notifyDataSetChanged();
        }
    }
}