package com.pwnjeswani.locusassignment.ui.adapter

import android.text.TextWatcher
import android.view.View
import androidx.core.widget.doAfterTextChanged
import androidx.core.widget.doOnTextChanged
import androidx.recyclerview.widget.RecyclerView
import com.pwnjeswani.locusassignment.data.FieldData
import com.pwnjeswani.locusassignment.databinding.ItemCommentBinding

class CommentViewHolder(
    private val binding: ItemCommentBinding,
    private val listener: ActionsListener
) :
    RecyclerView.ViewHolder(binding.root) {

    fun bindTo(field: FieldData.FieldDataItem) {
        field.apply {
            binding.tvTitle.text = title
            enteredData?.let {
                binding.tetComment.setText(it)
                binding.tetComment.setSelection(it.length)
            }
            binding.swComment.setOnCheckedChangeListener(null)
            binding.swComment.isChecked = isClicked
            binding.tetComment.visibility = if (isClicked) View.VISIBLE else View.GONE
            binding.swComment.setOnCheckedChangeListener { _, _ ->
                isClicked = !isClicked
                binding.tetComment.visibility = if (isClicked) View.VISIBLE else View.GONE
            }
            binding.tetComment.doAfterTextChanged {editable->
                id?.let {
                    editable.let { text ->
                        listener.dataUpdated(it, text.toString())
                    }
                }
            }

        }
    }
}