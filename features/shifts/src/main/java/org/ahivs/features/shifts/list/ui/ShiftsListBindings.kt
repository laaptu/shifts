package org.ahivs.features.shifts.list.ui

import android.view.View
import androidx.appcompat.widget.AppCompatImageView
import androidx.databinding.BindingAdapter
import com.squareup.picasso.Picasso

object ShiftsListBindings {
    @JvmStatic
    @BindingAdapter("loadImage")
    fun loadImage(imageView: AppCompatImageView, url: String) {
        Picasso.get().load(url).into(imageView)
    }

    @JvmStatic
    @BindingAdapter("showView")
    fun showView(view: View, show: Boolean) {
        when (show) {
            true -> view.visibility = View.GONE
            else -> view.visibility = View.VISIBLE
        }
    }
}