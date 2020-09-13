package com.lukeneedham.flowerpotrecyclersample.ui.feature.recycleritemview

import android.os.Bundle
import android.view.View
import androidx.fragment.app.Fragment
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.lukeneedham.flowerpotrecycler.SingleTypeRecyclerAdapterBuilder
import com.lukeneedham.flowerpotrecycler.adapter.config.AdapterConfig
import com.lukeneedham.flowerpotrecycler.util.extensions.addItemLayoutParams
import com.lukeneedham.flowerpotrecycler.util.extensions.addOnItemClickListener
import com.lukeneedham.flowerpotrecyclersample.R
import com.lukeneedham.flowerpotrecyclersample.domain.FlowerPotDatabase
import com.lukeneedham.flowerpotrecyclersample.domain.FlowerPotModel
import com.lukeneedham.flowerpotrecyclersample.ui.feature.FlowerPotItemView
import com.lukeneedham.flowerpotrecyclersample.ui.util.showSnackbar
import kotlinx.android.synthetic.main.fragment_recyclerview_layout.*

class RecyclerItemViewAdapterFragment : Fragment(R.layout.fragment_recyclerview_layout) {

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        val config = AdapterConfig<FlowerPotModel, FlowerPotItemView>().apply {
            items = FlowerPotDatabase.getAllEntries()
            addItemLayoutParams(
                RecyclerView.LayoutParams(
                    RecyclerView.LayoutParams.MATCH_PARENT,
                    RecyclerView.LayoutParams.WRAP_CONTENT
                )
            )
            addOnItemClickListener { item, position ->
                showSnackbar(item.nameResId)
            }
        }
        // Config optional
        val recyclerAdapter =
            SingleTypeRecyclerAdapterBuilder
                .fromRecyclerItemView<FlowerPotModel, FlowerPotItemView>(config)

        // Also, type params used above are optional, as they can be inferred from config:
        val recyclerAdapterInferredType =
            SingleTypeRecyclerAdapterBuilder.fromRecyclerItemView(config)

        // Alternatively, we could instantiate the view ourselves. Config optional
        val recyclerAdapterFromBuilder = SingleTypeRecyclerAdapterBuilder.fromRecyclerItemView {
            FlowerPotItemView(it.context)
        }

        recyclerView.apply {
            layoutManager = LinearLayoutManager(context)
            adapter = recyclerAdapter
        }
    }
}
