package com.example.visible_guitar.ui.adapter.core

import androidx.recyclerview.widget.DiffUtil
import com.example.visible_guitar.model.Model

class RecyclerViewItemCallback<T : Model> : DiffUtil.ItemCallback<T>() {
    override fun areItemsTheSame(oldItem: T, newItem: T) =
        oldItem.id == newItem.id

    override fun areContentsTheSame(oldItem: T, newItem: T) =
        oldItem.id == newItem.id
}