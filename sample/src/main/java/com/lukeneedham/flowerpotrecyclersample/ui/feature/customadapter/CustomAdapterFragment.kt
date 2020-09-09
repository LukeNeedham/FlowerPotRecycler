package com.lukeneedham.flowerpotrecyclersample.ui.feature.customadapter

import android.os.Bundle
import android.view.View
import androidx.fragment.app.Fragment
import androidx.recyclerview.widget.LinearLayoutManager
import com.lukeneedham.flowerpotrecyclersample.R
import com.lukeneedham.flowerpotrecyclersample.domain.FlowerPotDatabase
import com.lukeneedham.flowerpotrecyclersample.ui.util.showSnackbar
import kotlinx.android.synthetic.main.fragment_recyclerview_layout.*

class CustomAdapterFragment : Fragment(R.layout.fragment_recyclerview_layout) {

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        val customAdapter = CustomAdapter { showSnackbar(it.nameResId) }
        customAdapter.submitList(FlowerPotDatabase.getAllEntries())

        recyclerView.apply {
            layoutManager = LinearLayoutManager(context)
            adapter = customAdapter
        }
    }
}
