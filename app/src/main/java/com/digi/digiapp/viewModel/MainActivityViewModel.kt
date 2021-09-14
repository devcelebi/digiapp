package com.digi.digiapp.viewModel

import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.digi.digiapp.network.ApiInstance
import com.digi.digiapp.network.ApiService
import com.digi.digiapp.network.FilmCategoryListModel
import com.digi.digiapp.network.Genre
import io.reactivex.Observer
import io.reactivex.android.schedulers.AndroidSchedulers
import io.reactivex.disposables.Disposable
import io.reactivex.schedulers.Schedulers

class MainActivityViewModel : ViewModel() {
    var  filmCategoryList: MutableLiveData<FilmCategoryListModel> = MutableLiveData()

    fun getFilmCategories():MutableLiveData<FilmCategoryListModel>{
        return filmCategoryList
    }

    fun makeApiCall(apiKey:String){
        val apiInstance = ApiInstance.getApiInstance().create(ApiService::class.java)
        apiInstance.getFilmCategories(apiKey)
            .subscribeOn(Schedulers.io())
            .observeOn(AndroidSchedulers.mainThread())
            .subscribe(getFilmCategoryListObserver())
    }

    private fun getFilmCategoryListObserver(): Observer<FilmCategoryListModel> {
        return object : Observer<FilmCategoryListModel>{
            override fun onComplete() {

            }

            override fun onError(e: Throwable) {
                filmCategoryList.postValue(null)
            }

            override fun onNext(t: FilmCategoryListModel) {
                filmCategoryList.postValue(t)
            }

            override fun onSubscribe(d: Disposable) {

            }

        }
    }
}