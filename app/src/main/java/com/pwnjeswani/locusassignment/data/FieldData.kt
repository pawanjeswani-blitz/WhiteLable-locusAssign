package com.pwnjeswani.locusassignment.data


import android.os.Parcelable
import com.google.gson.annotations.SerializedName
import kotlinx.parcelize.Parcelize
@Parcelize
class FieldData : ArrayList<FieldData.FieldDataItem>(), Parcelable {
    @Parcelize
    data class FieldDataItem(
        @SerializedName("dataMap")
        val dataMap: DataMap?,
        @SerializedName("id")
        val id: String?,
        @SerializedName("title")
        val title: String?,
        @SerializedName("type")
        val type: String?,
        var isClicked: Boolean = false,
        var localPhotoUrl: String? = null,
        var enteredData: String? = null
    ) : Parcelable

    @Parcelize
    data class DataMap(
        val options: ArrayList<String>?
    ) : Parcelable
}