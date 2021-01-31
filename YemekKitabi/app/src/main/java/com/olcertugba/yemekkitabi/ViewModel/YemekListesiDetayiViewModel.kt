package com.olcertugba.yemekkitabi.ViewModel
import android.app.Application
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.olcertugba.yemekkitabi.model.Besin
import com.olcertugba.yemekkitabi.Service.YemekDatabase
import kotlinx.coroutines.launch

class YemekListesiDetayiViewModel(application: Application) : BaseViewModel(application) {

    val besinLiveData = MutableLiveData<Besin>()

    fun roomVerisiniAl(uuid : Int){
        launch {

            val dao = YemekDatabase(getApplication()).yemekDao()
            val besin = dao.getYemek(uuid)
            besinLiveData.value = besin
        }
    }
}