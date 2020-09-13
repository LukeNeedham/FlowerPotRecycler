package com.lukeneedham.flowerpotrecyclersample.ui.feature.view

import android.os.Bundle
import android.view.View
import android.view.ViewGroup.LayoutParams.MATCH_PARENT
import android.view.ViewGroup.LayoutParams.WRAP_CONTENT
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

class ViewAdapterFragment : Fragment(R.layout.fragment_recyclerview_layout) {

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        val config = AdapterConfig<FlowerPotModel, FlowerPotItemView>().apply {
            items = FlowerPotDatabase.getAllEntries()
            addItemLayoutParams(RecyclerView.LayoutParams(MATCH_PARENT, WRAP_CONTENT))
            addOnItemClickListener { item, position ->
                showSnackbar(item.nameResId)
            }
        }

        val recyclerAdapter = SingleTypeRecyclerAdapterBuilder.fromView(
            config,
            builder = { FlowerPotItemView(it.context) },
            binder = { itemView, position, item ->
                // As an example, we manually call the binding function.
                // In reality, since itemView is in this case a RecyclerItemView,
                // we can save on boilerplate by using [SingleTypeRecyclerAdapterBuilder.fromRecyclerItemView]
                itemView.setItem(position, item)
            }
        )

        // Alternatively, we can use the default view builder
        val alternativeAdapter = SingleTypeRecyclerAdapterBuilder
            .fromView(config) { itemView, position, item ->
                itemView.setItem(position, item)
            }

        recyclerView.apply {
            layoutManager = LinearLayoutManager(context)
            adapter = recyclerAdapter
        }
    }
}
