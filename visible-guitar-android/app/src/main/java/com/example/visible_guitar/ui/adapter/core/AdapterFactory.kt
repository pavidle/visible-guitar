package com.example.visible_guitar.ui.adapter.core

import android.view.View
import androidx.annotation.LayoutRes
import androidx.recyclerview.widget.RecyclerView
import com.example.visible_guitar.model.Model
import com.example.visible_guitar.ui.adapter.holder.OnItemClickListener
import com.example.visible_guitar.ui.adapter.holder.RecyclerViewHolder

interface AdapterFactory<T: Model, A: RecyclerView.Adapter<RecyclerViewHolder<T>>> {
    fun create(
        @LayoutRes layout: Int,
        viewHolder: (View) -> RecyclerViewHolder<T>,
        onItemClickListener: OnItemClickListener<T>? = null
    ): A
}