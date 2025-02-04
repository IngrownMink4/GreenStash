/*
MIT License

Copyright (c) 2022 Stɑrry Shivɑm // This file is part of GreenStash.

Permission is hereby granted, free of charge, to any person obtaining a copy
of this software and associated documentation files (the "Software"), to deal
in the Software without restriction, including without limitation the rights
to use, copy, modify, merge, publish, distribute, sublicense, and/or sell
copies of the Software, and to permit persons to whom the Software is
furnished to do so, subject to the following conditions:

The above copyright notice and this permission notice shall be included in all
copies or substantial portions of the Software.

THE SOFTWARE IS PROVIDED "AS IS", WITHOUT WARRANTY OF ANY KIND, EXPRESS OR
IMPLIED, INCLUDING BUT NOT LIMITED TO THE WARRANTIES OF MERCHANTABILITY,
FITNESS FOR A PARTICULAR PURPOSE AND NONINFRINGEMENT. IN NO EVENT SHALL THE
AUTHORS OR COPYRIGHT HOLDERS BE LIABLE FOR ANY CLAIM, DAMAGES OR OTHER
LIABILITY, WHETHER IN AN ACTION OF CONTRACT, TORT OR OTHERWISE, ARISING FROM,
OUT OF OR IN CONNECTION WITH THE SOFTWARE OR THE USE OR OTHER DEALINGS IN THE
SOFTWARE.
*/

package com.starry.greenstash.utils

import android.content.Context
import android.content.res.Configuration
import android.graphics.Bitmap
import android.graphics.BitmapFactory
import android.net.Uri
import android.text.Editable
import android.view.LayoutInflater
import android.view.View
import android.view.inputmethod.InputMethodManager
import android.widget.Button
import android.widget.TextView
import android.widget.Toast
import androidx.appcompat.app.AlertDialog
import androidx.appcompat.app.AppCompatDelegate
import com.google.android.material.snackbar.Snackbar
import com.starry.greenstash.R
import java.math.RoundingMode
import java.text.DecimalFormat
import java.text.DecimalFormatSymbols
import java.util.*

/* ========================================= Extensions ======================================== */

fun View.visible() {
    visibility = View.VISIBLE
}

fun View.gone() {
    visibility = View.GONE
}

fun View.invisible() {
    visibility = View.INVISIBLE
}

fun Button.dismissKeyboard() {
    val imm: InputMethodManager? =
        context.getSystemService(Context.INPUT_METHOD_SERVICE) as InputMethodManager?
    imm?.hideSoftInputFromWindow(windowToken, 0)
}

fun String.toToast(context: Context) {
    Toast.makeText(context, this, Toast.LENGTH_SHORT).show()
}

fun String.toSnackbar(view: View) {
    Snackbar.make(view, this, Snackbar.LENGTH_SHORT).show()
}

fun Editable.validateAmount(): Boolean {
    return !(isEmpty() || isBlank() || toString().replace(',', '.') == "."
            || toString().replace(',', '.').toFloat() == 0f)
}

/* ======================================== Other utils ======================================== */

// round decimal (float) to 2 digits
fun roundFloat(number: Float): Float {
    val locale = DecimalFormatSymbols(Locale.US)
    val df = DecimalFormat("#.##", locale)
    df.roundingMode = RoundingMode.CEILING
    return df.format(number).toFloat()
}

// convert float into currency format
fun formatCurrency(number: Float): String {
    val df = DecimalFormat("#,###.00")
    return df.format(number)
}

// get bitmap from image Uri.
fun uriToBitmap(uri: Uri, context: Context): Bitmap {
    val stream = context.contentResolver.openInputStream(uri)
    return BitmapFactory.decodeStream(stream)
}

// change app theme globally.
fun setAppTheme(mode: String) {
    when (mode) {
        "dark" -> AppCompatDelegate.setDefaultNightMode(AppCompatDelegate.MODE_NIGHT_YES)
        "light" -> AppCompatDelegate.setDefaultNightMode(AppCompatDelegate.MODE_NIGHT_NO)
        "system" -> AppCompatDelegate.setDefaultNightMode(AppCompatDelegate.MODE_NIGHT_FOLLOW_SYSTEM)
    }
}

fun isDarkModeOn(ctx: Context): Boolean {
    return when (ctx.resources.configuration.uiMode and
            Configuration.UI_MODE_NIGHT_MASK) {
        Configuration.UI_MODE_NIGHT_YES -> true
        Configuration.UI_MODE_NIGHT_NO -> false
        Configuration.UI_MODE_NIGHT_UNDEFINED -> false
        else -> false
    }
}

class IndeterminateProgressDialog(context: Context) : AlertDialog(context) {
    private val messageTextView: TextView

    init {
        val view = LayoutInflater.from(context).inflate(R.layout.progress_dialog, null)
        messageTextView = view.findViewById(R.id.message)
        setView(view)
    }

    override fun setMessage(message: CharSequence?) {
        this.messageTextView.text = message.toString()
    }
}