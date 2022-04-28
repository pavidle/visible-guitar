package com.example.visible_guitar.common.extensions

import android.app.Activity
import android.view.View
import androidx.core.content.ContextCompat
import androidx.core.os.bundleOf
import androidx.fragment.app.Fragment
import androidx.lifecycle.lifecycleScope
import androidx.navigation.fragment.findNavController
import com.example.visible_guitar.R
import com.example.visible_guitar.model.Model
import com.google.android.material.snackbar.Snackbar
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.launch

internal fun Fragment.launchCoroutine(block: suspend CoroutineScope.() -> Unit) {
    viewLifecycleOwner.lifecycleScope.launch {
        block()
    }
}

internal fun Fragment.navigateToDetailScreen(
    action: Int,
    argumentStringId: String
): (Model, View) -> Unit {

    fun navigationCallback(model: Model, view: View) {
        findNavController().navigate(
            action,
            bundleOf(argumentStringId to model.id)
        )
    }

    return ::navigationCallback
}


internal fun Activity.showBar(view: View, message: String) {
    val bar = Snackbar.make(view, message, Snackbar.LENGTH_LONG)
    bar
        .setBackgroundTint(ContextCompat.getColor(this, R.color.dark_red))
        .setDuration(8000)
        .show()
}

internal fun Fragment.showBar(view: View, message: String) {
    val bar = Snackbar.make(view, message, Snackbar.LENGTH_LONG)
    bar
        .setBackgroundTint(ContextCompat.getColor(context!!, R.color.dark_red))
        .setDuration(8000)
        .show()
}
