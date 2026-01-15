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
        if (position == 0) {
            holder.binding.isHeader = true
        } else {
            holder.binding.isHeader = false
            holder.binding.child = growthList[position - 1]
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