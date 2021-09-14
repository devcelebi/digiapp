package com.digi.digiapp.network

import io.reactivex.Observable
import retrofit2.http.GET
import retrofit2.http.Query

interface ApiService {

    @GET("genre/movie/list")
    fun getFilmCategories(@Query("api_key") query: String): Observable<FilmCategoryListModel>

    @GET("discover/movie")
    fun getFilmByGenreId(@Query("api_key") query: String,@Query("with_genres") genreId: String): Observable<FilmInfoList>
}