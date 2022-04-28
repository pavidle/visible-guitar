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
    ): RecyclerViewBuilder {
        recyclerView.layoutManager = LinearLayoutManager(
            context,
            orientation,
            false
        )
        return this
    }

    fun <M : Model> setAdapter(
        adapter: RecyclerViewAdapter<M>
    ): RecyclerViewBuilder {
        recyclerView.adapter = adapter
        return this
    }

    fun build(): RecyclerView {
        return recyclerView
    }

}