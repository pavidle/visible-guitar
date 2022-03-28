package com.example.visible_guitar.common

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Job
import kotlinx.coroutines.launch

internal fun ViewModel.launchCoroutine(block: suspend CoroutineScope.() -> Unit) : Job {
    return viewModelScope.launch {
        block()
    }
}