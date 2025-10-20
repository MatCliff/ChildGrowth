package com.ubaya.childgrowth.view

import android.content.Context
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.fragment.app.Fragment
import com.ubaya.childgrowth.databinding.FragmentProfileBinding

class ProfileFragment : Fragment() {
    private lateinit var binding: FragmentProfileBinding

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View? {
        binding = FragmentProfileBinding.inflate(inflater,container,false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        val sharePref = requireActivity().getSharedPreferences("profile_data", Context.MODE_PRIVATE)

        val name = sharePref.getString("name", "Udin Dindin")
        val birthdate = sharePref.getString("birthdate", "01/05/2004")
        val gender = sharePref.getString("gender", "Male")

        binding.txtInputName.setText(name)
        binding.txtInputBirthdate.setText(birthdate)
        if(gender == "Male"){
            binding.radioBtnMale.isChecked = true
        }else{
            binding.radioBtnFemale.isChecked = true
        }

        binding.btnSaveProfile.setOnClickListener{
            val inputName = binding.txtInputName.text.toString()
            val inputBirthdate = binding.txtInputBirthdate.text.toString()
            val inputGender = if(binding.radioBtnMale.isChecked) "Male" else "Female"

            if(inputName.isEmpty() || inputBirthdate.isEmpty() || inputGender.isEmpty()){
                Toast.makeText(requireContext(), "All fields must be filled", Toast.LENGTH_SHORT).show()
            }

            sharePref.edit().apply{
                putString("name", inputName)
                putString("birthdate", inputBirthdate)
                putString("gender", inputGender)
            }

            Toast.makeText(requireContext(), "Profile saved", Toast.LENGTH_SHORT).show()
        }
    }
}