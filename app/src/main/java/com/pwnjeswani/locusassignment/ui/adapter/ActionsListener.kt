package com.pwnjeswani.locusassignment.ui.adapter

interface ActionsListener {
    fun imageClicked(id: String)
    fun removeImageClicked(id: String)
    fun dataUpdated(id: String, value: String)
}