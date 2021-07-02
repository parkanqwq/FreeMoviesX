package com.kalabukhov.app.freemoviesx

import android.view.View
import com.google.android.material.snackbar.Snackbar

fun View.showSnackBar(
    text: String,
    actionText: String,
    action: (View) -> Unit,
    length: Int = Snackbar.LENGTH_INDEFINITE
){
    Snackbar.make(this, text, length).setAction(actionText, action).show()
}

fun View.showSnackBarLoading(
    text: String,
    length: Int = Snackbar.LENGTH_SHORT
){
    Snackbar.make(this, text, length).show()
}