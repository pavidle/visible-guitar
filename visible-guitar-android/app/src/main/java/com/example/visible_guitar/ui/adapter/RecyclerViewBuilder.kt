package com.example.visible_guitar.ui.adapter

import android.content.Context
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.example.visible_guitar.model.Model


class RecyclerViewBuilder(
    private val recyclerView: RecyclerView
) {

    fun setLinearManager(
        context: Context,
        orientation: Int
    ) = apply {
        recyclerView.layoutManager = LinearLayoutManager(
            context,
            orientation,
            false
        )
    }

    fun <M : Model> setAdapter(
        adapter: RecyclerViewAdapter<M>
    ) = apply {
        recyclerView.adapter = adapter
    }

    fun build(): RecyclerView {
        return recyclerView
    }

}