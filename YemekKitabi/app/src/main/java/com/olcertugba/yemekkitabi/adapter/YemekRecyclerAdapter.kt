package com.olcertugba.yemekkitabi.adapter

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.databinding.DataBindingUtil
import androidx.navigation.Navigation
import androidx.recyclerview.widget.RecyclerView
import com.olcertugba.yemekkitabi.Fragments.YemekListesiFragmentDirections
import com.olcertugba.yemekkitabi.model.Besin
import com.olcertugba.yemekkitabi.R
import com.olcertugba.yemekkitabi.databinding.YemekRecyclerRowBinding
import kotlinx.android.synthetic.main.yemek_recycler_row.view.*
import java.util.ArrayList

class YemekRecyclerAdapter(val yemekListesi: ArrayList<Besin>):RecyclerView.Adapter<YemekRecyclerAdapter.YemekViewHolder>() , YemekClickListener{

    class YemekViewHolder(var view: YemekRecyclerRowBinding):RecyclerView.ViewHolder(view.root){

    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): YemekViewHolder {
        val inflater=LayoutInflater.from(parent.context)
        //val view=inflater.inflate(R.layout.yemek_recycler_row,parent,false)
        val view=DataBindingUtil.inflate<YemekRecyclerRowBinding>(inflater, R.layout.yemek_recycler_row,parent,false)
        return YemekViewHolder(view)
    }

    override fun onBindViewHolder(holder: YemekViewHolder, position: Int) {
        holder.view.besin = yemekListesi[position]
        holder.view.listener = this

    }

    override fun getItemCount(): Int {
        return yemekListesi.size
    }

    fun yemekListesininiGuncelle(yeniYemekListesi:List<Besin>){
        yemekListesi.clear()
        yemekListesi.addAll(yeniYemekListesi)
        notifyDataSetChanged()
    }

    override fun yemekTiklandi(view: View) {
        val uuid = view.besin_uuid.text.toString().toIntOrNull()
        uuid?.let {
            val action = YemekListesiFragmentDirections.actionYemekListesiFragmentToYemekDetayListesiFragment(it)
            Navigation.findNavController(view).navigate(action)

        }

    }
}