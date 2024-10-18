package com.example.galeriapublica;

import android.app.Application;

import androidx.annotation.NonNull;
import androidx.lifecycle.AndroidViewModel;
import androidx.lifecycle.LiveData;
import androidx.lifecycle.ViewModelKt;
import androidx.paging.PagingData;

import kotlinx.coroutines.CoroutineScope;

//MainViewModel herda de AndroidViewModel e nao de ViewModel
    public class MainViewModel extends AndroidViewModel {

     //guarda a opcao escolhida -do tipo inteiro- escolhida pelo usuario no menu btNav
    int navigationOpSelected = R.id.gridViewOp;

    //parametro de entrada em seu construtor
    public MainViewModel(@NonNull Application application) {
        super(application);
    }

    //pegar e setar valor escolhido
    public int getNavigationOpSelected() {
        return navigationOpSelected;
    }

        public void setNavigationOpSelected(int navigationOpSelected) {
            this.navigationOpSelected = navigationOpSelected;
        }

        LiveData<PagingData<ImageData>> pageLv;

        public MainViewModel(@NonNull Application application) {
            super(application);
            GalleryRepository galleryRepository = new GalleryRepository(application);
            GalleryPagingSource(galleryRepository);
            Pager<Integer, ImageData> pager = new Pager(new PagingConfig(10), () -> galleryRepository);
            CoroutineScope viewModelScope = ViewModelKt.getViewModelScope(this);
    }

    public LiveData<PagingData<ImageData>> getPageLv() {
            return pageLv;
    }

    }
