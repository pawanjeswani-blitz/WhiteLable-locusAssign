package com.pwnjeswani.locusassignment.ui.adapter

import android.view.View
import android.widget.RadioButton
import androidx.recyclerview.widget.RecyclerView
import com.pwnjeswani.locusassignment.data.FieldData
import com.pwnjeswani.locusassignment.databinding.ItemSingleChoiceBinding

class SingleChoiceViewHolder(
    private val binding: ItemSingleChoiceBinding,
    private val listener: ActionsListener
) :
    RecyclerView.ViewHolder(binding.root) {

    fun bindTo(field: FieldData.FieldDataItem) {
        field.apply {
            if (dataMap?.options != null) {
                binding.cvMain.visibility = View.VISIBLE
                binding.tvTitle.text = title
                binding.rgOptions.removeAllViews()
                binding.rgOptions.setOnCheckedChangeListener(null)
                dataMap.also {
                    it.options?.forEach { option ->
                        val radioButtonOption = RadioButton(binding.root.context).apply {
                            text = option
                            id = View.generateViewId()
                        }
                        if (option == enteredData) {
                            radioButtonOption.isChecked = true
                        }
                        binding.rgOptions.addView(radioButtonOption)
                    }
                }
                binding.rgOptions.setOnCheckedChangeListener { radioGroup, id ->
                    enteredData = "${binding.rgOptions.findViewById<RadioButton>(id)?.text}"
                    field.id?.let {
                        enteredData?.let { value ->
                            listener.dataUpdated(it, value)
                        }
                    }
                }
            } else {
                binding.cvMain.visibility = View.GONE
            }
        }
    }
}