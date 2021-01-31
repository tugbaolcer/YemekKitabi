package com.olcertugba.yemekkitabi.Service

import android.content.Context
import androidx.room.Database
import androidx.room.Room
import androidx.room.RoomDatabase
import com.olcertugba.yemekkitabi.model.Besin

@Database(entities = arrayOf(Besin::class),version = 1)
abstract class YemekDatabase : RoomDatabase() {

    abstract fun yemekDao() : YemeklerDao

    //Singleton

    companion object {

        @Volatile private var instance : YemekDatabase? = null

        private val lock = Any()

        operator fun invoke(context : Context) = instance ?: synchronized(lock){
            instance ?: databaseOlustur(context).also {
                instance = it
            }
        }


        private fun databaseOlustur(context: Context) = Room.databaseBuilder(
            context.applicationContext,
            YemekDatabase::class.java,"yemekdatabase").build()

    }

}