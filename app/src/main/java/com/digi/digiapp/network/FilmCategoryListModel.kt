package com.digi.digiapp.network

data class FilmCategoryListModel(val genres: ArrayList<Genre>)
data class Genre(val id:String,val name:String)