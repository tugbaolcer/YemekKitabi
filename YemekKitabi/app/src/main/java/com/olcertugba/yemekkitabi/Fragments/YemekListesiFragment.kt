package com.olcertugba.yemekkitabi.Fragments

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.LinearLayout
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProviders
import androidx.navigation.Navigation
import androidx.recyclerview.widget.LinearLayoutManager
import com.olcertugba.yemekkitabi.adapter.YemekRecyclerAdapter
import com.olcertugba.yemekkitabi.R
import com.olcertugba.yemekkitabi.ViewModel.YemekListesiViewModel
import kotlinx.android.synthetic.main.fragment_yemek_listesi.*

class YemekListesiFragment : Fragment() {

    private lateinit var viewModel: YemekListesiViewModel
    private val recyclerYemekAdapter=YemekRecyclerAdapter(arrayListOf())

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.fragment_yemek_listesi, container, false)

    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        viewModel=ViewModelProviders.of(this).get(YemekListesiViewModel::class.java)
        viewModel.refreshData()

        yemekListRecycler.layoutManager=LinearLayoutManager(context)
        yemekListRecycler.adapter=recyclerYemekAdapter

        swipeRefreshLayout.setOnRefreshListener {
            yemekYukleniyor.visibility = View.VISIBLE
            yemekHataMesaji.visibility = View.GONE
            yemekListRecycler.visibility = View.GONE
            viewModel.refreshFromInternet()
            swipeRefreshLayout.isRefreshing = false
        }
        observeLiveData()
    }

    fun observeLiveData(){
        viewModel.yemekler.observe(this, Observer {
            it?.let {
                recyclerYemekAdapter.yemekListesininiGuncelle(it)
                yemekListRecycler.visibility=View.VISIBLE
            }
        })

        viewModel.yemekHataMesaji.observe(this, Observer {
            it?.let {
                if (it==true){
                    yemekHataMesaji.visibility=View.VISIBLE
                }else{
                    yemekHataMesaji.visibility=View.GONE
                }
            }
        })

        viewModel.yemekYukleniyor.observe(this, Observer {
            it?.let{
                if (it==true){
                    yemekListRecycler.visibility=View.GONE
                    yemekHataMesaji.visibility=View.GONE
                    yemekYukleniyor.visibility=View.VISIBLE
                }else{
                    yemekYukleniyor.visibility=View.GONE
                }
            }
        })
    }


}