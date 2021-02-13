package org.ahivs.features.shifts.list.ui

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.ListAdapter
import androidx.recyclerview.widget.RecyclerView
import org.ahivs.features.shifts.data.Shift
import org.ahivs.features.shifts.databinding.ShiftItemBinding

class ShiftsListAdapter :
    ListAdapter<Shift, ShiftsListAdapter.ShiftItemViewHolder>(ShiftItemsDiffCallback()) {


    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ShiftItemViewHolder {
        val layoutInflater = LayoutInflater.from(parent.context)
        val binding = ShiftItemBinding.inflate(layoutInflater, parent, false)
        return ShiftItemViewHolder(binding)
    }

    override fun onBindViewHolder(holder: ShiftItemViewHolder, position: Int) {
        holder.bind(getItem(position))
    }

    class ShiftItemViewHolder constructor(val binding: ShiftItemBinding) :
        RecyclerView.ViewHolder(binding.root) {
        fun bind(shift: Shift) {
            binding.shift = shift
            binding.executePendingBindings()
        }
    }
}

class ShiftItemsDiffCallback : DiffUtil.ItemCallback<Shift>() {
    override fun areItemsTheSame(oldItem: Shift, newItem: Shift): Boolean {
        return oldItem.start == newItem.start
    }

    override fun areContentsTheSame(oldItem: Shift, newItem: Shift): Boolean {
        return oldItem.end == newItem.end &&
                oldItem.endLatitude == newItem.endLatitude && oldItem.endLongitude == newItem.endLongitude
    }

}