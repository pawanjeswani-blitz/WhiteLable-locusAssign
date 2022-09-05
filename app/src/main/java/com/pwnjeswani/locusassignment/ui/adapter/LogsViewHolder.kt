package com.pwnjeswani.locusassignment.ui.adapter

import androidx.recyclerview.widget.RecyclerView
import com.pwnjeswani.locusassignment.data.Constants.COMMENT
import com.pwnjeswani.locusassignment.data.Constants.PHOTO
import com.pwnjeswani.locusassignment.data.Constants.SINGLE_CHOICE
import com.pwnjeswani.locusassignment.data.FieldData
import com.pwnjeswani.locusassignment.databinding.ItemLogFieldsBinding

class LogsViewHolder(
    private val binding: ItemLogFieldsBinding
) :
    RecyclerView.ViewHolder(binding.root) {


    fun bindTo(field: FieldData.FieldDataItem) {
        field.apply {
            binding.tvTitle.text = "Title: $title"
            binding.tvId.text = "Id: $id"
            binding.tvData.text = when (type) {
                SINGLE_CHOICE -> {
                    "Choice: $enteredData".takeIf { !enteredData.isNullOrEmpty() } ?: ""
                }
                COMMENT -> {
                    "Comment: $enteredData".takeIf { !enteredData.isNullOrEmpty() } ?: ""
                }
                PHOTO -> {
                    "Image path: $localPhotoUrl".takeIf { !localPhotoUrl.isNullOrEmpty() } ?: ""
                }
                else -> {
                    ""
                }
            }

        }
    }
}
