package com.dhpokemon.pokedexdigitalhouse.fragments;

import android.content.ActivityNotFoundException;
import android.content.Intent;
import android.graphics.Bitmap;
import android.net.Uri;
import android.os.Bundle;
import android.os.Environment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.ProgressBar;
import android.widget.TextView;
import android.widget.Toast;

import androidx.fragment.app.Fragment;
import androidx.lifecycle.ViewModelProviders;

import com.dhpokemon.pokedexdigitalhouse.R;
import com.dhpokemon.pokedexdigitalhouse.model.pokemon.Pokemon;
import com.dhpokemon.pokedexdigitalhouse.model.species.Specie;
import com.dhpokemon.pokedexdigitalhouse.viewmodel.SpeciesViewModel;
import com.google.android.material.snackbar.Snackbar;
import com.squareup.picasso.Picasso;

import java.io.File;
import java.io.FileOutputStream;

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
    private TextView textViewFlavor;
    private TextView textViewEggGroup;
    private TextView textViewGeneration;
    private TextView textViewGrowth;
    private TextView textViewHabitat;
    private TextView textViewShape;

    private Boolean isFavorite = false;

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


                speciesViewModel.isFavoriteFirebase(pokemon);
                speciesViewModel.isFavoriteFirebase
                        .observe(this, isFavorite -> refreshFavView(isFavorite));

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
                        .load("https://pokeres.bastionbot.org/images/pokemon/" + pokemon.getId() + ".png")
                        .placeholder(R.drawable.defaultpokemon)
                        .error(R.drawable.defaultpokemon)
                        .fit()
                        .into(imageViewDetail);

                imageViewShare.setOnClickListener(v -> {
                    Boolean isSDPresent = android.os.Environment.getExternalStorageState().equals(android.os.Environment.MEDIA_MOUNTED);
                    Boolean isSDSupportedDevice = Environment.isExternalStorageRemovable();
                    File filebm = null;

                    if (isSDSupportedDevice && isSDPresent) {
                        View rootView = getActivity().getWindow().getDecorView().findViewById(android.R.id.content);
                        Bitmap sharebm = getScreenShot(rootView);
                        filebm = store(sharebm, pokemon.getName());
                    }

                    if (filebm != null) {
                        shareImage(filebm);
                    } else {
                        shareLinkPokemon(pokemon);
                    }
                });

                imageViewFavorite.setOnClickListener(v -> speciesViewModel.favoritePokemon(pokemon,isFavorite));
                speciesViewModel.favoriteAdded.observe(this, result -> {
                    if (result != null) {
                        Snackbar.make(imageViewFavorite, result.getName()+" "+ getString(R.string.detail_addfavorites), Snackbar.LENGTH_LONG).show();
                    } else {
                        Snackbar.make(imageViewFavorite, pokemon.getName()+" "+ getString(R.string.detail_remfavorites), Snackbar.LENGTH_LONG).show();
                    }
                });

            }
        }
        return view;
    }

    private void refreshFavView(Boolean result) {
        if (result) {
            isFavorite = true;
            imageViewFavorite.setImageResource(R.drawable.ic_favorite_fav);
        } else {
            imageViewFavorite.setImageResource(R.drawable.ic_favorite);
            isFavorite = false;
        }
    }

    private void shareLinkPokemon(Pokemon pokemon) {
        Intent intentShare = new Intent(Intent.ACTION_SEND);

        //Envia texto no compartilhamento
        intentShare.putExtra(Intent.EXTRA_TEXT, getString(R.string.detail_pokedexdh) + "\n" +
                "\nPokemon: " + pokemon.getName() +
                "\nhttps://pokeres.bastionbot.org/images/pokemon/" + pokemon.getId() + ".png");

        //tipo de compartilhamento
        intentShare.setType("text/plain");

        //Mostra os aplicativos disponiveis para compartilhamento de dados
        Intent intentChooser = Intent.createChooser(
                intentShare, getString(R.string.detail_sharewith));

        //Start na Activity de compartilhamento
        startActivity(intentChooser);
    }

    private void initViews(View view) {
        linearLayoutDetail = view.findViewById(R.id.linearLayoutDetail);
        imageViewFavorite = view.findViewById(R.id.imageViewFavorite);
        imageViewShare = view.findViewById(R.id.imageViewShare);
        imageViewDetail = view.findViewById(R.id.imageViewDetail);
        progressBarDetail = view.findViewById(R.id.progressBarDetail);
        textViewName = view.findViewById(R.id.textViewName);
        textViewFlavor = view.findViewById(R.id.textViewFlavor);
        textViewEggGroup = view.findViewById(R.id.textViewEggGroup);
        textViewGeneration = view.findViewById(R.id.textViewGeneration);
        textViewGrowth = view.findViewById(R.id.textViewGrowth);
        textViewHabitat = view.findViewById(R.id.textViewHabitat);
        textViewShape = view.findViewById(R.id.textViewShape);
    }

    private static Bitmap getScreenShot(View view) {
        View screenView = view.getRootView();
        screenView.setDrawingCacheEnabled(true);
        Bitmap bitmap = Bitmap.createBitmap(screenView.getDrawingCache());
        screenView.setDrawingCacheEnabled(false);
        return bitmap;
    }

    public static File store(Bitmap bm, String fileName) {
        final String dirPath = Environment.getExternalStorageDirectory().getAbsolutePath() + "/Screenshots";
        File dir = new File(dirPath);
        if (!dir.exists())
            dir.mkdirs();
        File file = new File(dirPath, fileName);
        try {
            FileOutputStream fOut = new FileOutputStream(file);
            bm.compress(Bitmap.CompressFormat.PNG, 85, fOut);
            fOut.flush();
            fOut.close();
            return dir;
        } catch (Exception e) {
            e.printStackTrace();
        }
        return null;
    }

    private void shareImage(File file) {
        Uri uri = Uri.fromFile(file);
        Intent intent = new Intent();
        intent.setAction(Intent.ACTION_SEND);
        intent.setType("images/*");

        intent.putExtra(android.content.Intent.EXTRA_SUBJECT, "");
        intent.putExtra(android.content.Intent.EXTRA_TEXT, "");
        intent.putExtra(Intent.EXTRA_STREAM, uri);
        try {
            startActivity(Intent.createChooser(intent, getString(R.string.detail_pokedexdh)));
        } catch (ActivityNotFoundException e) {
            Toast.makeText(getContext(), getString(R.string.detail_noapp), Toast.LENGTH_SHORT).show();
        }
    }

    private void refreshViews(Specie specie) {
        if (specie != null) {
            if (specie.getName() != null) {
                textViewName.setText(specie.getName());
            }
            if (specie.getFlavorTextEntries() != null) {
                for (int i = 0; i < specie.getFlavorTextEntries().size(); i++) {
                    if (specie.getFlavorTextEntries().get(i).getLanguage().getName().equals("en")) {
                        textViewFlavor.setText(specie.getFlavorTextEntries().get(i).getFlavorText());
                        break;
                    }
                }

            }
            if (specie.toStringEggGroups() != null) {
                textViewEggGroup.setText(specie.toStringEggGroups());
            }
            if (specie.getGeneration() != null) {
                textViewGeneration.setText(specie.getGeneration().getName());
            }
            if (specie.getGrowthRate() != null) {
                textViewGrowth.setText(specie.getGrowthRate().getName());
            }
            if (specie.getHabitat() != null) {
                textViewHabitat.setText(specie.getHabitat().getName());
            }
            if (specie.getShape() != null) {
                textViewShape.setText(specie.getShape().getName());
            }

            switch (specie.getColor().getName()) {
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
}