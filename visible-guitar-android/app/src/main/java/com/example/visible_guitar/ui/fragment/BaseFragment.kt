package com.example.visible_guitar.ui.fragment

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.annotation.LayoutRes
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import androidx.recyclerview.widget.RecyclerView
import androidx.viewbinding.ViewBinding
import by.kirich1409.viewbindingdelegate.viewBinding
import com.example.visible_guitar.databinding.FragmentHomeBinding
import com.example.visible_guitar.model.Model
import com.example.visible_guitar.model.states.ListState
import com.example.visible_guitar.ui.adapter.RecyclerViewAdapter
import com.example.visible_guitar.ui.adapter.RecyclerViewBuilder
import com.example.visible_guitar.ui.adapter.holder.RecyclerViewHolder
import com.example.visible_guitar.viewmodel.BaseViewModel
import com.example.visible_guitar.viewmodel.HomeViewModel
import java.lang.reflect.ParameterizedType


abstract class BaseFragment<VM : ViewModel>(
    @LayoutRes layout: Int
) : Fragment(layout) {

    protected abstract val viewModel: VM

    protected open fun <M : Model> setupRecyclerView(
        recyclerView: RecyclerView,
        adapter: RecyclerViewAdapter<M>,
        orientation: Int
    ): RecyclerView {
        val recyclerViewBuilder = RecyclerViewBuilder(recyclerView)
        return recyclerViewBuilder
            .setLinearManager(context!!, orientation)
            .setAdapter(adapter)
            .build()
    }

//    protected open fun <M : Model> observeViewModelState(state: ListState<M>) = Unit

    protected open fun setupViews() = Unit

    protected open fun observeLiveData() = Unit

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        setupViews()
        observeLiveData()
    }
}