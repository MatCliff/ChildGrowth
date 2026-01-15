package com.ubaya.childgrowth.view

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.fragment.app.Fragment
import androidx.lifecycle.ViewModelProvider
import com.ubaya.childgrowth.databinding.FragmentProfileBinding
import com.ubaya.childgrowth.viewmodel.ProfileViewModel
import java.util.Calendar

class ProfileFragment : Fragment() {
    private lateinit var binding: FragmentProfileBinding
    private lateinit var viewModel: ProfileViewModel

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        binding = FragmentProfileBinding.inflate(inflater, container, false)
        viewModel = ViewModelProvider(this).get(ProfileViewModel::class.java)

        binding.viewModel = viewModel
        binding.lifecycleOwner = viewLifecycleOwner

        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        viewModel.refresh()

        // Observe LiveData for showing DatePicker
        viewModel.showDatePickerEvent.observe(viewLifecycleOwner) { show ->
            if (show == true) {
                openDatePicker()
                viewModel.showDatePickerEvent.value = false
            }
        }

        // Observe userLD to populate inputs if needed
        viewModel.userLD.observe(viewLifecycleOwner) { user ->
            viewModel.nameInput.value = user.name
            viewModel.bodInput.value = android.text.format.DateFormat.format("dd/MM/yyyy", user.bod).toString()
            viewModel.genderInput.value = user.gender
        }
    }

    private fun openDatePicker() {
        val calendar = Calendar.getInstance()
        val year = calendar.get(Calendar.YEAR)
        val month = calendar.get(Calendar.MONTH)
        val day = calendar.get(Calendar.DAY_OF_MONTH)

        val picker = android.app.DatePickerDialog(
            requireContext(),
            { _, selYear, selMonth, selDay ->
                val formatted = String.format("%02d/%02d/%04d", selDay, selMonth + 1, selYear)
                viewModel.bodInput.value = formatted
            }, year, month, day
        )
        picker.datePicker.maxDate = System.currentTimeMillis()
        picker.show()
    }
}
