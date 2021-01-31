package com.olcertugba.yemekkitabi.Fragments

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.databinding.DataBindingUtil
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProviders
import com.olcertugba.yemekkitabi.R
import com.olcertugba.yemekkitabi.ViewModel.YemekListesiDetayiViewModel
import com.olcertugba.yemekkitabi.databinding.FragmentYemekDetayListesiBinding
import kotlinx.android.synthetic.main.fragment_yemek_detay_listesi.*

class YemekDetayListesiFragment : Fragment() {

    private lateinit var viewModel : YemekListesiDetayiViewModel
    private var besinId = 0
    private lateinit var dataBinding : FragmentYemekDetayListesiBinding
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        dataBinding = DataBindingUtil.inflate(inflater,R.layout.fragment_yemek_detay_listesi,container,false)
        return dataBinding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        arguments?.let {
            besinId = YemekDetayListesiFragmentArgs.fromBundle(it).yemekId

        }

        viewModel = ViewModelProviders.of(this).get(YemekListesiDetayiViewModel::class.java)
        viewModel.roomVerisiniAl(besinId)


        observeLiveData()

    }

    fun observeLiveData(){

        viewModel.besinLiveData.observe(viewLifecycleOwner, Observer {besin ->

            besin?.let {

                dataBinding.secilenYemek = it

            }

        })

    }

}