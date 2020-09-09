package com.lukeneedham.flowerpotrecyclersample.ui.feature.xml

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
import com.lukeneedham.flowerpotrecyclersample.ui.util.showSnackbar
import kotlinx.android.synthetic.main.fragment_recyclerview_layout.*
import kotlinx.android.synthetic.main.view_flower_pot_item.view.*

class XmlAdapterFragment : Fragment(R.layout.fragment_recyclerview_layout) {

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        val config = AdapterConfig<FlowerPotModel>().apply {
            items = FlowerPotDatabase.getAllEntries()
            addItemLayoutParams(RecyclerView.LayoutParams(MATCH_PARENT, WRAP_CONTENT))
            addOnItemClickListener { item, position ->
                showSnackbar(item.nameResId)
            }
        }

        val recyclerAdapter = SingleTypeRecyclerAdapterBuilder.fromXml(
            R.layout.view_flower_pot_item,
            config
        ) { itemView, position, item ->
            itemView.potImageView.setImageResource(item.imageResId)
            itemView.potNameTextView.setText(item.nameResId)
        }

        // Alternatively, we can create a static adapter if we don't need any binding to be done
        val staticAdapter =
            SingleTypeRecyclerAdapterBuilder.fromXml(R.layout.view_flower_pot_item, config)

        recyclerView.apply {
            layoutManager = LinearLayoutManager(context)
            adapter = recyclerAdapter
        }
    }
}
