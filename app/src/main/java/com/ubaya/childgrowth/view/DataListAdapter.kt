package com.ubaya.childgrowth.view

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.ubaya.childgrowth.databinding.DataListItemBinding
import com.ubaya.childgrowth.model.Child

class DataListAdapter(val growthList: ArrayList<Child>):
    RecyclerView.Adapter<DataListAdapter.DataViewHolder>() {
    class DataViewHolder(val binding: DataListItemBinding) :
        RecyclerView.ViewHolder(binding.root)

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int):DataViewHolder {
        val binding = DataListItemBinding.inflate(
            LayoutInflater.from(parent.context), parent, false)
        return DataViewHolder(binding)
    }

    override fun onBindViewHolder(holder: DataViewHolder, position: Int) {
        holder.binding.txtAge.text = "AGE"
        holder.binding.txtHeight.text = "Height (cm)"
        holder.binding.txtWeight.text = "Weight (kg)"
        holder.binding.txtAge.text = growthList[position].age.toString()
        holder.binding.txtHeight.text = growthList[position].height.toString()
        holder.binding.txtWeight.text = growthList[position].weight.toString()

    }

    override fun getItemCount(): Int {
        return growthList.size
    }

    fun updateGrowthList(newStudentList: ArrayList<Child>) {
        growthList.clear()
        growthList.addAll(newStudentList)
        notifyDataSetChanged()
    }

}