package com.olcertugba.yemekkitabi.ViewModel

import android.app.Application
import android.widget.Toast
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.olcertugba.yemekkitabi.model.Besin
import com.olcertugba.yemekkitabi.Service.YemekAPIServis
import com.olcertugba.yemekkitabi.Service.YemekDatabase
import com.olcertugba.yemekkitabi.Util.OzelSharedPreferences
import io.reactivex.android.schedulers.AndroidSchedulers
import io.reactivex.disposables.CompositeDisposable
import io.reactivex.observers.DisposableSingleObserver
import io.reactivex.schedulers.Schedulers
import kotlinx.coroutines.launch

class YemekListesiViewModel(application:Application): BaseViewModel(application) {
    val yemekler=MutableLiveData<List<Besin>>()
    val yemekHataMesaji=MutableLiveData<Boolean>()
    val yemekYukleniyor=MutableLiveData<Boolean>()
    private var guncellemeZamani = 10 * 60 * 1000 * 1000 * 1000L

    private val yemekApiServis = YemekAPIServis()
    private val disposable = CompositeDisposable()//tek kullanımlık (rxjava)
    private val ozelSharedPreferences=OzelSharedPreferences(getApplication())


    fun refreshData() {

        val kaydedilmeZamani = ozelSharedPreferences.zamaniAl()
        if (kaydedilmeZamani != null && kaydedilmeZamani != 0L && System.nanoTime() - kaydedilmeZamani < guncellemeZamani){
            //Sqlite'tan çek
            verileriSQLitetanAl()
        } else {
            verileriInternettenAl()
        }


    }

    fun refreshFromInternet(){
        verileriInternettenAl()
    }

    private fun verileriSQLitetanAl(){
        yemekYukleniyor.value = true

        launch {

            val yemekListesi = YemekDatabase(getApplication()).yemekDao().getAllYemek()
            besinleriGoster(yemekListesi)
            Toast.makeText(getApplication(),"Yemekleri Room'dan Aldık",Toast.LENGTH_LONG).show()

        }

    }

    private fun verileriInternettenAl(){
        yemekYukleniyor.value = true

        disposable.add(
            yemekApiServis.getData()
                .subscribeOn(Schedulers.newThread())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribeWith(object : DisposableSingleObserver<List<Besin>>() {
                    override fun onSuccess(t: List<Besin>) {
                        //Başarılı olursa
                        sqliteSakla(t)
                        Toast.makeText(getApplication(),"Yemekleri Internet'ten Aldık",Toast.LENGTH_LONG).show()

                    }

                    override fun onError(e: Throwable) {
                        //Hata alırsak
                        yemekHataMesaji.value = true
                        yemekYukleniyor.value = false
                        e.printStackTrace()
                    }

                })

        )

    }

    private fun besinleriGoster(yemeklerListesi : List<Besin>){
        yemekler.value = yemeklerListesi
        yemekHataMesaji.value = false
        yemekYukleniyor.value = false
    }

    private fun sqliteSakla(yemekListesi: List<Besin>){

        launch {

            val dao = YemekDatabase(getApplication()).yemekDao()
            dao.deleteAllYemek()
            val uuidListesi = dao.insertAll(*yemekListesi.toTypedArray())
            var i = 0
            while (i < yemekListesi.size){
                yemekListesi[i].uuid = uuidListesi[i].toInt()
                i = i + 1
            }
            besinleriGoster(yemekListesi)
        }

        ozelSharedPreferences.zamaniKaydet(System.nanoTime())

    }

}