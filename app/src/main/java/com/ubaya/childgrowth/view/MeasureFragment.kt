package com.ubaya.childgrowth.view

import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.fragment.app.Fragment
import androidx.lifecycle.ViewModelProvider
import com.google.gson.Gson
import com.ubaya.childgrowth.databinding.FragmentMeasureBinding
import com.ubaya.childgrowth.model.Child
import com.ubaya.childgrowth.util.FileHelper
import com.ubaya.childgrowth.viewModel.ChildViewModel

class MeasureFragment : Fragment() {
    private lateinit var binding: FragmentMeasureBinding
    private lateinit var viewModel: ChildViewModel

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View? {
        binding = FragmentMeasureBinding.inflate(inflater,container,false)
        // Inflate the layout for this fragment
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        viewModel = ViewModelProvider(this).get(ChildViewModel::class.java)

        binding.btnAddMeasure.setOnClickListener{
            val weightText = binding.txtInputWeight.text.toString()
            val heightText = binding.txtInputHeight.text.toString()
            val ageText = binding.txtInputAge.text.toString()

            if(weightText.isEmpty() || heightText.isEmpty() || ageText.isEmpty()) {
                Toast.makeText(requireContext(), "All fields must be filled", Toast.LENGTH_SHORT).show()
            }

            val weight = weightText.toDoubleOrNull()
            val height = heightText.toDoubleOrNull()
            val age = ageText.toIntOrNull()

            if(weight==null || height==null || age==null){
                Toast.makeText(requireContext(), "Invalid input", Toast.LENGTH_SHORT).show()
                return@setOnClickListener
            }

            val measure = Child(
                weight,
                height,
                age
            )

            viewModel.addMeasure(measure)
            Toast.makeText(requireContext(), "Data added", Toast.LENGTH_SHORT).show()

            binding.txtInputWeight.setText("")
            binding.txtInputHeight.setText("")
            binding.txtInputAge.setText("")
        }
    }

}