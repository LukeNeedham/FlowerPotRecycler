package com.lukeneedham.flowerpotrecyclersample.ui.view.singletype

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.fragment.app.Fragment
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.lukeneedham.flowerpotrecycler.RecyclerAdapterBuilder
import com.lukeneedham.flowerpotrecycler.SingleTypeRecyclerAdapterBuilder
import com.lukeneedham.flowerpotrecycler.delegatedadapter.config.AdapterConfig
import com.lukeneedham.flowerpotrecycler.util.addItemLayoutParams
import com.lukeneedham.flowerpotrecycler.util.addOnItemClickListener
import com.lukeneedham.flowerpotrecyclersample.R
import com.lukeneedham.flowerpotrecyclersample.domain.FlowerPotDatabase
import com.lukeneedham.flowerpotrecyclersample.domain.FlowerPotModel
import com.lukeneedham.flowerpotrecyclersample.ui.view.FlowerPotItemView
import kotlinx.android.synthetic.main.fragment_xml_layout.*

class SingleViewTypeFragment : Fragment() {

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ) = inflater.inflate(R.layout.fragment_xml_layout, container, false)

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        val config = AdapterConfig<FlowerPotModel>().apply {
            items = FlowerPotDatabase.getAllEntries()
            addItemLayoutParams(
                RecyclerView.LayoutParams(
                    RecyclerView.LayoutParams.WRAP_CONTENT,
                    RecyclerView.LayoutParams.WRAP_CONTENT
                )
            )
            addOnItemClickListener { item, position ->
                val context = requireContext()
                Toast.makeText(context, context.getString(item.nameResId), Toast.LENGTH_SHORT)
                    .show()
            }
        }
        // Config optional
        val recyclerAdapter =
            SingleTypeRecyclerAdapterBuilder.fromRecyclerItemView<FlowerPotModel, FlowerPotItemView>(config)

        // Alternatively, we could instantiate the view ourselves. Config optional
        val recyclerAdapterFromViewCreator = SingleTypeRecyclerAdapterBuilder.fromRecyclerItemViewCreator {
            FlowerPotItemView(it.context)
        }

        // Alternatively, we could also specify the view class manually
        // This is useful when calling from Java. Config optional
        val recyclerAdapterFromViewClass =
            SingleTypeRecyclerAdapterBuilder.fromRecyclerItemViewClass(FlowerPotItemView::class)

        recyclerView.apply {
            layoutManager = LinearLayoutManager(context)
            adapter = recyclerAdapter
        }
    }
}
