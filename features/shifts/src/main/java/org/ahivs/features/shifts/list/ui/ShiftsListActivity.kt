package org.ahivs.features.shifts.list.ui

import android.content.Intent
import android.os.Bundle
import androidx.activity.viewModels
import androidx.lifecycle.Observer
import androidx.recyclerview.widget.DividerItemDecoration
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.google.android.material.snackbar.Snackbar
import org.ahivs.features.shifts.R
import org.ahivs.features.shifts.actions.ShiftActionActivity
import org.ahivs.features.shifts.databinding.ActivityShiftsListBinding
import org.ahivs.shared.base.ui.ViewModelActivity


class ShiftsListActivity : ViewModelActivity<ShiftsListViewModel>() {
    companion object {
        private const val REQUEST_SHIFT_ACTION = 0x1
    }

    override val viewModel: ShiftsListViewModel by viewModels { viewModelFactory }

    private val binding: ActivityShiftsListBinding by binding(R.layout.activity_shifts_list)
    private val shiftsListAdapter = ShiftsListAdapter()
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        initViews()
        listenToViewModelEvents()
        viewModel.fetchShifts()
    }

    private fun initViews() {
        binding.apply {
            lifecycleOwner = this@ShiftsListActivity
            viewModel = this@ShiftsListActivity.viewModel
            initRecyclerView(recyclerView)
            fab.setOnClickListener { invokeShiftStartEndAction() }
            refreshLayout.setOnRefreshListener {
                refreshShifts()
            }
        }
    }

    private fun initRecyclerView(recyclerView: RecyclerView) {
        val layoutManager = LinearLayoutManager(this, LinearLayoutManager.VERTICAL, false)
        recyclerView.layoutManager = layoutManager
        val dividerItemDecoration = DividerItemDecoration(
            recyclerView.context,
            layoutManager.orientation
        )
        recyclerView.addItemDecoration(dividerItemDecoration)
        recyclerView.adapter = shiftsListAdapter
    }

    private fun listenToViewModelEvents() {
        viewModel.infoMsgDate.observe(this, Observer {
            if (it == 0)
                return@Observer
            Snackbar.make(binding.fab, getString(it), Snackbar.LENGTH_SHORT).show()
        })
        viewModel.viewState.observe(this, Observer {
            if (it is LoadedStateWithStart)
                shiftsListAdapter.submitList(it.shifts)
            else if (it is LoadedStateWithEnd)
                shiftsListAdapter.submitList(it.shifts)
        })
        viewModel.shiftAction.observe(this, Observer {
            if (it is Start) {
                startShiftActionActivity(ShiftActionActivity.launchWithStartAction(this))
            } else if (it is End) {
                startShiftActionActivity(
                    ShiftActionActivity.launchWithEndAction(
                        this,
                        it.shiftToBeEnded.start
                    )
                )
            }
        })
    }

    private fun startShiftActionActivity(intent: Intent) {
        startActivityForResult(intent, REQUEST_SHIFT_ACTION)
    }

    private fun refreshShifts() = viewModel.refreshShifts()
    private fun invokeShiftStartEndAction() = viewModel.invokeShiftStartEndAction()

    override fun onActivityResult(requestCode: Int, resultCode: Int, data: Intent?) {
        super.onActivityResult(requestCode, resultCode, data)
    }

    override fun onDestroy() {
        super.onDestroy()
        viewModel.clearResources()
    }
}