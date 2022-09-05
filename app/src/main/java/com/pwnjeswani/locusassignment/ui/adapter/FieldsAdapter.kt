package com.pwnjeswani.locusassignment.ui.adapter

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.pwnjeswani.locusassignment.data.Constants.COMMENT
import com.pwnjeswani.locusassignment.data.Constants.PHOTO
import com.pwnjeswani.locusassignment.data.Constants.SINGLE_CHOICE
import com.pwnjeswani.locusassignment.data.Constants.TYPE_COMMENT
import com.pwnjeswani.locusassignment.data.Constants.TYPE_PHOTO
import com.pwnjeswani.locusassignment.data.Constants.TYPE_SINGLE_CHOICE
import com.pwnjeswani.locusassignment.data.FieldData
import com.pwnjeswani.locusassignment.databinding.ItemCommentBinding
import com.pwnjeswani.locusassignment.databinding.ItemPhotoBinding
import com.pwnjeswani.locusassignment.databinding.ItemSingleChoiceBinding


class FieldsAdapter(private val listener: ActionsListener) :
    RecyclerView.Adapter<RecyclerView.ViewHolder>() {

    lateinit var currentList: ArrayList<FieldData.FieldDataItem>

    override fun getItemCount(): Int {
        return currentList.size
    }

    fun submitList(list:ArrayList<FieldData.FieldDataItem>){
        currentList = list
        notifyDataSetChanged()
    }

    override fun getItemViewType(position: Int): Int {
        return when (currentList[position].type) {
            PHOTO -> TYPE_PHOTO
            SINGLE_CHOICE -> TYPE_SINGLE_CHOICE
            COMMENT -> TYPE_COMMENT
            else -> TYPE_PHOTO
        }
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): RecyclerView.ViewHolder {
        return when (viewType) {
            TYPE_PHOTO -> PhotoViewHolder(
                ItemPhotoBinding.inflate(
                    LayoutInflater.from(parent.context),
                    parent,
                    false
                ), listener = listener
            )
            TYPE_SINGLE_CHOICE -> SingleChoiceViewHolder(
                ItemSingleChoiceBinding.inflate(
                    LayoutInflater.from(parent.context),
                    parent,
                    false
                ), listener = listener
            )
            TYPE_COMMENT -> CommentViewHolder(
                ItemCommentBinding.inflate(
                    LayoutInflater.from(parent.context),
                    parent,
                    false
                ), listener = listener
            )
            else -> PhotoViewHolder(
                ItemPhotoBinding.inflate(
                    LayoutInflater.from(parent.context),
                    parent,
                    false
                ), listener = listener
            )
        }
    }

    override fun onBindViewHolder(holder: RecyclerView.ViewHolder, position: Int) {
        when (getItemViewType(position)) {
            TYPE_PHOTO -> {
                (holder as PhotoViewHolder).bindTo(currentList[position])
            }
            TYPE_SINGLE_CHOICE -> {
                (holder as SingleChoiceViewHolder).bindTo(currentList[position])
            }
            TYPE_COMMENT -> {
                (holder as CommentViewHolder).bindTo(currentList[position])
            }
            else -> {
                (holder as PhotoViewHolder).bindTo(currentList[position])
            }
        }
    }
}
