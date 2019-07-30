package com.dhpokemon.pokedexdigitalhouse.viewmodel;
import android.app.Application;

import androidx.annotation.NonNull;
import androidx.lifecycle.AndroidViewModel;
import androidx.lifecycle.LiveData;
import androidx.lifecycle.MutableLiveData;

import com.dhpokemon.pokedexdigitalhouse.data.database.DataBase;
import com.dhpokemon.pokedexdigitalhouse.data.database.dao.PokemonDao;
import com.dhpokemon.pokedexdigitalhouse.model.pokemon.Pokemon;
import com.dhpokemon.pokedexdigitalhouse.model.pokemon.PokemonResponse;
import com.dhpokemon.pokedexdigitalhouse.repository.PokemonRepository;

import java.util.List;

import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.disposables.CompositeDisposable;
import io.reactivex.schedulers.Schedulers;

import static com.dhpokemon.pokedexdigitalhouse.util.AppUtil.isNetworkConnected;

public class PokemonViewModel extends AndroidViewModel {
    private MutableLiveData<List<Pokemon>> pokemonLiveData = new MutableLiveData<>();
    private MutableLiveData<Boolean> loadingLiveData = new MutableLiveData<>();
    private MutableLiveData<Throwable> errorLiveData = new MutableLiveData<>();
    private CompositeDisposable disposable = new CompositeDisposable();

    private PokemonRepository repository = new PokemonRepository();

    public PokemonViewModel(@NonNull Application application) {
        super(application);
    }

    public LiveData<List<Pokemon>> getPokemonLiveData() {
        return pokemonLiveData;
    }

    public LiveData<Boolean> getLoadingLiveData() {
        return loadingLiveData;
    }

    public LiveData<Throwable> getErrorLiveData() {
        return errorLiveData;
    }


    public void getPokemon(int offset, int limit) {
        if (isNetworkConnected(getApplication())) {
            getApiPokemon(offset,limit);
        } else {
            getLocalPokemon();
        }
    }

    private void getApiPokemon(int offset, int limit) {
        disposable.add(repository.getPokemonApi(offset,limit)
                .subscribeOn(Schedulers.newThread())
                .map(pokemonResponse -> saveItems(pokemonResponse))
                .observeOn(AndroidSchedulers.mainThread())
                .doOnSubscribe(disposable1 -> loadingLiveData.setValue(true))
                .doAfterTerminate(() -> loadingLiveData.setValue(false))
                .subscribe(pokemonResponse -> pokemonLiveData.setValue(pokemonResponse.getResults())
                        , throwable -> errorLiveData.setValue(throwable))
        );
    }

    private void getLocalPokemon() {
        disposable.add(
                repository.getPokemonDatabase(getApplication().getApplicationContext())
                        .subscribeOn(Schedulers.newThread())
                        .observeOn(AndroidSchedulers.mainThread())
                        .doOnSubscribe(disposable1 -> loadingLiveData.setValue(false))
                        .doAfterTerminate(() -> loadingLiveData.setValue(false))
                        .subscribe(pokemons -> pokemonLiveData.setValue(pokemons)
                                , throwable -> errorLiveData.setValue(throwable))
        );
    }

    private PokemonResponse saveItems(PokemonResponse pokemonResponse) {
        PokemonDao pokemonDao = DataBase.getDatabase(getApplication()
                .getApplicationContext())
                .pokemonDao();
        pokemonDao.insertAll(pokemonResponse.getResults());
        return pokemonResponse;
    }

    //Limpa as chamadas que fizemos no RX
    @Override
    protected void onCleared() {
        super.onCleared();
        disposable.clear();
    }
}