package de.soenke.limit.utils.bindings

import android.widget.TextView
import androidx.databinding.BindingAdapter

@BindingAdapter("floatAsString")
fun floatAsString(view: TextView, value: Float?){
    value?.let{
        view.text = it.toString()
    }
}