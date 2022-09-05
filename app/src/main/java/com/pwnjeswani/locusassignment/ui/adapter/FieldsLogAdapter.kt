package com.pwnjeswani.locusassignment.ui.adapter

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.pwnjeswani.locusassignment.data.FieldData
import com.pwnjeswani.locusassignment.databinding.ItemLogFieldsBinding
import com.pwnjeswani.locusassignment.databinding.ItemPhotoBinding


class FieldsLogAdapter :
    RecyclerView.Adapter<RecyclerView.ViewHolder>() {

    lateinit var currentList: ArrayList<FieldData.FieldDataItem>

    override fun getItemCount(): Int {
        return currentList.size
    }

    fun submitList(list: ArrayList<FieldData.FieldDataItem>) {
        currentList = list
        notifyDataSetChanged()
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): RecyclerView.ViewHolder {
        return LogsViewHolder(
            ItemLogFieldsBinding.inflate(
                LayoutInflater.from(parent.context),
                parent,
                false
            )
        )
    }


    override fun onBindViewHolder(holder: RecyclerView.ViewHolder, position: Int) {
        (holder as LogsViewHolder).bindTo(currentList[position])
    }
}
