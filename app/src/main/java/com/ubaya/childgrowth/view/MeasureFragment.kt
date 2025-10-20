package com.ubaya.childgrowth.view

import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.fragment.app.Fragment
import com.google.gson.Gson
import com.ubaya.childgrowth.databinding.FragmentMeasureBinding
import com.ubaya.childgrowth.model.Child
import com.ubaya.childgrowth.util.FileHelper

class MeasureFragment : Fragment() {
    private lateinit var binding: FragmentMeasureBinding
    private lateinit var fileHelper: FileHelper
    private val gson = Gson()

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View? {
        binding = FragmentMeasureBinding.inflate(inflater,container,false)
        // Inflate the layout for this fragment
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        fileHelper = FileHelper(requireContext())

        binding.btnAddMeasure.setOnClickListener{
            val weightText = binding.txtInputWeight.text.toString()
            val heightText = binding.txtInputHeight.text.toString()
            val ageText = binding.txtInputAge.text.toString()

            if(weightText.isEmpty() || heightText.isEmpty() || ageText.isEmpty()) {
                Toast.makeText(requireContext(), "All fields must be filled", Toast.LENGTH_SHORT).show()
            }

            val measure = Child(
                weightText.toDouble(),
                heightText.toDouble(),
                ageText.toInt()
            )

            val jsonData = gson.toJson(measure)
            fileHelper.addToFile(jsonData)
            Toast.makeText(requireContext(), "Data added", Toast.LENGTH_SHORT).show()

            binding.txtInputWeight.setText("")
            binding.txtInputHeight.setText("")
            binding.txtInputAge.setText("")
        }
    }

}