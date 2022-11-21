package com.example.seoulpublicswimmingpool.adapter

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.example.seoulpublicswimmingpool.database.SwimLesson
import com.example.seoulpublicswimmingpool.seoulPublicSwimLessonData.Row
import com.example.seoulpublicswimmingpool.databinding.ItemMainBinding

class CustomAdapter(private var dataList: ArrayList<SwimLesson>) :
    RecyclerView.Adapter<CustomAdapter.CustomViewHolder>() {

    /*Set values of data for each item*/
    inner class CustomViewHolder(private val binding: ItemMainBinding) :
        RecyclerView.ViewHolder(binding.root) {
        fun bind(data: SwimLesson) {
            binding.tvCenterName.text = data.center
            binding.tvWeek.text = data.week
            binding.tvClassTime.text = data.time
            binding.tvFee.text = data.fee
        }
    }

    /*Bind xml file to viewholder*/
    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): CustomViewHolder {
        val binding = ItemMainBinding.inflate(LayoutInflater.from(parent.context), parent, false)
        return CustomViewHolder(binding)
    }
    override fun onBindViewHolder(holder: CustomViewHolder, position: Int) {
        holder.bind( dataList[position])
    }

    override fun getItemCount(): Int = dataList.size
}
