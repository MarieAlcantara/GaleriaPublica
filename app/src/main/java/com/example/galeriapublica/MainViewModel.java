package com.example.galeriapublica;

import android.app.Application;

import androidx.annotation.NonNull;
import androidx.lifecycle.AndroidViewModel;
import androidx.lifecycle.LiveData;
import androidx.lifecycle.ViewModelKt;
import androidx.paging.Pager;
import androidx.paging.PagingConfig;
import androidx.paging.PagingData;
import androidx.paging.PagingLiveData;

import kotlinx.coroutines.CoroutineScope;

//MainViewModel herda de AndroidViewModel e nao de ViewModel
public class MainViewModel extends AndroidViewModel {

     //guarda a opcao escolhida -do tipo inteiro- escolhida pelo usuario no menu btNav
    int navigationOpSelected = R.id.gridViewOp;

    LiveData<PagingData<ImageData>> pageLv;

    public MainViewModel(@NonNull Application application) {
        super(application);
        GalleryRepository galleryRepository = new GalleryRepository(application);
        GalleryPagingSource galleryPagingSource = new GalleryPagingSource(galleryRepository);
        Pager<Integer, ImageData> pager = new Pager(new PagingConfig(10), () -> galleryPagingSource);
        CoroutineScope viewModelScope = ViewModelKt.getViewModelScope(this);
        pageLv = PagingLiveData.cachedIn(PagingLiveData.getLiveData(pager), viewModelScope);

    }

    //pegar e setar valor escolhido
    public int getNavigationOpSelected() {
        return navigationOpSelected;
    }

    public void setNavigationOpSelected(int navigationOpSelected) {
        this.navigationOpSelected = navigationOpSelected;
    }

    public LiveData<PagingData<ImageData>> getPageLv() {
            return pageLv;
    }

}
