package com.ubaya.childgrowth.view

import android.content.Context
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import androidx.fragment.app.Fragment
import androidx.lifecycle.ViewModelProvider
import com.ubaya.childgrowth.databinding.FragmentProfileBinding
import com.ubaya.childgrowth.viewmodel.ProfileViewModel
import java.text.SimpleDateFormat
import java.util.Calendar
import java.util.Locale
import java.util.regex.Pattern

class ProfileFragment : Fragment() {
    private lateinit var binding: FragmentProfileBinding
    private lateinit var viewModel: ProfileViewModel

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View? {
        binding = FragmentProfileBinding.inflate(inflater,container,false)
        return binding.root

    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        viewModel = ViewModelProvider(this).get(ProfileViewModel::class.java)
        viewModel.refresh()
        observeViewModel()

        binding.btnSaveProfile.setOnClickListener{
            val inputName = binding.txtInputName.text.toString()
            val inputBirthdate = binding.txtInputBirthdate.text.toString()
            val inputGender = if(binding.radioBtnMale.isChecked) 0 else 1

            if(inputName.isEmpty() || inputBirthdate.isEmpty()){
                Toast.makeText(requireContext(), "All fields must be filled", Toast.LENGTH_SHORT).show()
            }
            if (!isValidName(inputName)) {
                Toast.makeText(requireContext(), "Name must contain only letters and spaces", Toast.LENGTH_SHORT).show()
                return@setOnClickListener
            }

            val sdf = SimpleDateFormat("dd/MM/yyyy", Locale.getDefault())
            val bodMillis = sdf.parse(inputBirthdate)?.time ?: return@setOnClickListener

            viewModel.updateProfile(inputName, bodMillis, inputGender)
            Toast.makeText(requireContext(), "Profile saved", Toast.LENGTH_SHORT).show()
        }
    }

    fun observeViewModel() {
        viewModel.userLD.observe(viewLifecycleOwner) { user ->
            binding.txtInputName.setText(user.name)

            val sdf = SimpleDateFormat("dd/MM/yyyy", Locale.getDefault())
            binding.txtInputBirthdate.setText(sdf.format(user.bod))

            if (user.gender == 0) {
                binding.radioBtnMale.isChecked = true
            } else {
                binding.radioBtnFemale.isChecked = true
            }
        }
    }

    private fun isValidName(name: String): Boolean {
        val namePattern = Pattern.compile("^[a-zA-Z\\s.'-]+$")
        return namePattern.matcher(name).matches() && name.length >= 2
    }

    private fun showDatePicker() {
        val calendar = Calendar.getInstance()
        val year = calendar.get(Calendar.YEAR)
        val month = calendar.get(Calendar.MONTH)
        val day = calendar.get(Calendar.DAY_OF_MONTH)

        val datePicker = android.app.DatePickerDialog(
            requireContext(),
            { _, selectedYear, selectedMonth, selectedDay ->
                // Format: DD/MM/YYYY
                val formattedDate = String.format("%02d/%02d/%04d", selectedDay, selectedMonth + 1, selectedYear)
                binding.txtInputBirthdate.setText(formattedDate)
            },
            year, month, day
        )

        // Set maksimal tanggal ke hari ini
        datePicker.datePicker.maxDate = System.currentTimeMillis()
        datePicker.show()
    }

}

