package com.olcertugba.yemekkitabi.Service

import com.olcertugba.yemekkitabi.model.Besin
import io.reactivex.Single
import retrofit2.http.GET

interface YemeklerAPI {
    @GET("atilsamancioglu/BTK20-JSONVeriSeti/master/besinler.json")
    fun getYemek() : Single<List<Besin>>
}