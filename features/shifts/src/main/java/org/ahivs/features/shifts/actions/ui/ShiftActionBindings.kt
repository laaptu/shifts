package org.ahivs.features.shifts.actions.ui

import android.view.View
import android.widget.TextView
import androidx.databinding.BindingAdapter
import com.google.android.material.button.MaterialButton
import com.google.android.material.progressindicator.CircularProgressIndicator
import com.google.android.material.textview.MaterialTextView
import org.ahivs.features.shifts.R
import org.ahivs.features.shifts.utils.DateUtils

object ShiftActionBindings {
    @JvmStatic
    @BindingAdapter("shiftActionViewState")
    fun setShiftActionViewState(view: View, shiftActionState: ShiftActionViewState) {
        when (view) {
            is MaterialTextView -> {
                if (shiftActionState is StartShift)
                    setTime(view, shiftActionState.time)
                else if (shiftActionState is EndShift)
                    setTime(view, shiftActionState.startTime)
            }
            is CircularProgressIndicator -> {
                if(shiftActionState == Progress)
                    view.visibility = View.VISIBLE
                else
                    view.visibility = View.GONE
            }
            is MaterialButton -> {
                view.isEnabled = shiftActionState != Progress
                if (shiftActionState is StartShift)
                    view.setText(R.string.start_shift)
                else if (shiftActionState is EndShift)
                    view.setText(R.string.end_shift)
            }
        }
    }

    private fun setTime(textView: TextView, time: String) {
        textView.text = textView.context.getString(
            R.string.shift_time,
            DateUtils.getUIDateFormat(time)
        )
    }
}