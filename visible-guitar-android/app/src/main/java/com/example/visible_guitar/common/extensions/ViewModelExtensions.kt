package com.example.visible_guitar.common.extensions

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import kotlinx.coroutines.Job
import kotlinx.coroutines.launch

internal fun ViewModel.launchCoroutine(block: suspend() -> Unit) : Job {
    return viewModelScope.launch {
        block()
    }
}