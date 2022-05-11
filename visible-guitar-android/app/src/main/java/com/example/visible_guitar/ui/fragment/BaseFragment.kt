package com.example.visible_guitar.ui.fragment

import android.os.Bundle
import android.view.View
import androidx.annotation.LayoutRes
import androidx.fragment.app.Fragment
import androidx.lifecycle.ViewModel
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.example.visible_guitar.model.Model
import com.example.visible_guitar.ui.adapter.RecyclerViewAdapter
import com.example.visible_guitar.ui.adapter.RecyclerViewBuilder


abstract class BaseFragment<VM : ViewModel>(
    @LayoutRes layout: Int
) : Fragment(layout) {

    protected abstract val viewModel: VM

    protected open fun <M : Model> setupRecyclerView(
        recyclerView: RecyclerView,
        adapter: RecyclerViewAdapter<M>
    ): RecyclerView {
        val recyclerViewBuilder = RecyclerViewBuilder(recyclerView)
        return recyclerViewBuilder
            .setLinearManager(context!!, LinearLayoutManager.HORIZONTAL)
            .setAdapter(adapter)
            .build()
    }

    protected open fun setupViews() = Unit
    protected open fun setupRequests() = Unit
    protected open fun setupListeners() = Unit
    protected open fun setupObservers() = Unit

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        setupViews()
        setupRequests()
        setupListeners()
        setupObservers()
    }
}