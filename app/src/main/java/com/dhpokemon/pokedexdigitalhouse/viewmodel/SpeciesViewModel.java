package com.dhpokemon.pokedexdigitalhouse.viewmodel;

import android.app.Application;

import androidx.annotation.NonNull;
import androidx.lifecycle.AndroidViewModel;
import androidx.lifecycle.LiveData;
import androidx.lifecycle.MutableLiveData;

import com.dhpokemon.pokedexdigitalhouse.model.species.Specie;
import com.dhpokemon.pokedexdigitalhouse.repository.PokemonRepository;

import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.disposables.CompositeDisposable;
import io.reactivex.schedulers.Schedulers;

import static com.dhpokemon.pokedexdigitalhouse.util.AppUtil.isNetworkConnected;

public class SpeciesViewModel extends AndroidViewModel{
    private MutableLiveData<Specie> specieLiveData = new MutableLiveData<>();
    private MutableLiveData<Boolean> loadingLiveData = new MutableLiveData<>();
    private MutableLiveData<Throwable> errorLiveData = new MutableLiveData<>();
    private CompositeDisposable disposable = new CompositeDisposable();

    private PokemonRepository repository = new PokemonRepository();


    public SpeciesViewModel(@NonNull Application application) {super(application);}
    public LiveData<Specie> getSpecieLiveData() {
        return specieLiveData;
    }
    public LiveData<Boolean> getLoadingLiveData() {
        return loadingLiveData;
    }
    public LiveData<Throwable> getErrorLiveData() {
        return errorLiveData;
    }


    public void getSpecie(int id) {
        if (isNetworkConnected(getApplication())) {
            getApiSpecie(id);
        }
    }

    private void getApiSpecie(int id) {
        disposable.add(repository.getSpeciesApi(id)
                .subscribeOn(Schedulers.newThread())
                .observeOn(AndroidSchedulers.mainThread())
                .doOnSubscribe(disposable1 -> loadingLiveData.setValue(true))
                .doAfterTerminate(() -> loadingLiveData.setValue(false))
                .subscribe(specieResponse -> specieLiveData.setValue(specieResponse.getSpecies())
                        , throwable -> errorLiveData.setValue(throwable))
        );
    }

    //Clear call's RX
    @Override
    protected void onCleared() {
        super.onCleared();
        disposable.clear();
    }
}
