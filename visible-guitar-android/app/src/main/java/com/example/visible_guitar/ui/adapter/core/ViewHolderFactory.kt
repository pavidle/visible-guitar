package com.example.visible_guitar.ui.adapter.core

import android.view.View
import com.example.visible_guitar.model.Model
import com.example.visible_guitar.ui.adapter.holder.RecyclerViewHolder

interface ViewHolderFactory<T : Model, VH : RecyclerViewHolder<T>> {
    fun create(view: View) : VH
}