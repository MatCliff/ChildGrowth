package com.ubaya.childgrowth.view

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProvider
import androidx.recyclerview.widget.LinearLayoutManager
import com.ubaya.childgrowth.databinding.FragmentDataBinding
import com.ubaya.childgrowth.viewModel.ListViewModel

class DataFragment : Fragment() {
    private lateinit var binding: FragmentDataBinding
    private lateinit var viewModel: ListViewModel
    private val growthListAdapter  = DataListAdapter(arrayListOf())


    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        binding = FragmentDataBinding.inflate(inflater,container,false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        viewModel = ViewModelProvider(this).get(ListViewModel::class.java)
        viewModel.refresh()
        //viewModel.testSaveFile()
        binding.recViewGrowth.layoutManager = LinearLayoutManager(context)
        binding.recViewGrowth.adapter = growthListAdapter

        observeViewModel()

    }

    fun observeViewModel() {
        viewModel.growthLD.observe(viewLifecycleOwner, Observer {
            growthListAdapter.updateGrowthList(it)
        })
        viewModel.dataLoadErrorLD.observe(viewLifecycleOwner, Observer {
            if(it == true) {
                binding.txtError?.visibility = View.VISIBLE
            } else {
                binding.txtError?.visibility = View.GONE
            }
        })
        viewModel.loadingLD.observe(viewLifecycleOwner, Observer {
            if(it == true) {
                binding.recViewGrowth.visibility = View.GONE
                binding.progressLoad.visibility = View.VISIBLE
            } else {
                binding.recViewGrowth.visibility = View.VISIBLE
                binding.progressLoad.visibility = View.GONE
            }
        })


    }

}