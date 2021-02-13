package org.ahivs.features.shifts.list.ui

import android.view.View
import androidx.appcompat.widget.AppCompatImageView
import androidx.databinding.BindingAdapter
import androidx.swiperefreshlayout.widget.SwipeRefreshLayout
import com.google.android.material.floatingactionbutton.FloatingActionButton
import com.squareup.picasso.Picasso
import org.ahivs.features.shifts.R

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

    @JvmStatic
    @BindingAdapter("listViewState")
    fun setViewState(view: View, listViewState: ListViewState) {
        when (view) {
            is FloatingActionButton -> {
                var visibility = View.VISIBLE
                when (listViewState) {
                    is ProgressState -> visibility = View.GONE
                    is LoadedStateWithEnd -> view.setImageResource(R.drawable.ic_end)
                    else -> view.setImageResource(R.drawable.ic_start)
                }
                view.visibility = visibility
            }
            is SwipeRefreshLayout -> {
                if (listViewState == ProgressState) {
                    if (!view.isRefreshing)
                        view.isRefreshing = true
                } else {
                    if (view.isRefreshing)
                        view.isRefreshing = false
                }
            }
        }
    }
}