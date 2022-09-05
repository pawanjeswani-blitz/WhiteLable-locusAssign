package com.pwnjeswani.locusassignment.ui.adapter

import android.net.Uri
import android.view.View
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.pwnjeswani.locusassignment.R
import com.pwnjeswani.locusassignment.data.FieldData
import com.pwnjeswani.locusassignment.databinding.ItemPhotoBinding
import com.pwnjeswani.locusassignment.utils.setParams

class PhotoViewHolder(
    private val binding: ItemPhotoBinding,
    private val listener: ActionsListener
) :
    RecyclerView.ViewHolder(binding.root) {


    fun bindTo(field: FieldData.FieldDataItem) {
        val params = binding.ivImageSelect.layoutParams
        field.apply {
            binding.tvTitle.text = title
            binding.ivImageSelect.setParams((!isClicked), params = params)
            localPhotoUrl?.let { path ->
                binding.ivImageSelect.setImageURI(Uri.parse(path))
            }
            Glide.with(binding.ivImageSelect.context)
                .load(localPhotoUrl)
                .override(200, 200)
                .centerCrop()
                .error(R.drawable.ic_baseline_add_photo_alternate_24)
                .into(binding.ivImageSelect)

            binding.ivRemove.visibility =
                View.VISIBLE.takeIf { (!localPhotoUrl.isNullOrEmpty()) } ?: View.GONE
            binding.ivRemove.setOnClickListener {
                isClicked = false
                binding.ivImageSelect.setParams((!isClicked), params = params)
                field.id?.let { it1 -> listener.removeImageClicked(it1) }
            }
            binding.ivImageSelect.setOnClickListener {
                if (localPhotoUrl == null) {
                    field.id?.let { it1 ->
                        listener.imageClicked(it1)
                    }
                } else {
                    binding.ivImageSelect.setParams(isClicked, params = params)
                    isClicked = !isClicked
                }
            }
        }
    }
}
