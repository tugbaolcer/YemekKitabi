package com.olcertugba.yemekkitabi.Service

import androidx.room.Dao
import androidx.room.Insert
import androidx.room.Query
import com.olcertugba.yemekkitabi.model.Besin
@Dao
interface YemeklerDao {
    @Insert //Room, sınıfından gelir.
    suspend fun insertAll(vararg besin: Besin):List<Long>
    //suspend, coroutine scope
    //vararg, birden fazla obje almamızı sağlar
    @Query("Select * from besin")//Query, veri çekmemize yardımcı olur.
    suspend fun getAllYemek():List<Besin>

    @Query("Select * from besin where uuid=:yemekId")
    suspend fun getYemek(yemekId:Int):Besin

    @Query("Delete from besin")
    suspend fun deleteAllYemek()
}