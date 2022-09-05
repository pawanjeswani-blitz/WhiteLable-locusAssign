package com.pwnjeswani.locusassignment.ui.fragment

import android.os.Bundle
import android.util.DisplayMetrics
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import com.google.android.material.bottomsheet.BottomSheetBehavior
import com.google.android.material.bottomsheet.BottomSheetDialogFragment
import com.pwnjeswani.locusassignment.data.FieldData
import com.pwnjeswani.locusassignment.databinding.LogsBottomSheetBinding
import com.pwnjeswani.locusassignment.ui.adapter.FieldsLogAdapter

const val LOGS_LIST = "logs_list"

class LogsBottomSheet : BottomSheetDialogFragment() {
    private lateinit var binding: LogsBottomSheetBinding
    lateinit var fieldData: FieldData
    val adapter by lazy { FieldsLogAdapter() }


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        arguments?.let {
            fieldData = it.getParcelable(LOGS_LIST)!!
        }
    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        binding = LogsBottomSheetBinding.inflate(inflater, container, false)
        return binding.root
    }


    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        binding.rvLogs.adapter = adapter
        adapter.submitList(fieldData)
    }


    companion object {
        fun newInstance(
            fieldData: FieldData,
        ) =
            LogsBottomSheet().apply {
                arguments = Bundle().apply {
                    putParcelable(LOGS_LIST, fieldData)
                }
            }
    }


}