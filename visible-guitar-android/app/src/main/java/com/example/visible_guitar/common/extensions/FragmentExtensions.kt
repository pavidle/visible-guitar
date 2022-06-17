package com.example.visible_guitar.common.extensions

import android.app.Activity
import android.content.Context
import android.text.Editable
import android.text.TextWatcher
import android.view.View
import android.widget.EditText
import androidx.core.content.ContextCompat
import androidx.core.os.bundleOf
import androidx.fragment.app.Fragment
import androidx.lifecycle.lifecycleScope
import androidx.navigation.NavDirections
import androidx.navigation.fragment.findNavController
import androidx.viewbinding.ViewBinding
import com.example.visible_guitar.R
import com.example.visible_guitar.model.Model
import com.example.visible_guitar.ui.fragment.HomeFragmentDirections
import com.google.android.material.bottomsheet.BottomSheetBehavior
import com.google.android.material.snackbar.Snackbar
import com.google.android.material.textfield.TextInputLayout
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.launch
import kotlin.math.pow
import kotlin.reflect.KClass

internal fun Fragment.launchCoroutine(block: suspend CoroutineScope.() -> Unit) {
    viewLifecycleOwner.lifecycleScope.launch {
        block()
    }
}

internal fun  Fragment.navigateToDetailScreen(
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

internal fun Fragment.navigateToDetailScreen(
    action: (Int) -> NavDirections
): (Model) -> Unit {
    fun navigationCallback(model: Model) {
        findNavController().navigate(
            action(model.id)
        )
    }
    return ::navigationCallback
}

internal fun Fragment.setAlphaAnimation(view: View, value: Float) {
    view.animate()
    .alpha(value)
    .setDuration(0)
    .start()
}

internal fun Fragment.setBottomBehavior(view: View, animationCallback: (Float) -> Unit) {

    val bottomSheet = BottomSheetBehavior.from(view).apply {
        peekHeight = 300
    }
    val bottomSheetCallback = object : BottomSheetBehavior.BottomSheetCallback() {
        override fun onStateChanged(bottomSheet: View, newState: Int) = Unit

        override fun onSlide(bottomSheet: View, slideOffset: Float) {
            animationCallback(slideOffset)
        }
    }
    bottomSheet.addBottomSheetCallback(bottomSheetCallback)
}


internal fun EditText.afterTextChanged(action: (Editable?)-> Unit){
    this.addTextChangedListener(object : TextWatcher {
        override fun beforeTextChanged(p0: CharSequence?, p1: Int, p2: Int, p3: Int) {}
        override fun onTextChanged(p0: CharSequence?, p1: Int, p2: Int, p3: Int) {}
        override fun afterTextChanged(editable: Editable?) {
            action(editable)
        }
    })}

internal fun disableFieldError(layout: TextInputLayout) {
    layout.isErrorEnabled = false
}

internal fun Activity.showBar(view: View, message: String) {
    setBar(view, message, this)
}

internal fun Fragment.showBar(view: View, message: String) {
    setBar(view, message, context!!)
}

internal fun setBar(view: View, message: String, context: Context) {
    val bar = Snackbar.make(view, message, Snackbar.LENGTH_LONG)
    bar
        .setBackgroundTint(ContextCompat.getColor(context, R.color.dark_red))
        .setTextColor(ContextCompat.getColor(context, R.color.white))
        .setDuration(5000)
        .show()
}
