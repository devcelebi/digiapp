package com.digi.digiapp.network

data class FilmInfoList(val results:ArrayList<FilmInfo>)
data class FilmInfo(val id:String,val title:String,val poster_path:String)
