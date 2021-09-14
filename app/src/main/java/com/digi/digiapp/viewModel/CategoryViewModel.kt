package com.digi.digiapp.viewModel

import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.digi.digiapp.network.ApiInstance
import com.digi.digiapp.network.ApiService
import com.digi.digiapp.network.FilmInfoList
import io.reactivex.Observer
import io.reactivex.android.schedulers.AndroidSchedulers
import io.reactivex.disposables.Disposable
import io.reactivex.schedulers.Schedulers

class CategoryViewModel : ViewModel() {
    var  filmInfoList: MutableLiveData<FilmInfoList> = MutableLiveData()

    fun getFilmInfo():MutableLiveData<FilmInfoList>{
        return filmInfoList
    }

    fun makeApiCall(apiKey:String,genreId:String){
        val apiInstance = ApiInstance.getApiInstance().create(ApiService::class.java)
        apiInstance.getFilmByGenreId(apiKey,genreId)
            .subscribeOn(Schedulers.io())
            .observeOn(AndroidSchedulers.mainThread())
            .subscribe(getFilmInfoListObserver())

    }

    private fun getFilmInfoListObserver(): Observer<FilmInfoList> {
        return object : Observer<FilmInfoList>{
            override fun onComplete() {

            }

            override fun onError(e: Throwable) {
              filmInfoList.postValue(null)
            }

            override fun onNext(t: FilmInfoList) {
                filmInfoList.postValue(t)
            }

            override fun onSubscribe(d: Disposable) {

            }
        }
    }
}