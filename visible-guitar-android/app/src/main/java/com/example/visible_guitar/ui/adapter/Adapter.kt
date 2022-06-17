package com.example.visible_guitar.ui.adapter

import android.view.View
import androidx.annotation.LayoutRes
import androidx.recyclerview.widget.ListAdapter
import com.example.visible_guitar.model.Model
import com.example.visible_guitar.ui.adapter.core.RecyclerViewAdapterFactory
import com.example.visible_guitar.ui.adapter.holder.OnItemClickListener
import com.example.visible_guitar.ui.adapter.holder.RecyclerViewHolder

typealias RecyclerViewAdapter<T> = ListAdapter<T, RecyclerViewHolder<T>>

fun <T : Model, VH : RecyclerViewHolder<T>> createAdapterOf(
    @LayoutRes layout: Int,
    viewHolder: (View) -> VH,
    onItemClickListener: OnItemClickListener<T>? = null
): ListAdapter<T, RecyclerViewHolder<T>> =
    RecyclerViewAdapterFactory<T>().create(
        layout,
        viewHolder,
        onItemClickListener
    )
