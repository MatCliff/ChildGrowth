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

    private val header = 0
    private val item = 1

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int):DataViewHolder {
        val binding = DataListItemBinding.inflate(
            LayoutInflater.from(parent.context), parent, false)
        return DataViewHolder(binding)
    }

    override fun getItemViewType(position: Int): Int {
        return if (position == 0) {
            header
        } else {
            item
        }
    }

    override fun onBindViewHolder(holder: DataViewHolder, position: Int) {
        if(holder.itemViewType == header){
            //Bold Tulisannya
            holder.binding.txtAge.setTypeface(null, android.graphics.Typeface.BOLD)
            holder.binding.txtHeight.setTypeface(null, android.graphics.Typeface.BOLD)
            holder.binding.txtWeight.setTypeface(null, android.graphics.Typeface.BOLD)
        }
        else{
            //position di kurangi 1 karena posisi 0 dipake header dan data yang kurang mulai dari 0
            holder.binding.txtAge.text = growthList[position-1].age.toString()
            holder.binding.txtHeight.text = growthList[position-1].height.toString()
            holder.binding.txtWeight.text = growthList[position-1].weight.toString()
        }

    }

    override fun getItemCount(): Int {
        return growthList.size + 1 //ditambah satu dikarenakan ada header
    }

    fun updateGrowthList(newGrowthList: List<Child>) {
        growthList.clear()
        growthList.addAll(newGrowthList)
        notifyDataSetChanged()
    }

}