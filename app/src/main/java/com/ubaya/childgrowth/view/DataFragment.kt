package com.ubaya.childgrowth.view

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.databinding.DataBindingUtil
import androidx.fragment.app.Fragment
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProvider
import androidx.recyclerview.widget.LinearLayoutManager
import com.ubaya.childgrowth.databinding.FragmentDataBinding
import com.ubaya.childgrowth.viewmodel.ChildViewModel
import com.ubaya.childgrowth.R

class DataFragment : Fragment() {
    private lateinit var binding: FragmentDataBinding
    private lateinit var viewModel: ChildViewModel
    private val growthListAdapter  = DataListAdapter(arrayListOf())


    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        binding =  DataBindingUtil.inflate(
            inflater,
            R.layout.fragment_data,
            container,
            false
        )
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        viewModel = ViewModelProvider(this).get(ChildViewModel::class.java)
        viewModel.refresh()
        binding.viewModel = viewModel
        binding.lifecycleOwner = viewLifecycleOwner
        binding.recViewGrowth.apply {
            layoutManager = LinearLayoutManager(context)
            adapter = growthListAdapter
        }
        //  Observer RecyclerView data
        viewModel.growthLD.observe(viewLifecycleOwner) {
            growthListAdapter.updateGrowthList(it)
        }
//        binding.recViewGrowth.layoutManager = LinearLayoutManager(context)
//        binding.recViewGrowth.adapter = growthListAdapter

        observeViewModel()

    }

    fun observeViewModel() {
        viewModel.growthLD.observe(viewLifecycleOwner, Observer {
            growthListAdapter.updateGrowthList(it)
        })
    }
//        viewModel.dataLoadErrorLD.observe(viewLifecycleOwner, Observer {
//            if(it == true) {
//                binding.txtError.visibility = View.VISIBLE
//            } else {
//                binding.txtError.visibility = View.GONE
//            }
//        })
//        viewModel.loadingLD.observe(viewLifecycleOwner, Observer {
//            if(it == true) {
//                binding.recViewGrowth.visibility = View.GONE
//                binding.progressLoad.visibility = View.VISIBLE
//            } else {
//                binding.recViewGrowth.visibility = View.VISIBLE
//                binding.progressLoad.visibility = View.GONE
//            }
//        })
//
//
//    }

}